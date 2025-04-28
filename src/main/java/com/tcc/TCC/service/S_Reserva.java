package com.tcc.TCC.service;

import com.tcc.TCC.repository.R_Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Reserva {
    @Autowired
    private R_Notebook rNotebook;

    public Integer getAllNotReserved(){
        return rNotebook.getAllNotReserved().size();
    }
}
