package com.rafacar.controller;
import com.rafacar.model.Despesa; import com.rafacar.service.DespesaService;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.net.URI; import java.util.*; import java.time.LocalDate;
@RestController @RequestMapping("/despesas") @CrossOrigin(origins="*") @RequiredArgsConstructor
public class DespesaController {
    private final DespesaService service;
    @PostMapping public ResponseEntity<Despesa> criar(@Valid @RequestBody Despesa d){
        if(d.getData()==null) d.setData(LocalDate.now());
        var salvo = service.criar(d);
        return ResponseEntity.created(URI.create("/despesas/"+salvo.getId())).body(salvo);
    }
    @GetMapping public java.util.List<Despesa> listar(){ return service.listar(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> remover(@PathVariable Long id){
        service.remover(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/resumo-mensal") public List<java.util.Map<String,Object>> resumoMensal(){
        java.util.Map<String, java.math.BigDecimal> mapa = new java.util.HashMap<>();
        for (var d : service.listar()){
            var dt = d.getData()!=null? d.getData() : LocalDate.now();
            String key = dt.getYear()+"-"+dt.getMonthValue();
            var acum = mapa.getOrDefault(key, java.math.BigDecimal.ZERO);
            acum = acum.add(d.getValor()); mapa.put(key, acum);
        }
        java.util.List<java.util.Map<String,Object>> out = new java.util.ArrayList<>();
        for (var e : mapa.entrySet()){
            String[] p = e.getKey().split("-"); int ano=Integer.parseInt(p[0]); int mes=Integer.parseInt(p[1]);
            var m = new java.util.HashMap<String,Object>(); m.put("ano",ano); m.put("mes",mes); m.put("valor", e.getValue());
            out.add(m);
        }
        out.sort((a,b)->{ int ay=((Integer)b.get("ano")).compareTo((Integer)a.get("ano")); if(ay!=0) return ay;
            return ((Integer)b.get("mes")).compareTo((Integer)a.get("mes")); });
        return out;
    }
}