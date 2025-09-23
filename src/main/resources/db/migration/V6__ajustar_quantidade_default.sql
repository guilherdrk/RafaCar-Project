-- Define valor padrão e garante que não haverá NULL
ALTER TABLE locacao
    ALTER COLUMN dias SET DEFAULT 1;

-- Atualiza registros antigos que eventualmente estejam com NULL
UPDATE locacao SET dias = 1 WHERE dias IS NULL;

-- Garante de novo a restrição NOT NULL
ALTER TABLE locacao
    ALTER COLUMN dias SET NOT NULL;
