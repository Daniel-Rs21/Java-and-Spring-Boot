CREATE TABLE consultas(

    id BIGINT NOT NULL AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    fecha DATETIME NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT  fix_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    CONSTRAINT  fix_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medicos(id)
)