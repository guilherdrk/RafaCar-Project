package com.rafacar.controller;

import com.rafacar.dto.LocacaoDTO;
import com.rafacar.model.Locacao;
import com.rafacar.service.VeiculoService;
import com.rafacar.service.LocacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/locacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LocacaoController {

    // renomeie se preferir para vendaService; aqui usei locacaoService para clareza
    private final LocacaoService locacaoService;
    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<LocacaoDTO> criar(@Valid @RequestBody Locacao venda) {
        // garante que o veículo existe (se passado)
        if (venda.getVeiculo() != null && venda.getVeiculo().getId() != null) {
            veiculoService.obter(venda.getVeiculo().getId());
        }
        Locacao salvo = locacaoService.criar(venda);
        LocacaoDTO dto = new LocacaoDTO(salvo);
        return ResponseEntity.created(URI.create("/locacoes/" + salvo.getId())).body(dto);
    }

    @GetMapping
    public List<LocacaoDTO> listar() {
        return locacaoService.listar().stream()
                .map(LocacaoDTO::new)
                .collect(Collectors.toList()); // compatível com Java 8+
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocacaoDTO> obter(@PathVariable Long id) {
        Locacao l = locacaoService.obter(id);
        return ResponseEntity.ok(new LocacaoDTO(l));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocacaoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Locacao venda) {
        Locacao atualizado = locacaoService.atualizar(id, venda);
        return ResponseEntity.ok(new LocacaoDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        locacaoService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumo-mensal")
    public List<Map<String, Object>> resumoMensal() {
        List<Locacao> locacoes = locacaoService.listar();
        Map<YearMonth, BigDecimal> mapa = new HashMap<>();

        for (Locacao v : locacoes) {
            LocacaoDTO dto = new LocacaoDTO(v);
            BigDecimal lucro = dto.getLucro() == null ? BigDecimal.ZERO : dto.getLucro();
            LocalDateTime dt = v.getDataVenda() == null ? LocalDateTime.now() : v.getDataVenda();
            YearMonth ym = YearMonth.from(dt);
            mapa.put(ym, mapa.getOrDefault(ym, BigDecimal.ZERO).add(lucro));
        }

        // transformar em lista de maps e ordenar por ano/mes desc
        List<Map<String, Object>> out = mapa.entrySet().stream()
                .map(e -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("ano", e.getKey().getYear());
                    m.put("mes", e.getKey().getMonthValue());
                    m.put("lucro", e.getValue());
                    return m;
                })
                .sorted(Comparator
                        .comparing((Map<String, Object> m) -> (Integer) m.get("ano")).reversed()
                        .thenComparing(m -> (Integer) m.get("mes"), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return out;
    }
}
