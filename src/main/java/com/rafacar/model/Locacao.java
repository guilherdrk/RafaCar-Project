package com.rafacar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @NotNull
    @Min(1)
    private Integer quantidade;

    @NotNull
    @Min(1)
    private Integer dias;

    // preço por dia efetivo usado para cálculo (pode ser default do veículo ou customizado)
    private BigDecimal precoPorDia;

    // valor customizado que o usuário pode informar (opcional)
    private BigDecimal precoPorDiaCustomizado;

    // custo unitário (padrão pego do veículo em prePersist quando null)
    private BigDecimal custoUnitario;

    private LocalDateTime dataVenda;

    @PrePersist
    public void prePersist(){
        if (dataVenda == null) dataVenda = LocalDateTime.now();

        if (veiculo != null) {
            if (custoUnitario == null) custoUnitario = veiculo.getCusto();
            if (precoPorDiaCustomizado == null && veiculo.getPreco() != null) {
                precoPorDiaCustomizado = veiculo.getPreco();
            }
            if (precoPorDia == null) {
                precoPorDia = precoPorDiaCustomizado != null ? precoPorDiaCustomizado
                        : (veiculo.getPreco() != null ? veiculo.getPreco() : BigDecimal.ZERO);
            }
        } else {
            if (precoPorDia == null) precoPorDia = precoPorDiaCustomizado == null ? BigDecimal.ZERO : precoPorDiaCustomizado;
            if (custoUnitario == null) custoUnitario = BigDecimal.ZERO;
        }
    }
}