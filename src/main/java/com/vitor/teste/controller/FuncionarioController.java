package com.vitor.teste.controller;

import com.vitor.teste.model.Funcionario;
import com.vitor.teste.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserirFuncionarios() {
        funcionarioService.inserirFuncionarios();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFuncionarioPorId(@PathVariable Long id) {
        funcionarioService.removerFuncionarioPorId(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/aumentar-salarios")
    public ResponseEntity<Void> aplicarAumentoDeSalario(@RequestParam BigDecimal percentual) {
        funcionarioService.aplicarAumentoDeSalario(percentual);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agrupar-por-funcao")
    public ResponseEntity<Map<String, List<Funcionario>>> agruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> agrupados = funcionarioService.agruparFuncionariosPorFuncao();
        return ResponseEntity.ok(agrupados);
    }

    @GetMapping("/aniversariantes")
    public ResponseEntity<List<Funcionario>> buscarAniversariantesNosMeses(@RequestParam List<Integer> meses) {
        List<Funcionario> aniversariantes = funcionarioService.buscarAniversariantesNosMeses(meses);
        return ResponseEntity.ok(aniversariantes);
    }

    @GetMapping("/mais-velho")
    public ResponseEntity<Funcionario> buscarFuncionarioMaisVelho() {
        Optional<Funcionario> maisVelho = funcionarioService.buscarFuncionarioMaisVelho();
        return maisVelho.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ordem-alfabetica")
    public ResponseEntity<List<Funcionario>> listarFuncionariosEmOrdemAlfabetica() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionariosEmOrdemAlfabetica();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/total-salarios")
    public ResponseEntity<String> calcularTotalDeSalarios() {
        BigDecimal total = funcionarioService.calcularTotalDeSalarios();
        return ResponseEntity.ok("Total dos salários dos funcionários: R$ " + total);
    }

    @GetMapping("/salarios-minimos")
    public ResponseEntity<List<String>> calcularQuantosSalariosMinimosCadaFuncionarioGanha(@RequestParam BigDecimal salarioMinimo) {
        List<String> relatorio = funcionarioService.calcularQuantosSalariosMinimosCadaFuncionarioGanha(salarioMinimo);
        return ResponseEntity.ok(relatorio);
    }
}
