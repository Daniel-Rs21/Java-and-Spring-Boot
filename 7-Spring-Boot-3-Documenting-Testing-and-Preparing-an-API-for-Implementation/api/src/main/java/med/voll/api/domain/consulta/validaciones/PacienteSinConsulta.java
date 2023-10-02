package med.voll.api.domain.consulta.validaciones;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        // Validar que el paciente no tenga una consulta en el mismo día
        if(datosAgendarConsulta.idPaciente()==null) {
            return;
        }

        LocalDateTime primerHorario = datosAgendarConsulta.fecha().withHour(7);
        LocalDateTime ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        Boolean tieneConsulta = consultaRepository
                .existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);

        if (tieneConsulta) {
            throw new ValidationException("El paciente ya tiene una consulta agendada para ese día");
        }
    }
}
