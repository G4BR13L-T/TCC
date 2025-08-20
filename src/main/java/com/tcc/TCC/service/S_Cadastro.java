package com.tcc.TCC.service;

import com.tcc.TCC.model.M_NivelPoder;
import com.tcc.TCC.model.M_Notebook;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.repository.R_NivelPoder;
import com.tcc.TCC.repository.R_Notebook;
import com.tcc.TCC.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class S_Cadastro {
    @Autowired
    private R_Usuario rUsuario;
    @Autowired
    private R_NivelPoder rNivelPoder;
    @Autowired
    private R_Notebook rNotebook;
    @Autowired
    private S_EMailSender eMailSender;

    private final PasswordEncoder passwordEncoder;

    public S_Cadastro() {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    /**
     * Realiza o cadastro de um novo usuário
     * @param nome
     * @param matricula
     * @param email
     * @param senha
     * @param confsenha
     * @param poderS
     * @return M_Resposta
     */
    public M_Resposta realizarCadastroUser(String nome,
                                           String matricula,
                                           String email,
                                           String senha,
                                           String confsenha,
                                           String poderS) {
        boolean sucesso = true;
        String mensagem = "";
        Long poderL = Long.parseLong(poderS);
        if (nome.isBlank()) {
            sucesso = false;
            mensagem += "O campo \"Nome\" deve ser preenchido!\n";
        }
        if (matricula.isBlank()) {
            sucesso = false;
            mensagem += "O campo \"Matrícula\" deve ser preenchido!\n";
        }
        if(email.isBlank()){
            sucesso = false;
            mensagem += "O campo \"E-Mail\" deve ser preenchido!\n";
        }
        if(senha.isBlank() && confsenha.isBlank()){
            sucesso = false;
            mensagem += "Os campos \"Senha\" e \"Confirmação\" devem ser preenchidos!\n";
        }else if(senha.isBlank()){
            sucesso = false;
            mensagem += "O campo \"Senha\" deve ser preenchido!\n";
        }else if(confsenha.isBlank()){
            sucesso = false;
            mensagem += "O campo \"Confirmação\" deve ser preenchido!\n";
        }else if(!senha.equals(confsenha)){
            sucesso = false;
            mensagem += "Os campos \"Senha\" e \"Confirmação\" devem ser iguais!\n";
        }
        if(poderL < 2 || poderL > 4){
            sucesso = false;
            mensagem += "Favor selecione um valor válido!\n";
        }
        for (M_Usuario user: rUsuario.findAll()){
            if (user.getMatricula().equals(matricula) || user.getEmail().equals(email) ||
                    user.getNome().toLowerCase(Locale.getDefault()).equals(nome.trim().toLowerCase(Locale.getDefault()))){
                sucesso = false;
                mensagem += "Esse usuário já existe!";
            }
        }
        if(sucesso){
            try{
                M_Usuario mUsuario = new M_Usuario();
                M_NivelPoder poder = rNivelPoder.getReferenceById(poderL);
                poder.setId(poderL);
                mUsuario.setNome(nome);
                mUsuario.setMatricula(matricula);
                mUsuario.setEmail(email);
//                mUsuario.setSenha(senha);
                mUsuario.setSenha(passwordEncoder.encode(senha));
                mUsuario.setPoder(poder);
                rUsuario.save(mUsuario);
                mensagem += "Usuário cadastrado com sucesso!";
                String eMailSubject = "Bem-vindo ao NotReserve!";
                String eMail =
                        "Olá, " + mUsuario.getNome() + "\n\n" +
                            "Seu cadastro foi realizado com sucesso no sistema NotReserve! \n\n" +
                            "Aqui estão seus dados de acesso:\n" +
                            "- Matrícula: " + mUsuario.getMatricula() + "\n" +
                            "- Senha: " + senha + "\n\n" +
                            "Agora você já pode acessar o sistema e começar a utilizar os nossos serviços.";
                eMailSender.enviarEmailSimples(mUsuario.getEmail(), eMailSubject, eMail);
            }catch (Exception e){
                sucesso = false;
                mensagem += "Erro interno durante o cadastro!";
            }
        }
        return new M_Resposta(sucesso,mensagem);
    }

    /**
     * Realiza o cadastro de um novo notebook
     * @param numero
     * @param codigo
     * @return M_Resposta
     */
    public M_Resposta realizarCadastroNote(Integer numero,
                                           String codigo){
        boolean sucesso = true;
        String mensagem = "";
        for (M_Notebook note: rNotebook.findAll()) {
            if(note.getCodigoPatrimonio().equals(codigo)){
                sucesso = false;
                mensagem += "Esse notebook já foi cadastrado, tente outro!\n";
            }
        }
        if(codigo.isBlank()){
            sucesso = false;
            mensagem += "O campo \"Código de Patrimônio\" deve ser preenchido!\n";
        }
        if(sucesso){
            try{
                M_Notebook notebook = new M_Notebook();
                notebook.setNumero(numero);
                notebook.setCodigoPatrimonio(codigo);
                notebook.setAtivo(true);
                rNotebook.save(notebook);
                mensagem += "O notebook foi cadastrado com sucesso!\n";
            }catch (Exception e){
                sucesso = false;
                mensagem += "Erro interno durante o cadastro!";
            }
        }
        return new M_Resposta(sucesso,mensagem);
    }
}
