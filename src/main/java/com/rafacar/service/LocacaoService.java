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
    public Locacao obter(Long id) { return repo.findById(id).orElseThrow(); }
    public Locacao atualizar(Long id, Locacao novo) {
        Locacao atual = obter(id);
        // atualizar campos permitidos
        atual.setVeiculo(novo.getVeiculo());
        atual.setDias(novo.getDias());
        atual.setPrecoPorDia(novo.getPrecoPorDia());
        atual.setPrecoPorDiaCustomizado(novo.getPrecoPorDiaCustomizado());
        atual.setDataVenda(novo.getDataVenda());
        return repo.save(atual);
    }
    public void remover(Long id) { repo.deleteById(id); }

    // compatibilidade
    public void deletarVenda(Long id){ remover(id); }
}
