
package com.tienda_backend.tienda_backend.controller;

import com.tienda_backend.tienda_backend.dto.LoginRequest;
import com.tienda_backend.tienda_backend.dto.LoginResponse;
import com.tienda_backend.tienda_backend.dto.RegistroRequest;
import com.tienda_backend.tienda_backend.dto.RegistroResponse;
import com.tienda_backend.tienda_backend.entity.Usuario;
import com.tienda_backend.tienda_backend.repository.UsuarioRepository;
import com.tienda_backend.tienda_backend.security.JwtService;
import com.tienda_backend.tienda_backend.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public UsuarioController(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            JwtService jwtService) {

        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    // Registrar un nuevo cliente
    @PostMapping("/registro")
    public ResponseEntity<RegistroResponse> registrar(
            @RequestBody RegistroRequest request) {

        Usuario usuario = usuarioService.registrarUsuario(
                request.getNombre(),
                request.getEmail(),
                request.getPassword()
        );

        RegistroResponse response = new RegistroResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                usuarioService.iniciarSesion(request)
        );
    }

    // Renovar un JWT todavía válido
    @PostMapping("/renovar")
    public ResponseEntity<LoginResponse> renovar(
            @RequestHeader("Authorization") String authorization) {

        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String token = authorization.substring(7);

        if (!jwtService.validarToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String email = jwtService.obtenerEmail(token)
                .trim()
                .toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String nuevoToken = jwtService.renovarToken(token, usuario);

        return ResponseEntity.ok(
                new LoginResponse(
                        nuevoToken,
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getRol().name()
                )
        );
    }

    // Manejar errores de registro y login
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarError(
            IllegalArgumentException ex) {

        if ("Credenciales incorrectas".equals(ex.getMessage())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
