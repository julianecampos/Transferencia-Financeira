package com.transferencias.transferencias_api.controller;

import com.transferencias.transferencias_api.model.Agendamento;
import com.transferencias.transferencias_api.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Agendamento agendamento) {
        try {
            return ResponseEntity.ok(service.agendar(agendamento));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Agendamento> listar() {
        return service.listarTodos();
    }
}