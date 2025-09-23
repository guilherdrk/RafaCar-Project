ALTER TABLE venda DROP CONSTRAINT IF EXISTS fkiis4fkjndd3sqr42vashfew9l;

ALTER TABLE venda
  ADD CONSTRAINT fk_venda_veiculo
  FOREIGN KEY (veiculo_id)
  REFERENCES veiculo (id)
  ON DELETE CASCADE;

