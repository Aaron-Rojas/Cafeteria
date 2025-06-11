package com.cafeteria.demo.service; 

import com.cafeteria.demo.model.Producto; // Ajusta el paquete
import com.cafeteria.demo.repository.ProductoRepository; 
import com.cafeteria.demo.repository.CategoriaRepository;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional; // Para findByID

@Service // Indica que es un componente de servicio de Spring
public class MenuService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Inyección de dependencias a través del constructor
    public MenuService(ProductoRepository productoRepository , CategoriaRepository categoriaRepository ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Método para obtener todos los productos del menú 
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        // --- INICIO: DEBUGGING ---
        System.out.println("\n--- DEBUG: Productos obtenidos del repositorio ---");
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en la base de datos.");
        } else {
            for (Producto p : productos) {
                System.out.println("  ID: " + p.getId() +
                                   ", Nombre: " + p.getNombre() +
                                   ", URL Imagen: " + p.getUrlImagen() +
                                   ", Categoría ID: " + (p.getCategoria() != null ? p.getCategoria().getId() : "NULL_OBJ_CATEGORY"));
            }
        }
        System.out.println("--- FIN DEBUG ---");
        // --- FIN: DEBUGGING ---
        return productos;
    }

    // Método para obtener un producto por su ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Métodos para gestionar productos (CRUD)
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto); // Guarda (crea o actualiza) un producto
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id); // Elimina un producto por ID
    }

    // Puedes añadir más métodos como buscar por categoría, actualizar, etc.
    public List<Producto> buscarProductosPorNombre(String searchTerm) {
        List<Producto> productos;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            productos = productoRepository.findAll();
        } else {
            productos = productoRepository.findByNombreContainingIgnoreCase(searchTerm);
        }
        // --- INICIO: DEBUGGING DE BÚSQUEDA ---
        System.out.println("\n--- DEBUG: Productos buscados por '" + searchTerm + "' ---");
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos para el término de búsqueda.");
        } else {
             for (Producto p : productos) {
                System.out.println("  ID: " + p.getId() +
                                   ", Nombre: " + p.getNombre() +
                                   ", URL Imagen: " + p.getUrlImagen() +
                                   ", Categoría ID: " + (p.getCategoria() != null ? p.getCategoria().getId() : "NULL_OBJ_CATEGORY"));
            }
        }
        System.out.println("--- FIN DEBUG ---");
        // --- FIN: DEBUGGING DE BÚSQUEDA ---
        return productos;
    }
}
