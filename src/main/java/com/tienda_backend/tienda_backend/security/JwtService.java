
package com.tienda_backend.tienda_backend.security;

import com.tienda_backend.tienda_backend.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey claveSecreta;

    public JwtService(@Value("${jwt.secret}") String secreto) {
        this.claveSecreta = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secreto)
        );
    }

    // Duración del token: 15 minutos
    private static final long DURACION_TOKEN = 15 * 60 * 1000L;

    // Generar un token JWT
    public String generarToken(Usuario usuario) {

        long ahora = System.currentTimeMillis();

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .issuedAt(new Date(ahora))
                .expiration(new Date(ahora + DURACION_TOKEN))
                .signWith(claveSecreta)
                .compact();
    }

    // Verificar firma y vigencia del token
    public Claims obtenerDatos(String token) {

        return Jwts.parser()
                .verifyWith(claveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Obtener correo electrónico
    public String obtenerEmail(String token) {
        return obtenerDatos(token).getSubject();
    }

    // Validar token
    public boolean validarToken(String token) {

        try {
            obtenerDatos(token);
            return true;

        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException ex) {
            return false;
        }
    }

    // Renovar token mientras siga siendo válido
    public String renovarToken(String token, Usuario usuario) {

        if (!validarToken(token)) {
            throw new IllegalArgumentException(
                    "Token inválido o expirado");
        }

        String emailToken = obtenerEmail(token);

        if (!emailToken.equals(usuario.getEmail())) {
            throw new IllegalArgumentException(
                    "El token no pertenece al usuario");
        }

        return generarToken(usuario);
    }
}
