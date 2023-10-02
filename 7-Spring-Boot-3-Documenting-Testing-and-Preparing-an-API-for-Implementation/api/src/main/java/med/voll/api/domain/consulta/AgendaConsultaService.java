package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadoresDeConsultas;


    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta) {

        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()) {
            throw new ValidationException("ID de paciente no encontrado");
        }

        if (datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())) {
            throw new ValidationException("ID de médico no encontrado");
        }

        validadoresDeConsultas.forEach(validador -> validador.validar(datosAgendarConsulta));

        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        Medico medico = elegirMedico(datosAgendarConsulta);

        if(medico==null) {
            throw new ValidationException("No hay médicos disponibles para la especialidad y fecha indicada");
        }

        Consulta consulta = new Consulta(null, paciente, medico, datosAgendarConsulta.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico elegirMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if (datosAgendarConsulta.idMedico() != null) {
            return medicoRepository.findById(datosAgendarConsulta.idMedico()).get();
        }

        Especialidad especialidad = datosAgendarConsulta.especialidad();

        return medicoRepository.seleccionarMedicoAdecuado(especialidad, datosAgendarConsulta.fecha());
    }

}
