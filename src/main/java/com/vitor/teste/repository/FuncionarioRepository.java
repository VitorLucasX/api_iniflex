package com.vitor.teste.repository;

import com.vitor.teste.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f WHERE FUNCTION('MONTH', f.dataNascimento) IN :meses")
    List<Funcionario> findByDataNascimentoMonthIn(@Param("meses") List<Integer> meses);

    @Modifying
    @Query("UPDATE Funcionario f SET f.salario = f.salario * :percentual")
    int updateSalarios(BigDecimal percentual);
}
