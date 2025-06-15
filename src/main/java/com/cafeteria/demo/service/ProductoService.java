package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Producto;
import com.cafeteria.demo.repository.ProductoRepository;
import com.cafeteria.demo.repository.CategoriaRepository; // Importación mantenida, asumiendo que CategoriaRepository podría ser usada en el futuro en este servicio, si no, puedes eliminarla.

import org.springframework.beans.factory.annotation.Autowired; // La anotación es opcional en constructores a partir de Spring 4.3, pero es una buena práctica para la claridad.
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importación necesaria para @Transactional.


import java.util.List;
import java.util.Optional;

@Service // Indica que es un componente de servicio de Spring
public class ProductoService { // Nombre de la clase: ProductoService

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository; // Se mantiene por ahora si es utilizada en alguna otra lógica aquí, o si se planea usar.

    // Inyección de dependencias a través del constructor
    @Autowired 
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Método para obtener todos los productos del menú público
    @Transactional(readOnly = true) // Indica que esta operación es solo de lectura
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll(); // Usa el método estándar de JPA
        System.out.println("\n--- DEBUG: Productos obtenidos del repositorio ---");
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en la base de datos.");
        } else {
            for (Producto p : productos) {
                System.out.println("   ID: " + p.getId() +
                                   ", Nombre: " + p.getNombre() +
                                   ", URL Imagen: " + p.getUrlImagen() +
                                   ", Categoría ID: " + (p.getCategoria() != null ? p.getCategoria().getId() : "NULL_OBJ_CATEGORY"));
            }
        }
        System.out.println("--- FIN DEBUG ---");
        return productos;
    }

    // Método para obtener un producto por su ID (uso público, por ejemplo, para añadir al carrito)
    @Transactional(readOnly = true) // Indica que esta operación es solo de lectura
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id); // Usa el método estándar de JPA
    }

    // Método para buscar productos por nombre (uso público, por ejemplo, en la página de menú)
    @Transactional(readOnly = true) // Indica que esta operación es solo de lectura
    public List<Producto> buscarProductosPorNombre(String searchTerm) {
        List<Producto> productos;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            productos = productoRepository.findAll();
        } else {
            productos = productoRepository.findByNombreContainingIgnoreCase(searchTerm); // Usa el método estándar de JPA
        }
        System.out.println("\n--- DEBUG: Productos buscados por '" + searchTerm + "' ---");
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos para el término de búsqueda.");
        } else {
            for (Producto p : productos) {
                System.out.println("   ID: " + p.getId() +
                                   ", Nombre: " + p.getNombre() +
                                   ", URL Imagen: " + p.getUrlImagen() +
                                   ", Categoría ID: " + (p.getCategoria() != null ? p.getCategoria().getId() : "NULL_OBJ_CATEGORY"));
            }
        }
        System.out.println("--- FIN DEBUG ---");
        return productos;
    }

    // --- Los métodos de CRUD (guardarProducto, eliminarProducto) no deberían estar en este servicio si serán manejados por SPs en ProductoAdminService ---
    // Si este servicio también manejará CRUD sin SPs para otros módulos, entonces se agregarían aquí.
    // public Producto guardarProducto(Producto producto) { ... }
    // public void eliminarProducto(Long id) { ... }
}