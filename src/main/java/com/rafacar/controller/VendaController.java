package com.rafacar.controller;

import com.rafacar.dto.VendaDTO;
import com.rafacar.model.Venda;
import com.rafacar.service.VeiculoService;
import com.rafacar.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;
    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda) {
        // Garante que o veículo existe
        if (venda.getVeiculo() != null && venda.getVeiculo().getId() != null) {
            veiculoService.obter(venda.getVeiculo().getId());
        }
        Venda salvo = vendaService.criar(venda);
        return ResponseEntity.created(URI.create("/vendas/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<VendaDTO> listar() {
        return vendaService.listar().stream().map(VendaDTO::new).toList();
    }
}
