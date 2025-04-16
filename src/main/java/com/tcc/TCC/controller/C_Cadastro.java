package com.tcc.TCC.controller;

import com.tcc.TCC.model.M_Resposta;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Cadastro {
    @GetMapping("/cadastro")
    public String getCadastro() {
        return "cadastro/cadastro";
    }

    @PostMapping("/gerar-cadastro")
    @ResponseBody
    public M_Resposta postGerarCadastro(@RequestParam("nome") String nome,
                                        @RequestParam("matricula") String matricula,
                                        @RequestParam("email") String email,
                                        @RequestParam("senha") String senha,
                                        @RequestParam("confsenha") String confsenha,
                                        @RequestParam("poder") String poder) {
        return null;
    }
}
