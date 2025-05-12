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
    public Object getAllReservasAtuais(M_Usuario mUsuario){
        List<M_Reserva> mReservas = rReserva.findAllOfToday();
        List<M_NotReserve> mNotReservas = new ArrayList<>();
        M_Usuario anonimo = new M_Usuario();
        anonimo.setId(-1L);
        anonimo.setNome("Anônimo");
        anonimo.setMatricula(null);
        anonimo.setEmail(null);
        anonimo.setSenha(null);
        anonimo.setPoder(null);
        if (mReservas != null) for (M_Reserva mReserva: mReservas) {
            if(mUsuario != mReserva.getUsuario()){
                mReserva.setUsuario(anonimo);
            }
            mNotReservas.addAll(rNotReserve.getAllByIdReserva(mReserva.getId()));
        }
        List<Object> response = new ArrayList<>();
        response.add(mReservas);
        response.add(mNotReservas);
        return response;
    }
}
