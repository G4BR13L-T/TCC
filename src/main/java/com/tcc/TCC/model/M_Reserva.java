package com.tcc.TCC.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reserva", schema = "tcc")
public class M_Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario"), nullable = false)
    private M_Usuario usuario;
    private Integer quantidade;
    private Boolean especifico;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;
    @ManyToOne
    @JoinColumn(name = "id_status", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_status"), nullable = false)
    private M_Status status;
    private Integer devolvidos;
    private Integer defeitos;

    @OneToMany(mappedBy = "reserva")
    private List<M_NotReserve> reservaNotes;

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
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getEspecifico() {
        return especifico;
    }

    public void setEspecifico(Boolean especifico) {
        this.especifico = especifico;
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

    public List<M_NotReserve> getReservaNotes() {
        return reservaNotes;
    }

    public void setReservaNotes(List<M_NotReserve> reservaNotes) {
        this.reservaNotes = reservaNotes;
    }

    public M_Status getStatus() {
        return status;
    }

    public void setStatus(M_Status status) {
        this.status = status;
    }

    public Integer getDevolvidos() {
        return devolvidos;
    }

    public void setDevolvidos(Integer devolvidos) {
        this.devolvidos = devolvidos;
    }

    public Integer getDefeitos() {
        return defeitos;
    }

    public void setDefeitos(Integer defeitos) {
        this.defeitos = defeitos;
    }
}
