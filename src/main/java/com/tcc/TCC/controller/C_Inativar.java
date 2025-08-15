package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Inativar;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Inativar {
    private final S_Inativar sInativar;

    public C_Inativar(S_Inativar sInativar) {
        this.sInativar = sInativar;
    }

    @GetMapping("/inativar-notebook")
    public String getInativarNotebook(HttpSession session,
                                      Model model){
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null && (mUsuario.getPoder().getId() == 1 || mUsuario.getPoder().getId() == 2)) {
            model.addAttribute("usuario", mUsuario);
            model.addAttribute("notebooks", sInativar.encontraNotebooksAtivos());
            return "inativar/inativar";
        }
        return "redirect:/";
    }

    @PostMapping("/inativar")
    @ResponseBody
    public M_Resposta postInativarNotebook(@RequestParam("idNotebook") Long idNote){
        return sInativar.inativar(idNote);
    }
}
