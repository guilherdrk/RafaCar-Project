package com.rafacar.controller;

import com.rafacar.dto.LocacaoDTO;
import com.rafacar.model.Locacao;
import com.rafacar.service.VeiculoService;
import com.rafacar.service.LocacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/locacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LocacaoController {
    private final LocacaoService vendaService;
    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Locacao> criar(@Valid @RequestBody Locacao venda) {
        // Garante que o veículo existe e carrega o objeto completo
        if (venda.getVeiculo() != null && venda.getVeiculo().getId() != null) {
            var veiculo = veiculoService.obter(venda.getVeiculo().getId());
            venda.setVeiculo(veiculo);
        } else {
            // se não veio veiculo ou id, reprovamos a requisição
            return ResponseEntity.badRequest().build();
        }

        // Se o usuário não enviou precoPorDiaCustomizado, use o preço padrão do veículo
        if (venda.getPrecoPorDiaCustomizado() == null) {
            venda.setPrecoPorDiaCustomizado(venda.getVeiculo().getPreco());
        }

        // Opcional: defina precoPorDia (campo da entidade) como o valor efetivo por dia
        venda.setPrecoPorDia(venda.getPrecoPorDiaCustomizado());

        Locacao salvo = vendaService.criar(venda);
        return ResponseEntity.created(URI.create("/locacoes/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<LocacaoDTO> listar() {
        return vendaService.listar()
                .stream()
                    .map(LocacaoDTO::new)
                        .toList();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        vendaService.deletarVenda(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/resumo-mensal")
    public List<java.util.Map<String,Object>> resumoMensal() {
        // Retorna lista: [{mes:1, ano:2025, lucro: 123.45}, ...]
        var locacoes = vendaService.listar();
        java.util.Map<String, java.math.BigDecimal> mapa = new java.util.HashMap<>();
        for (var v : locacoes) {
            var dto = new com.rafacar.dto.LocacaoDTO(v);
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