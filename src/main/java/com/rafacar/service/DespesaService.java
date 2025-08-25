package com.rafacar.service;
import com.rafacar.model.Despesa; import com.rafacar.repository.DespesaRepository;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Service; import java.util.List;
@Service @RequiredArgsConstructor public class DespesaService {
 private final DespesaRepository repo;
 public Despesa criar(Despesa d){ return repo.save(d); }
 public List<Despesa> listar(){ return repo.findAll(); }
 public void remover(Long id){ repo.deleteById(id); }
}