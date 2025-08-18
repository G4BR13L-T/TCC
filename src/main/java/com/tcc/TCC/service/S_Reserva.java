package com.tcc.TCC.service;

import com.tcc.TCC.model.*;
import com.tcc.TCC.repository.R_NotReserve;
import com.tcc.TCC.repository.R_Notebook;
import com.tcc.TCC.repository.R_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Autowired
    private S_EMailSender eMailSender;

    /**
     * After every minute verifies and, if necessary, changes the status of a reserve.
     * Depois de cada minuto verifica e, se necessário, muda o status de uma reserva.
     */
    // 1 - Em espera
    // 2 - Em andamento
    // 8 - Em atraso
    @Scheduled(cron = "0 * * * * ?")
    public void atualizarStatus() {
        List<M_Reserva> mReservas = rReserva.findAllInWaitOrCourse();
        M_Status emEspera = new M_Status(1l);
        M_Status emAndamento = new M_Status(2L);
        M_Status emAtraso = new M_Status(8L);
        for (M_Reserva mReserva : mReservas) {

            if (mReserva.getStatus().getId() == 1 || mReserva.getStatus().getId() == 2) {
                boolean modificado = false;
                String eMailSubject = "";
                String eMail = "";

                if (mReserva.getHorarioInicial().isAfter(LocalDateTime.now()) && mReserva.getStatus().getId() != 1) {
                    mReserva.setStatus(emEspera);
                    System.out.println("Reserva " + mReserva.getId() + " em espera!");
                    modificado = true;

                    eMailSubject = "Sua reserva está em espera";
                    eMail = "Olá " + mReserva.getUsuario().getNome() + ",\n\n" +
                            "Sua reserva [" + mReserva.getId() + "] está aguardando início.\n\n" +
                            "Detalhes:\n" +
                            "- Quantidade: " + mReserva.getQuantidade() + "\n" +
                            "- Horário inicial: " + mReserva.getHorarioInicial() + "\n" +
                            "- Horário final: " + mReserva.getHorarioFinal() + "\n\n" +
                            "Fique atento ao horário de retirada!";
                }

                if (!mReserva.getHorarioInicial().isAfter(LocalDateTime.now())
                        && mReserva.getHorarioFinal().plusMinutes(5l).isAfter(LocalDateTime.now()) && mReserva.getStatus().getId() != 2) {
                    mReserva.setStatus(emAndamento);
                    System.out.println("Reserva " + mReserva.getId() + " em andamento!");
                    modificado = true;

                    eMailSubject = "Sua reserva está em andamento";
                    eMail = "Olá " + mReserva.getUsuario().getNome() + ",\n\n" +
                            "Sua reserva [" + mReserva.getId() + "] já está em andamento.\n\n" +
                            "Horário inicial: " + mReserva.getHorarioInicial() + "\n" +
                            "Horário final: " + mReserva.getHorarioFinal() + "\n\n" +
                            "Retire e utilize seu notebook dentro do período.";
                }

                if (mReserva.getHorarioFinal().plusMinutes(5l).isBefore(LocalDateTime.now()) && mReserva.getStatus().getId() != 8) {
                    mReserva.setStatus(emAtraso);
                    System.out.println("Reserva " + mReserva.getId() + " em atraso!");
                    modificado = true;

                    eMailSubject = "Sua reserva está em atraso";
                    eMail = "Olá " + mReserva.getUsuario().getNome() + ",\n\n" +
                            "Sua reserva [" + mReserva.getId() + "] entrou em atraso.\n\n" +
                            "Horário inicial: " + mReserva.getHorarioInicial() + "\n" +
                            "Horário final: " + mReserva.getHorarioFinal() + "\n\n" +
                            "Por favor, regularize sua situação o quanto antes e entregue os notebooks na bilioteca.";
                }

                if (modificado) {
                    rReserva.saveAll(mReservas);
                    eMailSender.enviarEmailSimples(mReserva.getUsuario().getEmail(), eMailSubject, eMail);
                    System.out.println("Reserva " + mReserva.getId() + " atualizada e notificação enviada!");
                }
            }
        }
    }

    /**
     * @param quantidade
     * @param especifico
     * @param notebooks
     * @param session
     * @param horarioI
     * @param horarioF
     * @return M_Resposta
     */
    public M_Resposta reservar(Integer quantidade,
                               Boolean especifico,
                               String notebooks,
                               String horarioI,
                               String horarioF,
                               String observacao,
                               HttpSession session) {
        boolean sucesso = true;
        String mensagem = "";
        LocalDateTime dateS = null;
        LocalDateTime dateF = null;

        List<M_Notebook> mNotebooks = new ArrayList<>();
        try {
            dateS = LocalDateTime.parse(horarioI + ":00.000000");
            dateF = LocalDateTime.parse(dateS.toLocalDate().toString() + "T" + horarioF + ":00.000000");
            int qtdlivres = getAllFreeNotebooksInSpecificPeriod(dateS, dateF.toLocalTime()).size();

            if (dateF.isBefore(dateS)) {
                mensagem += "O Horario Final não pode ser antes do Inicial\n";
                sucesso = false;
            }
            if (dateF.isBefore(LocalDateTime.now())) {
                mensagem += "O horario final não pode ser antes do horário atual!\n";
                sucesso = false;
            }

            if (quantidade > qtdlivres) {
                sucesso = false;
                mensagem += "A quantidade a ser reservada deve estar dentro do limite, " +
                        "o qual atualmente é: " + qtdlivres + "\n";
            }
        } catch (Exception e) {
            sucesso = false;
        }

        if (horarioI.isEmpty() || horarioF.isEmpty()) {
            mensagem += "O horário não pode ser vazio!\n";
            sucesso = false;
        }

        if (quantidade < 1) {
            mensagem += "A quantidade deve ser maior do que 0!\n";
            sucesso = false;
        }

        if (especifico == null) {
            mensagem += "A especificidade não pode ser nula!\n";
            sucesso = false;
        }
        if (dateS == null || dateF == null) {
            mensagem += "As reservas não podem estar vazias!\n";
            sucesso = false;
        }
        if (sucesso) {
            try {
                if (especifico) {
                    String[] notes = notebooks.split(";");
                    if (!notes[0].isEmpty()) {
                        for (String note : notes) mNotebooks.add(rNotebook.getNoteById(Long.parseLong(note)));
                    }
                    if (notes.length < quantidade) {
                        List<M_Notebook> livres = getAllFreeNotebooksInSpecificPeriod(dateS, dateF.toLocalTime());
                        for (int i = 0; i < quantidade; i++) {
                            if (!mNotebooks.contains(livres.get(i))) mNotebooks.add(livres.get(i));
                            if (mNotebooks.size() == livres.size()) break;
                            if (mNotebooks.size() >= quantidade) break;
                        }
                    }
                } else {
                    List<M_Notebook> livres = getAllFreeNotebooksInSpecificPeriod(dateS, dateF.toLocalTime());
                    for (int i = 0; i < quantidade; i++) {
                        mNotebooks.add(livres.get(i));
                        if (mNotebooks.size() == livres.size()) break;
                        if (mNotebooks.size() >= quantidade) break;
                    }
                }
                M_Status status = new M_Status();
                if(dateS.isAfter(LocalDateTime.now())) {
                    status.setId(1L);
                }else {
                    status.setId(2L);
                }
                M_Reserva reserva = new M_Reserva();
                reserva.setEspecifico(especifico);
                reserva.setUsuario((M_Usuario) session.getAttribute("usuario"));
                reserva.setQuantidade(quantidade);
                reserva.setHorarioInicial(dateS);
                reserva.setHorarioFinal(dateF);
                reserva.setStatus(status);
                reserva.setDevolvidos(0);
                reserva.setDefeitos(0);
                reserva.setObservacoes(observacao);
                rReserva.save(reserva);
                for (M_Notebook note : mNotebooks) {
                    M_NotReserve mNotReserve = new M_NotReserve();
                    mNotReserve.setReserva(reserva);
                    mNotReserve.setNotebook(note);
                    rNotReserve.save(mNotReserve);
                }
                mensagem += "Reserva realizada com sucesso!\n";
                String eMailSubject = "Reserva Realizada";
                String eMail =
                        "Olá, " + reserva.getUsuario().getNome() + ",\n\n" +
                            "Sua reserva [" + reserva.getId() + "] foi realizada com sucesso!\n\n" +
                            "Detalhes da reserva:\n" +
                            "- Quantidade de notebooks: " + reserva.getQuantidade() + "\n" +
                            "- Horário inicial: " + reserva.getHorarioInicial() + "\n" +
                            "- Horário final: " + reserva.getHorarioFinal() + "\n" +
                            (reserva.getObservacoes() != null && !reserva.getObservacoes().isEmpty()
                                    ? "- Observações: " + reserva.getObservacoes() + "\n\n"
                                    : "\n") +
                            "Quando chegar o horário de retirada, você receberá um novo e-mail de aviso.\n\n" +
                            "Obrigado por utilizar o sistema NotReserve!";
                eMailSender.enviarEmailSimples(reserva.getUsuario().getEmail(), eMailSubject, eMail);
            } catch (Exception e) {
                System.err.println("" + e);
                mensagem += "Erro interno durante o precesso de reserva!\n";
                sucesso = false;
            }
        }
        return new M_Resposta(sucesso, mensagem);
    }

    /**
     * @param data_de_inicio
     * @param horario_de_fim
     * @return notbooks livres entre as duas datas
     */
    public List<M_Notebook> getAllFreeNotebooksInSpecificPeriod(LocalDateTime data_de_inicio,
                                                                LocalTime horario_de_fim) {
        LocalDateTime dataF = LocalDateTime.parse(data_de_inicio.toLocalDate().toString() + "T" + horario_de_fim);
        return rNotebook.findAllFreeInSpecificPeriod(data_de_inicio, dataF);
    }
}
