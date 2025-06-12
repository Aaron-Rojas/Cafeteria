package com.cafeteria.demo;

import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.model.Reserva.EstadoReserva;
import com.cafeteria.demo.repository.ReservaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner insertarReservaDePrueba(ReservaRepository reservaRepository) {
        return args -> {
            if (reservaRepository.count() == 0) {
                Reserva r = new Reserva();
                r.setUsuarioId(1L);
                r.setMesaId(1L);
                r.setFechaHoraReserva(LocalDateTime.of(2025, 6, 15, 14, 30));
                r.setDuracionMinutos(60);
                r.setCantidadPersonas(4);
                r.setEstado(EstadoReserva.Confirmada);
                r.setNotas("Reserva automática para prueba");
                reservaRepository.save(r);
                System.out.println("🔥 Reserva de prueba insertada.");
            } else {
                System.out.println("ℹ️ Ya existen reservas en la base.");
            }
        };
    }
}
