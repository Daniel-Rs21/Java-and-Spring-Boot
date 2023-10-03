package med.voll.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

    @MockBean
    private AgendaConsultaService agendaConsultaService;

    @Test
    @DisplayName("Debe retornar 400 al crear una consulta con datos inválidos")
    @WithMockUser
    void testCrearConsultaError400() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debe retornar 200 al crear una consulta con datos válidos")
    @WithMockUser
    void testCrearConsultaError200() throws Exception {

        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        Long idMedico = 1L;
        Long idPaciente = 1L;

        DatosAgendarConsulta datosAgendarConsulta = new DatosAgendarConsulta(null, idPaciente, idMedico, null, fecha);
        DatosDetalleConsulta datosDetalleConsulta = new DatosDetalleConsulta(null, idPaciente, idMedico, fecha);

        when(agendaConsultaService.agendar(any())).thenReturn(datosDetalleConsulta);

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(datosAgendarConsulta).getJson()))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value()); // Verificar que de el código correcto

        var jsonEsperado = detalleConsultaJacksonTester.write(datosDetalleConsulta).getJson();

        assertEquals(response.getContentAsString(), jsonEsperado); // Verificar que devuelva el json esperado
    }

}
