package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_UserDetails;
import com.tcc.TCC.repository.R_Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Login {
    @Autowired
    private R_Usuario rUsuario;
    @Autowired
    private S_Jwt sJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final SecurityContextRepository contextRepository;

    public S_Login() {
        this.contextRepository = new HttpSessionSecurityContextRepository();
    }

    /**
     * @param matricula
     * @param senha
     * @return M_Resposta
     */
    public M_Resposta validaLogin(String matricula, String senha, HttpServletRequest servletRequest, HttpServletResponse servletResponse){
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
//        M_Usuario mUsuario = rUsuario.getusuarioByLoginSenha(matricula,senha);
////        M_Usuario mUsuario = rUsuario.getByMatricula(matricula);
//        if(sucesso){
////            if (!passwordEncoder.matches(senha,mUsuario.getSenha()) || mUsuario == null){
////                mensagem += "Matrícula ou senha incorretos!\n";
////                sucesso = false;
////            }
//            if (mUsuario == null){
//                mensagem += "Matrícula ou senha incorretos!\n";
//                sucesso = false;
//            }
//            else {
//                mensagem += "Login realizado com sucesso!";
//            }
//        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(matricula, senha));
        }catch (Exception e){
            mensagem += "Matrícula ou senha incorretos!\n";
            sucesso = false;
            return new M_Resposta(sucesso,mensagem);
        }
        if(!authentication.isAuthenticated()){
            mensagem += "Matrícula ou senha incorretos!\n";
            sucesso = false;
            return new M_Resposta(sucesso,mensagem);
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        contextRepository.saveContext(context,servletRequest,servletResponse);

        M_UserDetails userDetails = (M_UserDetails) authentication.getPrincipal();

        sJwt.generateToken(userDetails.getUsername());

        mensagem += "Login realizado com sucesso!";
        return new M_Resposta(sucesso,mensagem,userDetails.getUsuario());
    }
}
