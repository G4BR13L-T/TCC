package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Notebook;
import com.tcc.TCC.model.M_Reserva;
import com.tcc.TCC.repository.R_Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class S_Reserva {
    @Autowired
    private R_Notebook rNotebook;

    public List<M_Notebook> getAllNotebooks(){
        return rNotebook.findAll();
    }
    public List<M_Notebook> getAllFreeNotebooks(){
        return rNotebook.getAllNotReserved();
    }

    public M_Reserva reservar(Integer quantidade,
                              Boolean especifico,
                              String notebooks){
        boolean sucesso = true;
        String mensagem = "";

        if(quantidade < 1){
            mensagem += "A quantidade deve ser maior do que 0!\n";
            sucesso = false;
        }
        if(especifico == null){
            mensagem += "A especificidade não pode ser nula!\n";
            sucesso = false;
        }
        if(notebooks.isEmpty()){
            notebooks = "Não especificado!";
        }else{
            String[] notes = notebooks.split(";");
            List<Long> notesid = new ArrayList<>();
            for (String note:
                 notes) {
                notesid.add(Long.parseLong(note));
            }
        }
        return new M_Reserva();
    }
}
