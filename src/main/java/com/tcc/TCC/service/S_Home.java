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
     * @param mUsuario
     * @return Reservas atuais
     */
    public Object getAllReservasAtuais(M_Usuario mUsuario){
        List<M_Reserva> mReservas = rReserva.findAllOfToday();
        M_Usuario anonimo = new M_Usuario();
        anonimo.setId(-1L);
        anonimo.setNome("Anônimo");
        anonimo.setMatricula(null);
        anonimo.setEmail(null);
        anonimo.setSenha(null);
        anonimo.setPoder(null);
        if (mReservas != null) for (M_Reserva mReserva: mReservas) {
            if(!mUsuario.getNome().equals(mReserva.getUsuario().getNome())){
                mReserva.setUsuario(anonimo);
            }
        }
        return mReservas;
    }

    /**
     * @param mUsuario
     * @return Reservas futuras
     */
    public Object getAllReservasFuturas(M_Usuario mUsuario){
        List<M_Reserva> mReservas = rReserva.findAllFuture();
        M_Usuario anonimo = new M_Usuario();
        anonimo.setId(-1L);
        anonimo.setNome("Anônimo");
        anonimo.setMatricula(null);
        anonimo.setEmail(null);
        anonimo.setSenha(null);
        anonimo.setPoder(null);
        if (mReservas != null) for (M_Reserva mReserva: mReservas) {
            if(!mUsuario.getNome().equals(mReserva.getUsuario().getNome())){
                mReserva.setUsuario(anonimo);
            }
        }
        return mReservas;
    }
}
