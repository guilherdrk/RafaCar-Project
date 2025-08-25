package com.rafacar.repository;
import com.rafacar.model.Despesa; import org.springframework.data.jpa.repository.JpaRepository;
public interface DespesaRepository extends JpaRepository<Despesa, Long> {}