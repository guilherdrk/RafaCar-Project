ALTER TABLE locacao DROP CONSTRAINT IF EXISTS fkiis4fkjndd3sqr42vashfew9l;

ALTER TABLE locacao
  ADD CONSTRAINT fk_locacao_veiculo
  FOREIGN KEY (veiculo_id)
  REFERENCES veiculo (id)
  ON DELETE CASCADE;
