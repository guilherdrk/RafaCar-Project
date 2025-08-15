package com.rafacar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    /** Preço da diária (R$) */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal preco;

    /** Custo por diária (R$) — manutenção, depreciação etc. */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal custo;

    /** URL da imagem do veículo (opcional) */
    private String imagemUrl;
}