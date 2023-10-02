package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        // Validar que el paciente esté activo
        if(datosAgendarConsulta.idPaciente()==null) {
            return;
        }

        Boolean activo = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get().getActivo();

        if (!activo) {
            throw new ValidationException("El paciente no está activo");
        }
    }
}
