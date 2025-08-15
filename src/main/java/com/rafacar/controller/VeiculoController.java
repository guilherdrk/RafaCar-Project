package com.rafacar.controller;

import com.rafacar.model.Veiculo;
import com.rafacar.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VeiculoController {
    private final VeiculoService service;

    @PostMapping
    public ResponseEntity<Veiculo> criar(@Valid @RequestBody Veiculo v) {
        Veiculo salvo = service.criar(v);
        return ResponseEntity.created(URI.create("/veiculos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Veiculo> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Veiculo obter(@PathVariable Long id) { return service.obter(id); }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo v) {
        return service.atualizar(id, v);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}