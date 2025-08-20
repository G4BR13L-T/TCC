package com.tcc.TCC.service;

import com.tcc.TCC.model.M_UserDetails;
import com.tcc.TCC.model.M_Usuario;
import com.tcc.TCC.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class S_UserDetails implements UserDetailsService {
    @Autowired
    private R_Usuario rUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        M_Usuario mUsuario = rUsuario.getByMatricula(username);
        if (mUsuario == null) {
            throw new UsernameNotFoundException(username + "! Não foi encontrado!");
        }
        return new M_UserDetails(mUsuario);
    }
}
