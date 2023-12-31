package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuarios;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJwtToken;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJwtToken> autenticarUsuario(
            @RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuarios.login(),
                datosAutenticacionUsuarios.clave());

        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

        String JwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJwtToken(JwtToken));
    }
}
