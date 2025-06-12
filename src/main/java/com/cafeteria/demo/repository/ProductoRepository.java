package com.cafeteria.demo.repository;

import com.cafeteria.demo.model.Producto; //Importamos el modelo

import org.springframework.data.jpa.repository.Query; // Necesario para @Query ye el SP
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal; 
import java.util.List;
import java.util.Optional;

@Repository // Indica que es un componente de repositorio de Spring
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository ya te da los métodos CRUD.
    List<Producto> findByCategoriaNombre(String nombreCategoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre); // Para buscar productos por nombre
    
    @Query
    (value = "CALL GetAllProductsWithCategoryName()", nativeQuery = true)
    List<Object[]> findAllProductsWithCategoryNameSP();

     // Método para llamar al procedimiento almacenado InsertProduct
    @Procedure(procedureName = "InsertProduct") void insertProduct(String p_nombre, String p_descripcion, BigDecimal p_precio, String p_url_imagen, Long p_categoria_id);

    // Método para llamar al procedimiento almacenado UpdateProduct
    @Procedure(procedureName = "UpdateProduct") void updateProduct(Long p_id, String p_nombre, String p_descripcion, BigDecimal p_precio, String p_url_imagen, Long p_categoria_id);

    // Método para llamar al procedimiento almacenado DeleteProduct
    @Procedure(procedureName = "DeleteProduct") void deleteProduct(Long p_id);
}
