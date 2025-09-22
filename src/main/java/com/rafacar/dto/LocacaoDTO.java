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
    private BigDecimal precoUnitario;
    private BigDecimal custoUnitario;
    private BigDecimal total;   // receita da venda (preço x dias)
    private BigDecimal totalCustomizado;
    private BigDecimal lucro;   // total - custoTotal
    private double margem;      // %
    private int dias;
    private BigDecimal precoPorDia;
    private BigDecimal precoPorDiaCustomizado;



    public LocacaoDTO(Locacao venda) {
        this.id = venda.getId();
        this.veiculo = venda.getVeiculo() != null ? venda.getVeiculo().getNome() : null;
        this.imagemUrl = venda.getVeiculo() != null ? venda.getVeiculo().getImagemUrl() : null;
        this.quantidade = venda.getQuantidade() != null ? venda.getQuantidade() : 0;
        this.precoUnitario = venda.getVeiculo() != null && venda.getVeiculo().getPreco() != null
                ? venda.getVeiculo().getPreco()
                : BigDecimal.ZERO;
        this.custoUnitario = venda.getVeiculo() != null && venda.getVeiculo().getCusto() != null
                ? venda.getVeiculo().getCusto()
                : BigDecimal.ZERO;

        // dias
        this.dias = venda.getDias();

        // Total = precoUnitario * quantidade * dias
        BigDecimal q = BigDecimal.valueOf(Math.max(this.quantidade, 0));
        BigDecimal d = BigDecimal.valueOf(Math.max(this.dias, 0));
        this.total = precoUnitario.multiply(q).multiply(d);

        // Total customizado: se precoPorDiaCustomizado estiver presente, use ele * quantidade * dias.
        BigDecimal precoCustomPorDia = venda.getPrecoPorDiaCustomizado() != null
                ? venda.getPrecoPorDiaCustomizado()
                : BigDecimal.ZERO;
        this.totalCustomizado = precoCustomPorDia.multiply(q).multiply(d);

        // Custo total = custoUnitario * quantidade * dias
        BigDecimal custoTotal = custoUnitario.multiply(q).multiply(d);

        // Lucro = total - custoTotal
        this.lucro = total.subtract(custoTotal);

        // Margem (%)
        this.margem = total.signum() > 0 ? (lucro.doubleValue() / total.doubleValue()) * 100.0 : 0.0;

        // Preço por dia (para toda a locação): total / dias (evita dividir por zero)
        if (this.dias > 0) {
            // preço por dia total (para todos os veículos juntos)
            this.precoPorDia = this.total.divide(d, 2, RoundingMode.HALF_UP);
            // preço por dia customizado (para todos os veículos juntos)
            this.precoPorDiaCustomizado = this.totalCustomizado.divide(d, 2, RoundingMode.HALF_UP);
        } else {
            this.precoPorDia = BigDecimal.ZERO;
            this.precoPorDiaCustomizado = BigDecimal.ZERO;
        }
    }
}