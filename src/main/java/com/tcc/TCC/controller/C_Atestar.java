package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.service.S_Atestar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Atestar {
    private final S_Atestar sAtestar;

    public C_Atestar(S_Atestar sAtestar) {
        this.sAtestar = sAtestar;
    }

    @PostMapping("/atestar")
    @ResponseBody
    public M_Resposta postAtestar(@RequestParam("idReserva") Long idReserva,
                                  @RequestParam("idStatus") Long idStatus,
                                  @RequestParam("qtdDevolvidos") Integer devolvidos,
                                  @RequestParam("qtdDefeitos") Integer defeitos){
        return sAtestar.atestReserve(idReserva, idStatus, devolvidos, defeitos);
    }
}
