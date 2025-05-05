package com.tcc.TCC.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table(name = "notreserve", schema = "tcc")
public class M_NotReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reserva"), nullable = false)
    private M_Reserva reserva;
    @ManyToOne()
    @JoinColumn(name = "id_notebook", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_notebook"), nullable = false)
    private M_Notebook notebook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public M_Reserva getReserva() {
        return reserva;
    }

    public void setReserva(M_Reserva reserva) {
        this.reserva = reserva;
    }

    public M_Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(M_Notebook notebook) {
        this.notebook = notebook;
    }
}
