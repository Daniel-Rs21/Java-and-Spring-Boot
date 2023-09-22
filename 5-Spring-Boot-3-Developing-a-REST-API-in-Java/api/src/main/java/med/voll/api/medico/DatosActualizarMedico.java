package med.voll.api.medico;

import io.micrometer.common.lang.NonNull;
import med.voll.api.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NonNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion) {

}
