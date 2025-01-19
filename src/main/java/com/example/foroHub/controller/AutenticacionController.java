package com.example.foroHub.controller;

import com.example.foroHub.domain.usuario.DatosAutenticacionUsuario;
import com.example.foroHub.domain.usuario.Usuario;
import com.example.foroHub.infra.security.JWTTokenDTO;
import com.example.foroHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        //Spring genera un authentication token.
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        //Se valida con la información de base de datos. Contraseñas BD deben estar encriptadas. Ejemplo: (Algoritmo bcrypt).
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        //Se genera token JWT (JSON web token).
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        //Se devuelve token.
        return ResponseEntity.ok(new JWTTokenDTO(JWTtoken));
    }
}
