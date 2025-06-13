// src/main/java/com/cafeteria/demo/controller/ClienteReservaController.java
package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.MesaService;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/reserva")
public class ClienteReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private MesaService mesaService;

    // Muestra el formulario en /reserva/form
    @GetMapping("/form")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva-cliente";
    }

    // Guarda la reserva cuando se envía el form
    @PostMapping("/guardar")
    public String guardarReservaCliente(
            @RequestParam Long usuarioId,
            @RequestParam Long mesaId,
            @RequestParam String fechaHoraReserva,
            @RequestParam Integer duracionMinutos,
            @RequestParam Integer cantidadPersonas,
            @RequestParam(required = false) String notas) {

        Reserva reserva = new Reserva();
        reserva.setUsuarioId(usuarioId);
        reserva.setMesa(mesaService.obtenerMesaPorId(mesaId));
        reserva.setFechaHoraReserva(LocalDateTime.parse(fechaHoraReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        reserva.setDuracionMinutos(duracionMinutos);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado(Reserva.EstadoReserva.Pendiente); // ⚠️ Cliente -> Pendiente
        reserva.setNotas(notas);

        reservaService.guardarReserva(reserva);

        return "redirect:/reserva/form?success";
    }
}
