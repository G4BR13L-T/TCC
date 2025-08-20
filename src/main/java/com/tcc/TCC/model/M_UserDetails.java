package com.tcc.TCC.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class M_UserDetails implements UserDetails {
    private final M_Usuario usuario;
    private final LocalDateTime registro;

    public M_UserDetails(M_Usuario usuario) {
        this.usuario = usuario;
        registro = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("PROFESSOR"));
        if (usuario.getPoder().getId() <= 2L){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("BIBLIOTECARIO"));
            if (usuario.getPoder().getId() == 1L) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            }
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getMatricula();
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDateTime.now().isBefore(registro.plusMinutes(30L));
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public M_Usuario getUsuario(){
        return usuario;
    }
}
