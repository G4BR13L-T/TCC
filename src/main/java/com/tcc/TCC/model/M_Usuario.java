package com.tcc.TCC.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario", schema = "tcc")
public class M_Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String matricula;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private String email;
    @ManyToOne
    @JoinColumn(name = "id_poder", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fkpoder"), nullable = false)
    private M_NivelPoder poder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public M_NivelPoder getPoder() {
        return poder;
    }

    public void setPoder(M_NivelPoder poder) {
        this.poder = poder;
    }
}
