package com.rafacar.dto;

import com.rafacar.model.Locacao;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class LocacaoDTO {
    private Long id;
    private String veiculo;
    private String imagemUrl;
    private int dias;

    private BigDecimal precoPorDia;
    private BigDecimal precoPorDiaCustomizado;
    private BigDecimal precoUnitario; // preco efetivo por dia

    private BigDecimal total;
    private BigDecimal custoTotal;
    private BigDecimal lucro;
    private Double margem;
    private LocalDateTime dataVenda;

    public LocacaoDTO(Locacao l) {
        this.id = l.getId();
        if (l.getVeiculo() != null) {
            this.veiculo = l.getVeiculo().getNome();
            this.imagemUrl = l.getVeiculo().getImagemUrl();
        }
        this.dias = l.getDias() == null ? 1 : l.getDias();

        this.precoPorDia = l.getPrecoPorDia() == null ? BigDecimal.ZERO : l.getPrecoPorDia();
        this.precoPorDiaCustomizado = l.getPrecoPorDiaCustomizado();

        this.precoUnitario = (this.precoPorDiaCustomizado != null) ? this.precoPorDiaCustomizado : this.precoPorDia;

        this.total = this.precoUnitario.multiply(BigDecimal.valueOf(this.dias)).setScale(2, RoundingMode.HALF_UP);

        this.custoTotal = l.getCustoTotal() == null ? BigDecimal.ZERO : l.getCustoTotal().setScale(2, RoundingMode.HALF_UP);
        this.lucro = l.getLucro() == null ? BigDecimal.ZERO : l.getLucro().setScale(2, RoundingMode.HALF_UP);

        this.margem = this.total.signum() > 0 ? (this.lucro.doubleValue() / this.total.doubleValue()) * 100.0 : 0.0;

        this.dataVenda = l.getDataVenda();
    }
}
