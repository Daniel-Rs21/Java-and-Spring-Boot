ALTER Table medicos ADD activo BOOLEAN NOT NULL;
UPDATE medicos SET activo = true;