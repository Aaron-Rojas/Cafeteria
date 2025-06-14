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

    // GET /reserva/form
    @GetMapping("/form")
    public String mostrarFormularioReserva(Model model,
                                          @RequestParam(required = false) String success) {
        // Instancia vacía para binding en Thymeleaf
        model.addAttribute("reserva", new Reserva());
        // Pasamos también las mesas disponibles
        model.addAttribute("mesas", mesaService.obtenerTodasLasMesas()
                                        .stream()
                                        .filter(m -> m.getEstado() == Mesa.EstadoMesa.Disponible)
                                        .toList());
        // Indicador para mensaje de éxito
        model.addAttribute("success", success != null);
        return "reserva-cliente";
    }

    // POST /reserva/guardar
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
        reserva.setFechaHoraReserva(
            LocalDateTime.parse(fechaHoraReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        );
        reserva.setDuracionMinutos(duracionMinutos);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado(Reserva.EstadoReserva.Pendiente);
        reserva.setNotas(notas);

        reservaService.guardarReserva(reserva);

        // Redirigimos con flag para mostrar mensaje
        return "redirect:/reserva/form?success";
    }
}
