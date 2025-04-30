package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_Notebook extends JpaRepository<M_Notebook,Long> {
    @Query(value = "select * " +
            "from tcc.notebook " +
            "where not reservado " +
            "order by numero",nativeQuery = true)
    List<M_Notebook> getAllNotReserved();
}
