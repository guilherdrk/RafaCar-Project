package com.rafacar.dto;

import com.rafacar.model.Venda;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendaDTO {
    private Long id;
    private String veiculo;
    private String imagemUrl;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal custoUnitario;
    private BigDecimal outros;
    private BigDecimal total;
    private BigDecimal lucro;
    private double margem;

    public VendaDTO(Venda venda){
        this.id = venda.getId();
        this.veiculo = venda.getVeiculo().getNome();
        this.imagemUrl = venda.getVeiculo().getImageUrl();
        this.quantidade = venda.getQuantidade();
        this.precoUnitario = venda.getVeiculo().getPreco();
        this.custoUnitario = venda.getVeiculo().getCusto();
        this.outros = venda.getOutros() != null ? venda.getOutros() : BigDecimal.ZERO;


        // Total = (preço x quantidade) + outros
        this.total = precoUnitario.multiply(BigDecimal.valueOf(quantidade)).add(outros);

        // Custo total
        BigDecimal custoTotal = custoUnitario.multiply(BigDecimal.valueOf(quantidade));

        // Lucro = total - custoTotal
        this.lucro = total.subtract(custoTotal);

        //Margem (%)
        this.margem = total.compareTo(BigDecimal.ZERO) > 0 ? (lucro.doubleValue() / total.doubleValue()) * 100 : 0.0;
    }
}
