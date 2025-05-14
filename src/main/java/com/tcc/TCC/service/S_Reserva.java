package com.tcc.TCC.service;

import com.tcc.TCC.model.*;
import com.tcc.TCC.repository.R_NotReserve;
import com.tcc.TCC.repository.R_Notebook;
import com.tcc.TCC.repository.R_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * @param quantidade
     * @param especifico
     * @param notebooks
     * @param session
     * @param horario
     * @return M_Resposta
     */
    public M_Resposta reservar(Integer quantidade,
                               Boolean especifico,
                               String notebooks,
                               HttpSession session,
                               String horario) {
        boolean sucesso = true;
        String mensagem = "";
        LocalDateTime date = LocalDateTime.parse(horario + ":00.000000");
        int qtdlivres = getAllFreeNotebooksInSpecificDate(date).size();
        List<M_Notebook> mNotebooks = new ArrayList<>();

        if (horario.isEmpty()) {
            mensagem += "O horário não pode ser vazio!\n";
            sucesso = false;
        }

        if (quantidade < 1) {
            mensagem += "A quantidade deve ser maior do que 0!\n";
            sucesso = false;
        }

        if (quantidade > qtdlivres) {
            sucesso = false;
            mensagem += "A quantidade a ser reservada deve estar dentro do limite, " +
                    "o qual atualmente é: " + qtdlivres + "\n";
        }

        if (especifico == null) {
            mensagem += "A especificidade não pode ser nula!\n";
            sucesso = false;
        }

        if (sucesso) {
            try {
                if (especifico) {
                    String[] notes = notebooks.split(";");
                    if (!notes[0].isEmpty()) {
                        for (String note : notes) mNotebooks.add(rNotebook.getNoteById(Long.parseLong(note)));
                    }
                } else {
                    List<M_Notebook> livres = getAllFreeNotebooksInSpecificDate(date);
                    for (int i = 0; i < quantidade; i++) {
                        mNotebooks.add(livres.get(i));
                        if (mNotebooks.size() == livres.size()) break;
                    }
                }
                M_Reserva reserva = new M_Reserva();
                reserva.setEspecifico(especifico);
                reserva.setUsuario((M_Usuario) session.getAttribute("usuario"));
                reserva.setQuantidade(quantidade);
                reserva.setHorarioInicial(date);
                rReserva.save(reserva);
                for (M_Notebook note : mNotebooks) {
                    M_NotReserve mNotReserve = new M_NotReserve();
                    mNotReserve.setReserva(reserva);
                    mNotReserve.setNotebook(note);
                    rNotReserve.save(mNotReserve);
                }
                mensagem += "Reserva realizada com sucesso!\n";
            } catch (Exception e) {
                System.err.println("" + e);
                mensagem += "Erro interno durante o precesso de reserva!\n";
                sucesso = false;
            }
        }
        return new M_Resposta(sucesso, mensagem);
    }

    /**
     * @param data
     * @return Lista de Notebooks em date-time específico
     */
    public List<M_Notebook> getAllFreeNotebooksInSpecificDate(LocalDateTime data) {
        return rNotebook.findAllFreeInSpecificDate(data);
    }
}
