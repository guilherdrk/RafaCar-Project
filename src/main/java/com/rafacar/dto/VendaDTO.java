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
    private BigDecimal total;   // receita da venda (preço x dias)
    private BigDecimal lucro;   // total - custoTotal
    private double margem;      // %

    public VendaDTO(Venda venda){
        this.id = venda.getId();
        this.veiculo = venda.getVeiculo().getNome();
        this.imagemUrl = venda.getVeiculo().getImagemUrl();
        this.quantidade = venda.getQuantidade();
        this.precoUnitario = venda.getVeiculo().getPreco();
        this.custoUnitario = venda.getVeiculo().getCusto();

        // Total = preço x quantidade (diárias)
        this.total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

        // Custo total = custo x quantidade
        BigDecimal custoTotal = custoUnitario.multiply(BigDecimal.valueOf(quantidade));

        // Lucro = total - custoTotal
        this.lucro = total.subtract(custoTotal);

        // Margem (%)
        this.margem = total.signum() > 0 ? (lucro.doubleValue() / total.doubleValue()) * 100.0 : 0.0;
    }
}