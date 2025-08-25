package com.rafacar.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import lombok.Data;
import java.math.BigDecimal; import java.time.LocalDate;
@Entity @Data public class Despesa {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String descricao;
 @NotNull @DecimalMin(value="0.0", inclusive=false) private BigDecimal valor;
 @NotNull private LocalDate data;
 @ManyToOne(optional=true) private Veiculo veiculo;
}