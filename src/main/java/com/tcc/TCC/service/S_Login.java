package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Login {
    @Autowired
    private R_Usuario rUsuario;
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
        M_Usuario mUsuario = null;
        if(sucesso){
            mUsuario = rUsuario.getusuarioByLoginSenha(matricula, senha);
            mensagem += "Login realizado com sucesso!";
        }
        if(mUsuario == null && sucesso){
            sucesso = false;
            mensagem = "Matrícula ou senha incorretos!";
        }
        M_Resposta resposta = new M_Resposta();
        resposta.setMensagem(mensagem);
        resposta.setSucesso(sucesso);
        resposta.setObject(mUsuario);
        return resposta;
    }
    public List<M_Usuario> getAllUsuario(){
        return rUsuario.findAll();
    }
}
