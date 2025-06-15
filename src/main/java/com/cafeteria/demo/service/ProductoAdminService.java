package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Categoria;
import com.cafeteria.demo.model.Producto; // Ajusta el paquete

import com.cafeteria.demo.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import com.cafeteria.demo.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional; // Para findByID

@Service // Indica que es un componente de servicio de Spring
public class ProductoAdminService {
    
    @Autowired
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoAdminService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }


    // Métodos para el Admin usando Procedimientos Almacenados    
    // Método para obtener todos los productos con el nombre de su categoría desde el SP
    public List<Object[]> obtenerTodosLosProductosConCategoriaParaAdmin() {
        return productoRepository.findAllProductsWithCategoryNameSP();
    }

    // Métodos CRUD para el Admin que usarán los SPs 
    @Transactional // Las operaciones que modifican la BD deben ser transaccionales
    public void guardarProductoConSP(Producto producto) { //Recibe el objeto Producto
        productoRepository.insertProduct(
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getUrlImagen(),
            producto.getCategoria() != null ? producto.getCategoria().getId() : null // Pasa el ID de la categoría
        );
    }
    @Transactional
    public void actualizarProductoConSP(Producto producto) {
        // Llama al SP UpdateProduct
        productoRepository.updateProduct(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getUrlImagen(),
            producto.getCategoria() != null ? producto.getCategoria().getId() : null
        );
    }

    
    @Transactional
    public void eliminarProductoConSP(Long id) {
        productoRepository.deleteProduct(id);
    }

    // --- Métodos Auxiliares para CRUD si no usas SPs inicialmente para save/update/delete ---
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }


}