package com.tcc.TCC.service;

import com.tcc.TCC.model.M_Notebook;
import com.tcc.TCC.model.M_Resposta;
import com.tcc.TCC.repository.R_Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Inativar {
    @Autowired
    R_Notebook rNotebook;

    public List<M_Notebook> encontraNotebooksAtivos(){
        return rNotebook.findAllActive();
    }

    public M_Resposta inativar (Long idNote){
        boolean sucesso = true;
        String mensagem = "";
        try {
            M_Notebook mNotebook = rNotebook.getNoteById(idNote);
            mNotebook.setAtivo(false);
            rNotebook.save(mNotebook);
            mensagem += "Notebook inativado com sucesso!\n";
        }catch (Exception e){
            sucesso = false;
            mensagem += "Erro interno durante o processo de inativação de notebook!\n";
        }
        return new M_Resposta(sucesso,mensagem);
    }
}
