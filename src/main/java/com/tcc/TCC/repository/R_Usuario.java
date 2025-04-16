package com.tcc.TCC.repository;

import com.tcc.TCC.model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuario,Long> {
    @Query(value = "select * from tcc.usuario " +
            "where matricula = :matricula and " +
            "senha = :senha", nativeQuery = true)
    M_Usuario getusuarioByLoginSenha(@Param("matricula") String matricula,
                                              @Param("senha") String senha);
}
