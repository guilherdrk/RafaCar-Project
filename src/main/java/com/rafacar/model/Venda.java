package com.rafacar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    /** Quantidade de diárias alugadas */
    @NotNull
    @Min(1)
    private Integer quantidade;

    private LocalDateTime dataVenda;
    
    @PrePersist
    public void prePersist(){
        if (dataVenda == null) dataVenda = LocalDateTime.now();
    }
}