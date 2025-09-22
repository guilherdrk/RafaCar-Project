package com.rafacar.controller;

import com.rafacar.dto.LocacaoDTO;
import com.rafacar.service.LocacaoService;
import com.rafacar.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/financeiro")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FinanceiroController {
  private final LocacaoService vendaService;
  private final DespesaService despesaService;

  @GetMapping("/resumo-mensal")
  public List<Map<String, Object>> resumoMensal() {
    Map<String, BigDecimal> rec = new HashMap<>();
    Map<String, BigDecimal> des = new HashMap<>();

    // Agrupa receitas (lucro das vendas) por ano-mês
    for (var v : vendaService.listar()) {
      var dto = new LocacaoDTO(v);
      LocalDateTime dt = v.getDataVenda() != null ? v.getDataVenda() : LocalDateTime.now();
      String key = dt.getYear() + "-" + dt.getMonthValue();
      rec.put(key, rec.getOrDefault(key, BigDecimal.ZERO).add(dto.getLucro()));
    }

    // Agrupa despesas por ano-mês
    for (var d : despesaService.listar()) {
      LocalDate dt = d.getData() != null ? d.getData() : LocalDate.now();
      String key = dt.getYear() + "-" + dt.getMonthValue();
      des.put(key, des.getOrDefault(key, BigDecimal.ZERO).add(d.getValor()));
    }

    // Junta chaves e monta saída
    Set<String> keys = new HashSet<>();
    keys.addAll(rec.keySet());
    keys.addAll(des.keySet());

    List<Map<String, Object>> out = new ArrayList<>();
    for (var k : keys) {
      String[] p = k.split("-");
      int ano = Integer.parseInt(p[0]);
      int mes = Integer.parseInt(p[1]);
      BigDecimal r = rec.getOrDefault(k, BigDecimal.ZERO);
      BigDecimal d = des.getOrDefault(k, BigDecimal.ZERO);
      BigDecimal l = r.subtract(d);

      Map<String, Object> m = new HashMap<>();
      m.put("ano", ano);
      m.put("mes", mes);
      m.put("receita", r);
      m.put("despesas", d);
      m.put("lucro", l);
      out.add(m);
    }

    // ordenar por ano/mes desc
    out.sort((a, b) -> {
      int ay = ((Integer) b.get("ano")).compareTo((Integer) a.get("ano"));
      if (ay != 0) return ay;
      return ((Integer) b.get("mes")).compareTo((Integer) a.get("mes"));
    });
    return out;
  }
}
