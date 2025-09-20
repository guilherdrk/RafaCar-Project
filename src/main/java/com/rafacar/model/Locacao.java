package com.rafacar.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer quantidadeDias;

    private Double valorDiaria;

    //Valor total customizado
    private Double valorTotal;

    public Locacao(){}

    public Locacao(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, Integer quantidadeDias, Double valorDiaria, Double valorTotal) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.quantidadeDias = quantidadeDias;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
