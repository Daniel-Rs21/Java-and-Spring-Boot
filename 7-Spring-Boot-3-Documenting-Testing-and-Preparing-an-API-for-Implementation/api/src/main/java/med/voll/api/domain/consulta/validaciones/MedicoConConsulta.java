package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() == null) {
            return;
        }

        Boolean tieneConsulta = consultaRepository
                .existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fecha());

        if (tieneConsulta) {
            throw new ValidationException("El medico ya tiene una consulta agendada para esa hora");
        }
    }
}
