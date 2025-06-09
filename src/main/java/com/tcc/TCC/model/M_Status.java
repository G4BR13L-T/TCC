package com.tcc.TCC.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status", schema = "tcc")
public class M_Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    public M_Status() {
        id = null;
        status = "";
    }

    public M_Status(Long id) {
        this.id = id;
        status = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
