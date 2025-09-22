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

    @ManyToOne(optional = false)
    private Veiculo veiculo;

    /** Quantidade de diárias alugadas */
    @NotNull
    @Min(1)
    private Integer quantidade;

    private LocalDateTime dataVenda;

    private int dias;
    private BigDecimal precoPorDia;
    private BigDecimal precoPorDiaCustomizado;

    @PrePersist
    public void prePersist(){
        if (dataVenda == null) dataVenda = LocalDateTime.now();

        if (precoPorDiaCustomizado == null && veiculo != null && veiculo.getPreco() != null) {
            precoPorDiaCustomizado = veiculo.getPreco();
        }
        if (precoPorDia == null) {
            precoPorDia = precoPorDiaCustomizado != null ? precoPorDiaCustomizado
                    : (veiculo != null ? veiculo.getPreco() : BigDecimal.ZERO);
        }
    }

}