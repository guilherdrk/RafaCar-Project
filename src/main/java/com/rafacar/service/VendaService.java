package com.rafacar.service;

import com.rafacar.model.Venda;
import com.rafacar.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository repo;

    public Venda criar(Venda v) { return repo.save(v); }
    public List<Venda> listar() { return repo.findAll(); }
}