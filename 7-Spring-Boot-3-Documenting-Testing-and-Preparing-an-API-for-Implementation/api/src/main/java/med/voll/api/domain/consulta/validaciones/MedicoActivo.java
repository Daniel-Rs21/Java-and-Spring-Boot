package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class MedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        // Validar que el medico esté activo
        if(datosAgendarConsulta.idMedico()==null) {
            return;
        }

        Boolean activo = medicoRepository.findById(datosAgendarConsulta.idMedico()).get().getActivo();

        if (!activo) {
            throw new ValidationException("El medico no está activo");
        }
    }
}
