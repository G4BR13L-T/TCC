package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_NotReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_NotReserve extends JpaRepository<M_NotReserve, Long> {
    @Query(value = "select * " +
            "from tcc.notreserve " +
            "where id_reserva = :id", nativeQuery = true)
    List<M_NotReserve> getAllByIdReserva(@Param("id") Long id);
}
