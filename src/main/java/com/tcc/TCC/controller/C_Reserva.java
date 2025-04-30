package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Reserva {
    private final S_Reserva sReserva;

    public C_Reserva(S_Reserva sReserva) {
        this.sReserva = sReserva;
    }

    @GetMapping("/reserva-notebook")
    public String getReservaNotebook (HttpSession session,
                                      Model model){
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null) {
            model.addAttribute("usuario", mUsuario);
            model.addAttribute("notebooks", sReserva.getAllNotebooks());
            model.addAttribute("free", sReserva.getAllFreeNotebooks());
            return "reserva/reserva";
        }
        return "redirect:/";
    }

    @PostMapping("/reservar")
    @ResponseBody
    public M_Resposta postReserva(@RequestParam("quantidade") Integer quantidade,
                                  @RequestParam("especifico") Boolean especifico,
                                  @RequestParam("notebooks") String notebooks){

        return new M_Resposta();
    }
}
