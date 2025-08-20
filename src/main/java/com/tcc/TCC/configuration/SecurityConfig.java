package com.tcc.TCC.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] publico = new String[]{
                "/", "/logout", // GET
                "/js/index.js", "/js/home.js", "/js/navbar.js",// JS
                "/css/geral.css", // CSS
                "/valida-login" // POST
        };
        String[] professor = new String[]{
                "/reserva-notebook", // GET
                "/js/reserva.js", // JS
                "/notes-disponiveis", "/reservar" // POST
        };
        String[] bibliotecario = new String[]{
                "/cadastro-usuario", "/cadastro-notebook", "/inativar-notebook", "/atestar-reserva", // GET
                "/js/cadastroU.js", "/js/cadastroN.js", "/js/inativar.js", "/js/atestar.js", // JS
                "/cancelar-reserva", "/atestar-recebimento", "/gerar-cadastro-user", "/gerar-cadastro-note", //POST
                "/inativar", "/atestar"
        };
//        String[] admin = new String[]{
//                ""
//        };
        return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(publico).permitAll()
                        .requestMatchers(professor).hasAuthority("PROFESSOR")
                        .requestMatchers(bibliotecario).hasAuthority("BIBLIOTECARIO")
//                        .requestMatchers(admin).hasAuthority("ADMIN") // Descomentar quando tiver algo que apenas admin pode fazer
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
