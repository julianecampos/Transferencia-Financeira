package com.transferencias.transferencias_api.service;

import com.transferencias.transferencias_api.model.Agendamento;
import com.transferencias.transferencias_api.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    public Agendamento agendar(Agendamento agendamento) {
        BigDecimal taxa = calcularTaxa(agendamento.getDataTransferencia(), agendamento.getValor());
        agendamento.setTaxa(taxa);
        agendamento.setDataAgendamento(LocalDate.now());
        return repository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    private BigDecimal calcularTaxa(LocalDate dataTransferencia, BigDecimal valor) {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), dataTransferencia);

        if (dias == 0)
            return valor.multiply(new BigDecimal("0.025")).add(new BigDecimal("3.00"));
        else if (dias >= 1 && dias <= 10)
            return new BigDecimal("12.00");
        else if (dias >= 11 && dias <= 20)
            return valor.multiply(new BigDecimal("0.082"));
        else if (dias >= 21 && dias <= 30)
            return valor.multiply(new BigDecimal("0.069"));
        else if (dias >= 31 && dias <= 40)
            return valor.multiply(new BigDecimal("0.047"));
        else if (dias >= 41 && dias <= 50)
            return valor.multiply(new BigDecimal("0.017"));
        else
            throw new RuntimeException("Nenhuma taxa aplicável para essa data de transferência.");
    }
}