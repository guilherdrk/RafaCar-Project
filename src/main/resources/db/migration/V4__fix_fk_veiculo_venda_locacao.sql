-- V4__fix_fk_veiculo_venda_locacao.sql
-- Remove quaisquer FKs antigas que referenciem veiculo nas tabelas venda e locacao,
-- e recria FKs com ON DELETE CASCADE de forma segura.

-- Remove FKs em "venda" que referenciem "veiculo"
DO $$
DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT conname
    FROM pg_constraint
    WHERE contype = 'f'
      AND conrelid = 'venda'::regclass
      AND confrelid = 'veiculo'::regclass
  LOOP
    EXECUTE format('ALTER TABLE venda DROP CONSTRAINT %I', r.conname);
  END LOOP;
END
$$;

-- Remove FKs em "locacao" que referenciem "veiculo"
DO $$
DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT conname
    FROM pg_constraint
    WHERE contype = 'f'
      AND conrelid = 'locacao'::regclass
      AND confrelid = 'veiculo'::regclass
  LOOP
    EXECUTE format('ALTER TABLE locacao DROP CONSTRAINT %I', r.conname);
  END LOOP;
END
$$;

-- Cria as FKs com nomes definidos (será seguro porque já removemos qualquer FK pré-existente)
ALTER TABLE venda
  ADD CONSTRAINT fk_venda_veiculo
  FOREIGN KEY (veiculo_id)
  REFERENCES veiculo (id)
  ON DELETE CASCADE;

ALTER TABLE locacao
  ADD CONSTRAINT fk_locacao_veiculo
  FOREIGN KEY (veiculo_id)
  REFERENCES veiculo (id)
  ON DELETE CASCADE;
