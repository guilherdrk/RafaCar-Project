package com.rafacar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Veiculo veiculo;

    @NotNull
    @Min(1)
    private Integer quantidade;

    // Custos adicionais dessa venda (ex: documentação, frete, taxas variadas)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal outros;

    private LocalDateTime dataVenda;
}
