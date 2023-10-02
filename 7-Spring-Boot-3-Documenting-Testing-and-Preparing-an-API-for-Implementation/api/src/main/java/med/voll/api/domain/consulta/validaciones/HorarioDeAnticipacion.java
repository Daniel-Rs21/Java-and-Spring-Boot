package med.voll.api.domain.consulta.validaciones;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        // Validar que la consulta se agende con al menos 30 horas de anticipación
        LocalDateTime horaActual = LocalDateTime.now();
        LocalDateTime horaConsulta = datosAgendarConsulta.fecha();

        if (horaActual.plusHours(30).isAfter(horaConsulta)) {
            throw new ValidationException("La consulta debe agendarse con al menos 30 horas de anticipación");
        }
    }
}
