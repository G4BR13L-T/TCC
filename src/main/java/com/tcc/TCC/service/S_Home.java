package com.tcc.TCC.service;

import com.tcc.TCC.model.*;
import com.tcc.TCC.repository.R_NotReserve;
import com.tcc.TCC.repository.R_Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class S_Home {
    @Autowired
    private R_Reserva rReserva;
    @Autowired
    private R_NotReserve rNotReserve;

    /**
     * @return Reservas atuais
     */
    public Object getAllReservasAtuais(){
        List<M_Reserva> mReservas = rReserva.findAllOfToday();
        List<Object> finale = new ArrayList<>();
        for (M_Reserva res: mReservas) {
            finale.add(res);
            boolean calculavel = false;
            if(res.getHorarioInicial().isAfter(LocalDateTime.now().plusHours(1L))) calculavel = true;
            finale.add(calculavel);
        }
        return finale;
    }

    /**
     * @return Reservas futuras
     */
    public Object getAllReservasFuturas(){
        List<M_Reserva> mReservas = rReserva.findAllFuture();
        return mReservas;
    }
}
