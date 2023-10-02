package med.voll.api.domain.medico;

import io.micrometer.common.lang.NonNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NonNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion) {

}
