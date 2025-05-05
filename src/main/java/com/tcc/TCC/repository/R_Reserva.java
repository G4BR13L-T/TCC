package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Reserva extends JpaRepository<M_Reserva,Long> {
}
