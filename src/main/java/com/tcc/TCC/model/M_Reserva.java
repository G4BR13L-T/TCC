package com.tcc.TCC.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva", schema = "tcc")
public class M_Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario"), nullable = false)
    private M_Usuario usuario;
    private String notebooks;
    private Integer quantidade;
    private Boolean especifico;
    private LocalDateTime horario;

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

    public String getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(String notebooks) {
        this.notebooks = notebooks;
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

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
}
