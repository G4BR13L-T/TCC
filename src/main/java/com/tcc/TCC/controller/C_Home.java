package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Reserva;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Home {
    private final S_Home sHome;
    private Long id_reserva_atestada;

    public C_Home(S_Home sHome) {
        this.sHome = sHome;
    }

    @PostMapping("/cancelar-reserva")
    @ResponseBody
    public M_Resposta postCancelarReservas(@RequestParam("reservaId") Long id){
        return sHome.cancelReserve(id);
    }

    @PostMapping("/atestar-recebimento")
    @ResponseBody
    public boolean postAtestarRecebimento(@RequestParam("reservaId") Long id){
        id_reserva_atestada = id;
        return true;
    }

    @GetMapping("/atestar-reserva")
    public String getAtestamentoReserva(HttpSession session,
                                        Model model){
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null && (mUsuario.getPoder().getId() == 1 || mUsuario.getPoder().getId() == 2)) {
            model.addAttribute("reserva",sHome.getSpecificReserve(id_reserva_atestada));
            model.addAttribute("usuario",mUsuario);
            return "atestar/atestar";
        }
        return "redirect:/";
    }
}
