package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {


    private final ReservaRepository reservaRepository;
    private final MesaService mesaService;
    
    @Autowired
    public ReservaService(ReservaRepository reservaRepository, MesaService mesaService) {
        this.reservaRepository = reservaRepository;
        this.mesaService = mesaService;
    }

     // **Nuevo**: traer todas las reservas
    @Transactional(readOnly = true)
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    @Transactional
    public Reserva guardarReserva(Reserva reserva) {
        // Primero guarda la reserva
       Reserva savedReserva = reservaRepository.save(reserva);
        
        // Luego actualiza el estado de la mesa SOLO si es necesario
        if (reserva.getEstado() == Reserva.EstadoReserva.Confirmada && reserva.getMesa() != null) {
            // Recargar la mesa para evitar problemas de contexto de persistencia
            Optional <Mesa> mesaOptional = mesaService.obtenerMesaPorId(reserva.getMesa().getId());
        
            if(mesaOptional.isPresent()) {
                Mesa mesaActualizada = mesaOptional.get();
                if (mesaActualizada.getEstado() != Mesa.EstadoMesa.Ocupada) {
                    mesaActualizada.setEstado(Mesa.EstadoMesa.Ocupada);
                    mesaService.guardarMesa(mesaActualizada);
                }else{
                    System.err.println("Advertencia: Mesa asociada a la reserva no encontrada. ID: " + savedReserva.getMesa().getId());
                }
            }
        }
        // Lógica para liberar la mesa si la reserva se cancela o completa también iría aquí a futuro
        // else if (savedReserva.getEstado() == Reserva.EstadoReserva.Cancelada || savedReserva.getEstado() == Reserva.EstadoReserva.Completada) {
        // Lógica para marcar la mesa como Disponible
        // }
        return savedReserva;
    }
    
        @Transactional
        public void eliminarReserva(Long id) {
            // Opcional: Obtener la reserva para liberar la mesa antes de eliminarla
            // Esto es si se elimina una reserva Confirmada y la mesa debe liberarse
            Optional<Reserva> reservaOptional = reservaRepository.findById(id);
            if(reservaOptional.isPresent()) {
                Reserva reserva = reservaOptional.get();
                if(reserva.getEstado() == Reserva.EstadoReserva.Confirmada && reserva.getMesa() != null) {
                    Optional<Mesa> mesaOptional = mesaService.obtenerMesaPorId(reserva.getMesa().getId());
                    if(mesaOptional.isPresent()) {
                        Mesa mesa = mesaOptional.get();
                        mesa.setEstado(Mesa.EstadoMesa.Disponible); // O el estado apropiado
                        mesaService.guardarMesa(mesa);
                    }
                }
            }
            reservaRepository.deleteById(id);
        }

        @Transactional(readOnly = true)
        public Optional<Reserva> obtenerReservaPorId(Long id) { // Cambiado a Optional<Reserva>
            return reservaRepository.findById(id);
        }
}