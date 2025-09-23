package com.rafacar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    // número de diárias
    @NotNull
    @Min(1)
    private Integer dias;

    // preço padrão por dia (vindo do Veículo quando criado se não informado)
    @Column(precision = 12, scale = 2)
    private BigDecimal precoPorDia;

    // preço customizado por dia (quando houver devolução antecipada ou desconto)
    @Column(precision = 12, scale = 2)
    private BigDecimal precoPorDiaCustomizado;

    private LocalDateTime dataVenda;

    @PrePersist
    public void prePersist(){
        if (dataVenda == null) dataVenda = LocalDateTime.now();

        if (veiculo != null) {
            if (precoPorDia == null && veiculo.getPreco() != null) {
                precoPorDia = veiculo.getPreco();
            }
        }

        if (precoPorDia == null) precoPorDia = BigDecimal.ZERO;
        if (dias == null) dias = 1;
    }

    /**
     * Retorna o preço por dia efetivamente aplicado (customizado quando presente).
     */
    @Transient
    public BigDecimal getPrecoPorDiaEfetivo(){
        return precoPorDiaCustomizado != null ? precoPorDiaCustomizado : precoPorDia;
    }

    /**
     * Total da locação: precoPorDiaEfetivo * dias
     */
    @Transient
    public BigDecimal getTotal(){
        BigDecimal d = BigDecimal.valueOf(Objects.requireNonNullElse(dias, 1));
        return getPrecoPorDiaEfetivo().multiply(d);
    }

    /**
     * Cálculo do custo (p.ex. custo do veículo por dia * dias) - assume veiculo.getCusto() is custo por dia
     */
    @Transient
    public BigDecimal getCustoTotal(){
        BigDecimal custoDia = (veiculo != null && veiculo.getCusto() != null) ? veiculo.getCusto() : BigDecimal.ZERO;
        BigDecimal d = BigDecimal.valueOf(Objects.requireNonNullElse(dias, 1));
        return custoDia.multiply(d);
    }

    /**
     * Cálculo do lucro: total - custoTotal
     */
    @Transient
    public BigDecimal getLucro(){
        return getTotal().subtract(getCustoTotal());
    }

    /**
     * Margem percentual (em %). Retorna 0 quando total = 0.
     */
    @Transient
    public Double getMargem(){
        BigDecimal total = getTotal();
        if (total.signum() == 0) return 0.0;
        return getLucro().doubleValue() / total.doubleValue() * 100.0;
    }
}
