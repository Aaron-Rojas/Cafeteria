package com.cafeteria.demo.repository; 

import com.cafeteria.demo.model.Categoria; // Ajusta el paquete
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que es un componente de repositorio de Spring
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // JpaRepository ya proporciona métodos CRUD básicos (save, findById, findAll, delete, etc.)
    // Puedes añadir métodos de búsqueda personalizados si los necesitas, ej:
    // Optional<Categoria> findByNombre(String nombre);
}