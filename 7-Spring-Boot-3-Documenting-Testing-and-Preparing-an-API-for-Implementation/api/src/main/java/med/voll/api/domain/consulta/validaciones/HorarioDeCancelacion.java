package med.voll.api.domain.consulta.validaciones;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;

public class HorarioDeCancelacion implements ValidadorDeCancelacion {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelarConsulta datosCancelarConsulta) {
        LocalDateTime horaActual = LocalDateTime.now();
        LocalDateTime horaConsulta = consultaRepository.findById(datosCancelarConsulta.idConsulta()).get().getFecha();

        if (horaActual.plusHours(24).isAfter(horaConsulta)) {
            throw new ValidationException("La consulta debe cancelarse con al menos 24 horas de anticipación");
        }
    }

}
