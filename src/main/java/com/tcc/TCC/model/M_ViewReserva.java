package com.tcc.TCC.model;

import java.time.LocalDateTime;
import java.util.List;

public class M_ViewReserva {
    private Long id;
    private M_Usuario usuario;
    private List<M_NotReserve> reservaNotes;
    private Integer quantidade;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;
    private M_Status status;
    private String observacao;
    private boolean cancelavel;

    private boolean atestavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public M_Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(M_Usuario usuario) {
        this.usuario = usuario;
    }

    public List<M_NotReserve> getReservaNotes() {
        return reservaNotes;
    }

    public void setReservaNotes(List<M_NotReserve> reservaNotes) {
        this.reservaNotes = reservaNotes;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(LocalDateTime horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(LocalDateTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public M_Status getStatus() {
        return status;
    }

    public void setStatus(M_Status status) {
        this.status = status;
    }

    public boolean isCancelavel() {
        return cancelavel;
    }

    public void setCancelavel(boolean cancelavel) {
        this.cancelavel = cancelavel;
    }

    public boolean isAtestavel() {
        return atestavel;
    }

    public void setAtestavel(boolean atestavel) {
        this.atestavel = atestavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
