package com.rafacar.service;

import com.rafacar.model.Locacao;
import com.rafacar.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository repo;

    public Locacao criar(Locacao v) { return repo.save(v); }
    public List<Locacao> listar() { return repo.findAll(); }

    public void deletarVenda(Long id) {
        repo.deleteById(id);
    }
}
