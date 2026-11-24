
package com.tienda_backend.tienda_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.tienda_backend.tienda_backend.entity.Producto;

public interface ProductoRepository
        extends MongoRepository<Producto, String> {
}
