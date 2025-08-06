package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Reserva;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Status;
import com.tcc.TCC.repository.R_Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Atestar {
    @Autowired
    private R_Reserva rReserva;

    public M_Resposta atestReserve(Long idReserva,
                                   Long idStatus,
                                   Integer devolvidos,
                                   Integer defeitos){
        boolean sucesso = true;
        String mensagem = "";
        if (idStatus == 4 || idStatus == 6 || idStatus == 9 || idStatus == 11){
            devolvidos = rReserva.getReferenceById(idReserva).getQuantidade();
        }
        try{
            M_Reserva mReserva = rReserva.getReferenceById(idReserva);
            if(mReserva.getStatus().getId() == 2 || mReserva.getStatus().getId() == 8){
                M_Status status = new M_Status();
                status.setId(idStatus);
                mReserva.setStatus(status);
                mReserva.setDefeitos(defeitos);
                mReserva.setDevolvidos(devolvidos);
                rReserva.save(mReserva);
                mensagem += "Atestamento realizado com sucesso";
            }
        }catch (Exception e){
            System.err.println(e);
            sucesso = false;
            mensagem+= "Erro interno durante o atestamento da reserva!\n";
        }
        return new M_Resposta(sucesso,mensagem);
    }
}
