
package com.tienda_backend.tienda_backend.service;

import com.tienda_backend.tienda_backend.entity.Producto;
import com.tienda_backend.tienda_backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtener todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    public Producto buscarProducto(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("Producto no encontrado"));
    }

    // Crear producto
    public Producto crearProducto(Producto producto) {
        validarProducto(producto);
        producto.setId(null);

        if (producto.getDescuento() == null) {
            producto.setDescuento(0);
        }

        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Producto actualizarProducto(String id, Producto datos) {
        Producto producto = buscarProducto(id);

        validarProducto(datos);

        producto.setNombre(datos.getNombre());
        producto.setCategoria(datos.getCategoria());
        producto.setPrecio(datos.getPrecio());
        producto.setImagen(datos.getImagen());
        producto.setColor(datos.getColor());
        producto.setDetalle(datos.getDetalle());
        producto.setEmoji(datos.getEmoji());
        producto.setDescuento(
            datos.getDescuento() == null ? 0 : datos.getDescuento()
        );

        return productoRepository.save(producto);
    }

    // Eliminar producto
    public void eliminarProducto(String id) {
        Producto producto = buscarProducto(id);
        productoRepository.delete(producto);
    }

    // Validaciones de negocio
    private void validarProducto(Producto producto) {

        if (producto.getNombre() == null ||
            producto.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                "El nombre es obligatorio");
        }

        if (producto.getCategoria() == null ||
            producto.getCategoria().isBlank()) {
            throw new IllegalArgumentException(
                "La categoría es obligatoria");
        }

        if (producto.getPrecio() == null ||
            producto.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                "El precio debe ser mayor que cero");
        }

        if (producto.getDescuento() != null &&
            (producto.getDescuento() < 0 ||
             producto.getDescuento() > 100)) {
            throw new IllegalArgumentException(
                "El descuento debe estar entre 0 y 100");
        }
    }
}
