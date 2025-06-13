package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaService mesaService;
    
     // **Nuevo**: traer todas las reservas
    @Transactional(readOnly = true)
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    @Transactional
    public void guardarReserva(Reserva reserva) {
        // Primero guarda la reserva
        reservaRepository.save(reserva);
        
        // Luego actualiza el estado de la mesa SOLO si es necesario
        if (reserva.getEstado() == Reserva.EstadoReserva.Confirmada && reserva.getMesa() != null) {
            // Recargar la mesa para evitar problemas de contexto de persistencia
            Mesa mesaActualizada = mesaService.obtenerMesaPorId(reserva.getMesa().getId());
            
            if(mesaActualizada != null) {
                mesaActualizada.setEstado(Mesa.EstadoMesa.Ocupada);
                mesaService.actualizarMesa(mesaActualizada);
            }
        }
    }
    

    public void eliminarReserva(Long id) { // CORREGIDO: int → Long
        reservaRepository.deleteById(id);
    }

    public Reserva obtenerReservaPorId(Long id) { // CORREGIDO: int → Long
        return reservaRepository.findById(id).orElse(null);
    }
}
