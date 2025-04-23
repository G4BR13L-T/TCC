package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_NivelPoder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_NivelPoder extends JpaRepository<M_NivelPoder,Long> {
}
