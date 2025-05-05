package com.tcc.TCC.service;

import com.tcc.TCC.model.*;
import com.tcc.TCC.repository.R_NotReserve;
import com.tcc.TCC.repository.R_Notebook;
import com.tcc.TCC.repository.R_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class S_Reserva {
    @Autowired
    private R_Notebook rNotebook;
    @Autowired
    private R_Reserva rReserva;
    @Autowired
    R_NotReserve rNotReserve;

    public List<M_Notebook> getAllNotebooks() {
        return rNotebook.findEvery();
    }

    public List<M_Notebook> getAllFreeNotebooks() {
        return rNotebook.getAllNotReserved();
    }

    public M_Resposta reservar(Integer quantidade,
                               Boolean especifico,
                               String notebooks,
                               HttpSession session) {
        boolean sucesso = true;
        String mensagem = "";

        if (quantidade < 1) {
            mensagem += "A quantidade deve ser maior do que 0!\n";
            sucesso = false;
        }
        if (especifico == null) {
            mensagem += "A especificidade não pode ser nula!\n";
            sucesso = false;
        }

        if (sucesso) {
            String[] notes = notebooks.split(";");
            List<M_Notebook> mnotes = new ArrayList<>();
            if (!notes[0].isEmpty()) {
                for (String note : notes) {
                    Long id = Long.parseLong(note);
                    M_Notebook notebook = rNotebook.getNoteById(id);
                    notebook.setReservado(true);
                    mnotes.add(notebook);
                    rNotebook.save(notebook);
                }
            }
            System.out.println(mnotes);
            try {
                M_Reserva reserva = new M_Reserva();
                reserva.setEspecifico(especifico);
                reserva.setUsuario((M_Usuario) session.getAttribute("usuario"));
                reserva.setQuantidade(quantidade);
                reserva.setHorario(LocalDateTime.now());
                rReserva.save(reserva);
                for (M_Notebook note: mnotes) {
                    M_NotReserve mNotReserve = new M_NotReserve();
                    mNotReserve.setReserva(reserva);
                    mNotReserve.setNotebook(note);
                    rNotReserve.save(mNotReserve);
                }
                mensagem += "Reserva realizada com sucesso!\n";
            } catch (Exception e) {
                System.err.println(e);
                mensagem += "Erro interno durante o precesso de reserva!\n";
                sucesso = false;
            }
        }
        return new M_Resposta(sucesso, mensagem);
    }
}
