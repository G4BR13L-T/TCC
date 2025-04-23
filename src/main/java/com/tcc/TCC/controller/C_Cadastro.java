package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Cadastro;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Cadastro {
    private final S_Cadastro sCadastro;

    public C_Cadastro(S_Cadastro sCadastro) {
        this.sCadastro = sCadastro;
    }

    @GetMapping("/cadastro-usuario")
    public String getCadastroUsuario(HttpSession session,
                                     Model model) {
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null && (mUsuario.getPoder().getId() == 1 || mUsuario.getPoder().getId() == 2)) {
            model.addAttribute("usuario", mUsuario);
            return "cadastro/usuario";
        }
        return "redirect:/";
    }

    @PostMapping("/gerar-cadastro-user")
    @ResponseBody
    public M_Resposta postGerarCadastroUser(@RequestParam("nome") String nome,
                                            @RequestParam("matricula") String matricula,
                                            @RequestParam("email") String email,
                                            @RequestParam("senha") String senha,
                                            @RequestParam("confsenha") String confsenha,
                                            @RequestParam("poder") String poder) {
        return sCadastro.realizarCadastroUser(nome, matricula, email, senha, confsenha, poder);
    }

    @GetMapping("/cadastro-notebook")
    public String getCadastroNotebook(HttpSession session,
                                      Model model) {
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null && (mUsuario.getPoder().getId() == 1 || mUsuario.getPoder().getId() == 2 || mUsuario.getPoder().getId() == 3)) {
            model.addAttribute("usuario", mUsuario);
            return "cadastro/notebook";
        }
        return "redirect:/";
    }
    @PostMapping("/gerar-cadastro-note")
    @ResponseBody
    public M_Resposta postGerarCadastroNote(@RequestParam("numero")Integer numero,
                                            @RequestParam("codigo")String codigo){
        return sCadastro.realizarCadastroNote(numero, codigo);
    }
}
