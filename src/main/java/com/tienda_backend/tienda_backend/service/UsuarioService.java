
package com.tienda_backend.tienda_backend.service;

import com.tienda_backend.tienda_backend.dto.LoginRequest;
import com.tienda_backend.tienda_backend.dto.LoginResponse;
import com.tienda_backend.tienda_backend.entity.Usuario;
import com.tienda_backend.tienda_backend.repository.UsuarioRepository;
import com.tienda_backend.tienda_backend.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registrar un nuevo cliente
    public Usuario registrarUsuario(
            String nombre,
            String email,
            String password) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo es obligatorio");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 8 caracteres");
        }

        String emailNormalizado =
                email.trim().toLowerCase(Locale.ROOT);

        if (usuarioRepository.existsByEmail(emailNormalizado)) {
            throw new IllegalArgumentException(
                    "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre.trim());
        usuario.setEmail(emailNormalizado);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(Usuario.Rol.CLIENTE);

        return usuarioRepository.save(usuario);
    }

    // Iniciar sesión y generar JWT
    public LoginResponse iniciarSesion(LoginRequest request) {

        if (request == null ||
            request.getEmail() == null ||
            request.getPassword() == null) {
            throw new IllegalArgumentException(
                    "Credenciales incorrectas");
        }

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Credenciales incorrectas"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new IllegalArgumentException(
                    "Credenciales incorrectas");
        }

        String token = jwtService.generarToken(usuario);

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }
}
