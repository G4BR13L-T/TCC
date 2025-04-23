package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Login;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Login {
    private final S_Login sLogin;

    public C_Login(S_Login sLogin) {
        this.sLogin = sLogin;
    }

    @GetMapping("/")
    public String getHome(HttpSession session,
                          Model model) {
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null) {
            model.addAttribute("usuario",mUsuario);
            return "home/home";
        }
        return "index";
    }

    @PostMapping("/valida-login")
    @ResponseBody
    public M_Resposta postlogin(@RequestParam("matricula") String matricula,
                                @RequestParam("senha") String senha,
                                HttpSession session) {
        M_Resposta response = sLogin.validaLogin(matricula, senha);
        if (response.getObject() != null) {
            session.setAttribute("usuario", response.getObject());
        }
        return response;
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean getLogout(HttpSession session) {
        session.setAttribute("usuario", null);
        return true;
    }
}
