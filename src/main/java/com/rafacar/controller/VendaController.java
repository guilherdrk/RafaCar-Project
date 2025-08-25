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
@CrossOrigin(origins = "*")
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        vendaService.deletarVenda(id);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/resumo-mensal")
    public List<java.util.Map<String,Object>> resumoMensal() {
        // Retorna lista: [{mes:1, ano:2025, lucro: 123.45}, ...]
        var vendas = vendaService.listar();
        java.util.Map<String, java.math.BigDecimal> mapa = new java.util.HashMap<>();
        for (var v : vendas) {
            var dto = new com.rafacar.dto.VendaDTO(v);
            java.time.LocalDateTime dt = v.getDataVenda();
            if (dt == null) dt = java.time.LocalDateTime.now();
            String key = dt.getYear() + "-" + dt.getMonthValue();
            java.math.BigDecimal acum = mapa.getOrDefault(key, java.math.BigDecimal.ZERO);
            acum = acum.add(dto.getLucro());
            mapa.put(key, acum);
        }
        java.util.List<java.util.Map<String,Object>> out = new java.util.ArrayList<>();
        for (var e : mapa.entrySet()) {
            String[] parts = e.getKey().split("-");
            int ano = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);
            java.util.Map<String,Object> m = new java.util.HashMap<>();
            m.put("ano", ano);
            m.put("mes", mes);
            m.put("lucro", e.getValue());
            out.add(m);
        }
        // ordenar por ano/mes desc
        out.sort((a,b) -> {
            int ay = ((Integer)b.get("ano")).compareTo((Integer)a.get("ano"));
            if (ay!=0) return ay;
            return ((Integer)b.get("mes")).compareTo((Integer)a.get("mes"));
        });
        return out;
    }
    
}