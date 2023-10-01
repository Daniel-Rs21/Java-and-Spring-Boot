ALTER Table pacientes ADD activo BOOLEAN NOT NULL;
UPDATE medicos SET activo = true;