package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Reserva;
import com.tcc.TCC.model.M_Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Reserva extends JpaRepository<M_Reserva, Long> {

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where cast(horario_inicial as date) = current_date " +
            "and id_status != 3 " +
            "order by horario_inicial", nativeQuery = true)
    List<M_Reserva> findAllOfToday();

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where horario_inicial > now() " +
            "order by horario_inicial", nativeQuery = true)
    List<M_Reserva> findAllFuture();

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where id = :id", nativeQuery = true)
    M_Reserva findElementById(@Param("id")Long id);
}
