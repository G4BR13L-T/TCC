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
            "where (cast(horario_inicial as date) = current_date or id_status = 8) " +
            "and id_status != 3 " +
            "and (id_status = 1 or id_status = 2 or id_status = 8) " +
            "order by horario_inicial", nativeQuery = true)
    List<M_Reserva> findAllOfToday();

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where horario_inicial > now() " +
            "and id_status != 3 " +
            "and (id_status = 1 or id_status = 2 or id_status = 8) " +
            "and cast(horario_inicial as date) != current_date " +
            "order by horario_inicial", nativeQuery = true)
    List<M_Reserva> findAllFuture();

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where id = :id", nativeQuery = true)
    M_Reserva findElementById(@Param("id")Long id);

    @Query(value = "select * " +
            "from tcc.reserva " +
            "where id_status = 1 or id_status = 2",nativeQuery = true)
    List<M_Reserva> findAllInWaitOrCourse();
}
