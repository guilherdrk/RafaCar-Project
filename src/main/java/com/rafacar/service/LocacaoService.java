package com.rafacar.service;

import com.rafacar.model.Locacao;
import com.rafacar.model.Veiculo;
import com.rafacar.repository.LocacaoRepository;
import com.rafacar.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository repo;
    private final VeiculoRepository veiculoRepo; // <-- injetar repo de Veiculo

    public Locacao criar(Locacao v) {
        // Se veio apenas veiculo.id, buscar o Veiculo completo
        if (v.getVeiculo() != null && v.getVeiculo().getId() != null) {
            Veiculo full = veiculoRepo.findById(v.getVeiculo().getId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + v.getVeiculo().getId()));
            v.setVeiculo(full);
        }

        // preencher precoPorDia caso ainda seja null (prePersist cobre, mas aqui garantimos)
        if (v.getPrecoPorDia() == null && v.getVeiculo() != null && v.getVeiculo().getPreco() != null) {
            v.setPrecoPorDia(v.getVeiculo().getPreco());
        }

        if (v.getDias() == null) v.setDias(1);

        return repo.save(v);
    }

    public List<Locacao> listar() { return repo.findAll(); }

    public Locacao obter(Long id) { return repo.findById(id).orElseThrow(); }

    public Locacao atualizar(Long id, Locacao novo) {
        Locacao atual = obter(id);

        if (novo.getVeiculo() != null && novo.getVeiculo().getId() != null) {
            Veiculo full = veiculoRepo.findById(novo.getVeiculo().getId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + novo.getVeiculo().getId()));
            atual.setVeiculo(full);
        }

        atual.setDias(novo.getDias());
        atual.setPrecoPorDia(novo.getPrecoPorDia());
        atual.setPrecoPorDiaCustomizado(novo.getPrecoPorDiaCustomizado());
        atual.setDataVenda(novo.getDataVenda());

        // se precoPorDia ficou null, preencher via veiculo
        if (atual.getPrecoPorDia() == null && atual.getVeiculo() != null && atual.getVeiculo().getPreco() != null) {
            atual.setPrecoPorDia(atual.getVeiculo().getPreco());
        }

        return repo.save(atual);
    }

    public void remover(Long id) { repo.deleteById(id); }

    // compatibilidade
    public void deletarVenda(Long id){ remover(id); }
}
