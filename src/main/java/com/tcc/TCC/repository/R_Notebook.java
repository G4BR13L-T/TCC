package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Notebook;
import com.tcc.TCC.model.M_Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface R_Notebook extends JpaRepository<M_Notebook,Long> {
    @Query(value = "select * " +
            "from tcc.notebook " +
            "where id = :id", nativeQuery = true)
    M_Notebook getNoteById(@Param("id")Long id);

    @Query(value = "with notes_reservados as( " +
            "select distinct " +
            "nr.id_notebook " +
            "from tcc.notreserve nr " +
            "inner join tcc.reserva r " +
            "on r.id = nr.id_reserva " +
            "where :date between r.horario_inicial and r.horario_final " +
            "and r.id_status != 2 and r.id_status != 1 " +
            ") " +
            "select distinct n.* " +
            "from tcc.notebook n " +
            "left outer join notes_reservados nr " +
            "on n.id = nr.id_notebook " +
            "where nr.id_notebook is null and ativo " +
            "order by numero", nativeQuery = true)
    List<M_Notebook> findAllFreeInSpecificDate(@Param("date") LocalDateTime date);

    @Query(value = "with notes_reservados as ( " +
            "select distinct " +
            "nr.id_notebook " +
            "from tcc.notreserve nr " +
            "inner join tcc.reserva r " +
            "on r.id = nr.id_reserva " +
            "where r.horario_inicial < :end " +
            "and r.horario_final > :start " +
            "and (r.id_status = 2 or r.id_status = 1) " +
            ") " +
            "select distinct n.* " +
            "from tcc.notebook n " +
            "left outer join notes_reservados nr " +
            "on n.id = nr.id_notebook " +
            "where nr.id_notebook is null and ativo " +
            "order by numero;", nativeQuery = true)
    List<M_Notebook> findAllFreeInSpecificPeriod(@Param("start") LocalDateTime data_de_inicio,
                                                 @Param("end") LocalDateTime data_de_fim);

    @Query(value = "select * " +
            "from tcc.notebook " +
            "where ativo " +
            "order by numero", nativeQuery = true)
    List<M_Notebook> findAllActive();
}
