package com.vitor.teste.service;

import com.vitor.teste.model.Funcionario;
import com.vitor.teste.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Inserir funcionários
    @Transactional
    public void inserirFuncionarios() {
        Funcionario[] funcionarios = {
                new Funcionario("Maria", LocalDate.parse("2000-10-18"), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.parse("1990-05-12"), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.parse("1961-05-04"), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.parse("1988-10-02"), new BigDecimal("1919.18"), "Diretor"),
                new Funcionario("Alice", LocalDate.parse("1995-01-05"), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heltor", LocalDate.parse("1999-11-19"), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.parse("1993-03-31"), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.parse("1994-07-08"), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.parse("2003-05-24"), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.parse("1996-09-02"), new BigDecimal("2799.93"), "Gerente")
        };

        funcionarioRepository.saveAll(Arrays.asList(funcionarios)); // Salva todos os funcionários no banco de dados
    }

    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public void removerFuncionarioPorId(Long id) {
        funcionarioRepository.deleteById(id);
    }

    @Transactional
    public void aplicarAumentoDeSalario(BigDecimal percentualDeAumento) {
        BigDecimal fatorDeAumento = percentualDeAumento.divide(new BigDecimal("100")).add(BigDecimal.ONE);
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(fatorDeAumento);
            funcionario.setSalario(novoSalario);
            funcionarioRepository.save(funcionario);
        }
    }

    @Transactional
    public Map<String, List<Funcionario>> agruparFuncionariosPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        Map<String, List<Funcionario>> agrupadosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            agrupadosPorFuncao.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        }
        return agrupadosPorFuncao;
    }

    public List<Funcionario> buscarAniversariantesNosMeses() {
        List<Integer> meses = Arrays.asList(10, 12); // Outubro e Dezembro
        return funcionarioRepository.findByDataNascimentoMonthIn(meses);
    }


    public Optional<Map<String, Object>> buscarFuncionarioMaisVelho() {
        return listarTodosFuncionarios().stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .map(funcionario -> {
                    Map<String, Object> result = new HashMap<>();
                    Period period = Period.between(funcionario.getDataNascimento(), LocalDate.now());
                    result.put("nome", funcionario.getNome());
                    result.put("idade", period.getYears());
                    return result;
                });
    }

    public List<Funcionario> listarFuncionariosEmOrdemAlfabetica() {
        List<Funcionario> funcionarios = listarTodosFuncionarios();
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        return funcionarios;
    }

    public BigDecimal calcularTotalDeSalarios() {
        return listarTodosFuncionarios().stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<String> calcularQuantosSalariosMinimosCadaFuncionarioGanha() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");  // Valor fixo do salário mínimo
        List<String> relatorio = new ArrayList<>();
        for (Funcionario funcionario : listarTodosFuncionarios()) {
            BigDecimal quantosSalarios = funcionario.getSalario().divide(salarioMinimo, 1, BigDecimal.ROUND_HALF_UP);
            relatorio.add(funcionario.getNome() + " ganha " + quantosSalarios + " salários mínimos.");
        }
        return relatorio;
    }

}
