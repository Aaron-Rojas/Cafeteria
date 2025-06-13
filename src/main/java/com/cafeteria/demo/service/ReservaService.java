package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaService mesaService; // o usa MesaRepository directamente si no tienes servicio


    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public void guardarReserva(Reserva reserva) {
        reservaRepository.save(reserva);
        if (reserva.getEstado() == Reserva.EstadoReserva.Confirmada && reserva.getMesa() != null) {
        Mesa mesa = reserva.getMesa();
        mesa.setEstado(Mesa.EstadoMesa.Ocupada);
        mesaService.guardarMesa(mesa);
    }

    }

    public void eliminarReserva(Long id) { // CORREGIDO: int → Long
        reservaRepository.deleteById(id);
    }

    public Reserva obtenerReservaPorId(Long id) { // CORREGIDO: int → Long
        return reservaRepository.findById(id).orElse(null);
    }
}
