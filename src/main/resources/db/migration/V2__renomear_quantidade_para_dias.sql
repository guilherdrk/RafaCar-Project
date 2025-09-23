ALTER TABLE locacao ADD COLUMN IF NOT EXISTS dias INTEGER;

DO $$
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='locacao' AND column_name='quantidade'
  ) THEN
    UPDATE locacao SET dias = quantidade WHERE quantidade IS NOT NULL AND (dias IS NULL OR dias = 0);
  END IF;
END$$;

ALTER TABLE locacao ALTER COLUMN dias SET DEFAULT 1;
UPDATE locacao SET dias = 1 WHERE dias IS NULL;

DO $$
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='locacao' AND column_name='quantidade'
  ) THEN
    ALTER TABLE locacao DROP COLUMN quantidade;
  END IF;
END$$;
