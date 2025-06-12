package com.cafeteria.demo.repository;

import com.cafeteria.demo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Puedes añadir consultas personalizadas aquí si lo necesitas más adelante
}