package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Notebook;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.service.S_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class C_Reserva {
    private final S_Reserva sReserva;

    public C_Reserva(S_Reserva sReserva) {
        this.sReserva = sReserva;
    }

    @GetMapping("/reserva-notebook")
    public String getReservaNotebook(HttpSession session,
                                     Model model) {
        M_Usuario mUsuario = (M_Usuario) session.getAttribute("usuario");
        if (mUsuario != null) {
            model.addAttribute("usuario", mUsuario);
            model.addAttribute("today", LocalDateTime.now().minusNanos(LocalDateTime.now().getNano())
                    .minusSeconds(LocalDateTime.now().getSecond()));
            model.addAttribute("free", sReserva.getAllFreeNotebooks());
            return "reserva/reserva";
        }
        return "redirect:/";
    }

    @PostMapping("/reservar")
    @ResponseBody
    public M_Resposta postReserva(@RequestParam("quantidade") Integer quantidade,
                                  @RequestParam("especifico") Boolean especifico,
                                  @RequestParam("notebooks") String notebooks,
                                  @RequestParam("horario") String horario,
                                  HttpSession session) {
        return sReserva.reservar(quantidade, especifico, notebooks, session, horario);
    }

    @PostMapping("notes-disponiveis")
    public String postNotesDisponiveis(@RequestParam("data") LocalDateTime data,
                                                 Model model) {
        model.addAttribute("free", sReserva.getAllFreeNotebooksInSpecificDate(data));
        return "reserva/partial-view/checkbox";
    }
}
