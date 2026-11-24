
package com.tienda_backend.tienda_backend.controller;

import com.tienda_backend.tienda_backend.entity.Producto;
import com.tienda_backend.tienda_backend.service.ProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET: listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // GET: buscar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(
            @PathVariable String id) {

        return ResponseEntity.ok(
            productoService.buscarProducto(id)
        );
    }

    // POST: crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
            @RequestBody Producto producto) {

        Producto nuevo = productoService.crearProducto(producto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    // PUT: actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable String id,
            @RequestBody Producto producto) {

        return ResponseEntity.ok(
            productoService.actualizarProducto(id, producto)
        );
    }

    // DELETE: eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable String id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }

    // Manejo de errores de validación
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarError(
            IllegalArgumentException ex) {

        String mensaje = ex.getMessage();

        if ("Producto no encontrado".equals(mensaje)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mensaje);
        }

        return ResponseEntity.badRequest().body(mensaje);
    }
}
