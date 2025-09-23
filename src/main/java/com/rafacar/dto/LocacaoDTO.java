package com.rafacar.dto;

import com.rafacar.model.Locacao;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class LocacaoDTO {
    private Long id;
    private String veiculo;
    private String imagemUrl;
    private int quantidade;
    private BigDecimal precoUnitario; // preço usado por dia
    private BigDecimal custoUnitario;
    private int dias;
    private BigDecimal total; // total = precoUnitario * quantidade * dias
    private BigDecimal totalCustomizado; // usando precoPorDiaCustomizado
    private BigDecimal lucro;
    private double margem;
    private BigDecimal precoPorDia; // total / dias (para todos os veículos)
    private BigDecimal precoPorDiaCustomizado;
    private java.time.LocalDateTime dataVenda;

    public LocacaoDTO(Locacao l) {
        this.id = l.getId();
        if (l.getVeiculo() != null) {
            this.veiculo = l.getVeiculo().getNome();
            this.imagemUrl = l.getVeiculo().getImagemUrl();
        }
        this.quantidade = l.getQuantidade() == null ? 0 : l.getQuantidade();
        this.dias = l.getDias() == null ? 0 : l.getDias();

        this.precoUnitario = l.getPrecoPorDia() == null ? BigDecimal.ZERO : l.getPrecoPorDia();
        this.precoPorDiaCustomizado = l.getPrecoPorDiaCustomizado() == null ? BigDecimal.ZERO : l.getPrecoPorDiaCustomizado();
        this.custoUnitario = l.getCustoUnitario() == null ? BigDecimal.ZERO : l.getCustoUnitario();

        BigDecimal q = BigDecimal.valueOf(this.quantidade);
        BigDecimal d = BigDecimal.valueOf(Math.max(this.dias, 1)); // evitar divisão por zero

        // total com precoUnitario
        this.total = this.precoUnitario.multiply(q).multiply(BigDecimal.valueOf(this.dias));
        // total customizado
        this.totalCustomizado = this.precoPorDiaCustomizado.multiply(q).multiply(BigDecimal.valueOf(this.dias));

        // lucro = total - custoUnitario * quantidade * dias
        BigDecimal custoTotal = this.custoUnitario.multiply(q).multiply(BigDecimal.valueOf(this.dias));
        this.lucro = this.total.subtract(custoTotal);

        // margem em % (segurança: total != 0)
        if (this.total.signum() > 0) {
            this.margem = this.lucro.doubleValue() / this.total.doubleValue() * 100.0;
        } else {
            this.margem = 0.0;
        }

        // preço por dia (valor agregado para todos os itens) -> total/dias
        if (this.dias > 0) {
            this.precoPorDia = this.total.divide(d, 2, RoundingMode.HALF_UP);
            this.precoPorDiaCustomizado = this.totalCustomizado.divide(d, 2, RoundingMode.HALF_UP);
        } else {
            this.precoPorDia = BigDecimal.ZERO;
            this.precoPorDiaCustomizado = BigDecimal.ZERO;
        }

        this.dataVenda = l.getDataVenda();
    }
}