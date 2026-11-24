
package com.tienda_backend.tienda_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tienda_backend.tienda_backend.entity.Usuario;

public interface UsuarioRepository
        extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
