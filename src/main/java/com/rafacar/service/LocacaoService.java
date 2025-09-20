package com.rafacar.service;


import com.rafacar.model.Locacao;
import com.rafacar.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    public Double calcularValorTotal(Locacao locacao){
        if(locacao.getValorTotal() != null){
            return locacao.getValorTotal(); // usa o valor definido manualmente
        }

        if(locacao.getQuantidadeDias() != null && locacao.getValorDiaria() != null){
            int dias = locacao.getQuantidadeDias();

            if (dias == 2){
                return 550.0;
            } else {
                return locacao.getValorDiaria() * dias;
            }
        }

        return 0.0;
    }

}
