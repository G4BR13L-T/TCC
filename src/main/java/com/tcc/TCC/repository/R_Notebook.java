package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Notebook extends JpaRepository<M_Notebook,Long> {
}
