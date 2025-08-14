package com.rafacar.service;

import com.rafacar.model.Veiculo;
import com.rafacar.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository repo;

    public Veiculo criar(Veiculo v) {
        return repo.save(v);
    }

    public List<Veiculo> listar() {
        return repo.findAll();
    }

    public Veiculo obter(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public Veiculo atualizar(Long id, Veiculo v) {
        Veiculo atual = obter(id);
        atual.setNome(v.getNome());
        atual.setPreco(v.getPreco());
        atual.setCusto(v.getCusto());
        atual.setImagemUrl(v.getImagemUrl());
        return repo.save(atual);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
}
