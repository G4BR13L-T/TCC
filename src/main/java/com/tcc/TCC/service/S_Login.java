package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.repository.R_Usuario;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Login {
    @Autowired
    private R_Usuario rUsuario;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param matricula
     * @param senha
     * @return M_Resposta
     */
    public M_Resposta validaLogin(String matricula, String senha){
        String mensagem = "";
        boolean sucesso = true;
        if(matricula.trim().isBlank() && senha.trim().isBlank()){
            mensagem += "Os campos devem ser preenchidos!\n";
            sucesso = false;
        }else if(matricula.trim().isBlank()){
            mensagem += "O campo \"Login\" deve ser preenchido!\n";
            sucesso = false;
        }else if(senha.trim().isBlank()){
            mensagem += "O campo \"Senha\" deve ser preenchido!\n";
            sucesso = false;
        }
        M_Usuario mUsuario = rUsuario.getusuarioByLoginSenha(matricula,senha);
//        M_Usuario mUsuario = rUsuario.getByMatricula(matricula);
        if(sucesso){
//            if (!passwordEncoder.matches(senha,mUsuario.getSenha()) || mUsuario == null){
//                mensagem += "Matrícula ou senha incorretos!\n";
//                sucesso = false;
//            }
            if (mUsuario == null){
                mensagem += "Matrícula ou senha incorretos!\n";
                sucesso = false;
            }
            else {
                mensagem += "Login realizado com sucesso!";
            }
        }

        return new M_Resposta(sucesso,mensagem,mUsuario);
    }
}
