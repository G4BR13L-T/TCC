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
            M_ViewReserva mViewReserva = new M_ViewReserva();
            mViewReserva.setId(res.getId());
            mViewReserva.setUsuario(res.getUsuario());
            mViewReserva.setReservaNotes(res.getReservaNotes());
            mViewReserva.setQuantidade(res.getQuantidade());
            mViewReserva.setHorarioInicial(res.getHorarioInicial());
            mViewReserva.setHorarioFinal(res.getHorarioFinal());
            mViewReserva.setStatus(res.getStatus());
            mViewReserva.setObservacao(res.getObservacoes());
            boolean cancelavel = false;
            boolean atestavel = false;
            if(res.getHorarioInicial().isAfter(LocalDateTime.now().plusMinutes(30L))) cancelavel = true;
            if(res.getHorarioInicial().isBefore(LocalDateTime.now())) atestavel = true;
            mViewReserva.setCancelavel(cancelavel);
            mViewReserva.setAtestavel(atestavel);
            finale.add(mViewReserva);
        }
        return finale;
    }

    /**
     * @return Reservas futuras
     */
    public Object getAllReservasFuturas(){
        List<M_Reserva> mReservas = rReserva.findAllFuture();
        List<Object> finale = new ArrayList<>();
        for (M_Reserva res: mReservas) {
            M_ViewReserva mViewReserva = new M_ViewReserva();
            mViewReserva.setId(res.getId());
            mViewReserva.setUsuario(res.getUsuario());
            mViewReserva.setReservaNotes(res.getReservaNotes());
            mViewReserva.setQuantidade(res.getQuantidade());
            mViewReserva.setHorarioInicial(res.getHorarioInicial());
            mViewReserva.setHorarioFinal(res.getHorarioFinal());
            mViewReserva.setObservacao(res.getObservacoes());
            boolean cancelavel = false;
            boolean atestavel = false;
            if(res.getHorarioInicial().isAfter(LocalDateTime.now().plusMinutes(30L))) cancelavel = true;
            if(res.getHorarioInicial().isBefore(LocalDateTime.now())) atestavel = true;
            mViewReserva.setCancelavel(cancelavel);
            mViewReserva.setAtestavel(atestavel);
            finale.add(mViewReserva);
        }
        return finale;
    }
    public M_Resposta cancelReserve(Long id){
        boolean sucesso = true;
        String mensagem = "";
        try {
            M_Reserva mReserva = rReserva.findElementById(id);
            if(mReserva.getStatus().getId() == 1){
                M_Status status = new M_Status();
                status.setId(3L);
                mReserva.setStatus(status);
                mReserva.setHorarioFinal(LocalDateTime.now());
                rReserva.save(mReserva);
                mensagem += "Cancelamento realizado com sucesso!\n";
            }
        }catch (Exception e){
            System.err.println(e);
            sucesso = false;
            mensagem+= "Erro interno durante o cancelamento da reserva!\n";
        }
        return new M_Resposta(sucesso,mensagem);
    }
    public Object getSpecificReserve(Long id){
        M_Reserva res = rReserva.findElementById(id);
        M_ViewReserva mViewReserva = new M_ViewReserva();
        mViewReserva.setId(res.getId());
        mViewReserva.setUsuario(res.getUsuario());
        mViewReserva.setReservaNotes(res.getReservaNotes());
        mViewReserva.setQuantidade(res.getQuantidade());
        mViewReserva.setHorarioInicial(res.getHorarioInicial());
        mViewReserva.setHorarioFinal(res.getHorarioFinal());
        mViewReserva.setStatus(res.getStatus());
        boolean cancelavel = false;
        boolean atestavel = false;
        if(res.getHorarioInicial().isAfter(LocalDateTime.now().plusMinutes(30L))) cancelavel = true;
        if(res.getHorarioInicial().isBefore(LocalDateTime.now())) atestavel = true;
        mViewReserva.setCancelavel(cancelavel);
        mViewReserva.setAtestavel(atestavel);

        return mViewReserva;
    }
}
