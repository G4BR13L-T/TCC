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
}
