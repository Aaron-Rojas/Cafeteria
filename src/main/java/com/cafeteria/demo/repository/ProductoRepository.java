package com.cafeteria.demo.repository;

import com.cafeteria.demo.model.Producto; //Importamos el modelo

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository // Indica que es un componente de repositorio de Spring
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository ya te da los métodos CRUD.
    // Aquí puedes añadir métodos de búsqueda personalizados si los necesitas, por ejemplo:
    List<Producto> findByCategoriaNombre(String nombreCategoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre); // Para buscar productos por nombre

}
