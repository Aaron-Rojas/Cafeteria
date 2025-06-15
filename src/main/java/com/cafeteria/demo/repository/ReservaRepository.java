package com.cafeteria.demo.repository;

import com.cafeteria.demo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {


}