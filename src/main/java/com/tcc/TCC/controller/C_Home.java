package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Reserva;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Home {
    private final S_Home sHome;

    public C_Home(S_Home sHome) {
        this.sHome = sHome;
    }

    @PostMapping("/cancelar-reserva")
    @ResponseBody
    public M_Resposta postCancelarReservas(@RequestParam("reservaId") Long id){
        return sHome.cancelReserve(id);
    }

    @PostMapping("/atestar-recebimento")
    public String postAtestarRecebimento(@RequestParam("reservaId") Long id,
                                         HttpSession session,
                                         Model model){
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null && (mUsuario.getPoder().getId() == 1 || mUsuario.getPoder().getId() == 2)) {
            model.addAttribute("usuario", mUsuario);
            model.addAttribute("reserva", sHome.getSpecificReserve(id));
            return "atestar/atestar";
        }
        return "redirect:/";
    }
}
