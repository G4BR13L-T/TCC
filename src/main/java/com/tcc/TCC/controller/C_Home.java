package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.service.S_Home;
import org.springframework.stereotype.Controller;
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
}
