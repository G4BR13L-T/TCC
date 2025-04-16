package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class S_Cadastro {
    @Autowired
    private R_Usuario rUsuario;

    public M_Resposta realizarCadastro(String nome,
                                       String matricula,
                                       String email,
                                       String senha,
                                       String confsenha,
                                       String poderS) {
        boolean sucesso = true;
        String mensagem = "";
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
            mensagem += "Os campos \"Senhaz\" e \"Confirmação\" devem ser iguais!\n";
        }
        return null;
    }
}
