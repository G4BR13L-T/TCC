package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_NotReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_NotReserve extends JpaRepository<M_NotReserve, Long> {
}
