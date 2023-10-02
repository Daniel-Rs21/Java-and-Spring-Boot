package med.voll.api.domain.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeConsultas implements ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta) {

        // Validar que no sea domingo y esteé en el horario correcto
        Boolean domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());
        Boolean apertura = datosAgendarConsulta.fecha().getHour() < 7;
        Boolean cierre = datosAgendarConsulta.fecha().getHour() > 19;

        if (domingo || apertura || cierre) {
            throw new ValidationException(
                    "El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
