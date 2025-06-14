package com.cafeteria.demo.repository;

import com.cafeteria.demo.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    // Métodos personalizados si necesitas más adelante
}