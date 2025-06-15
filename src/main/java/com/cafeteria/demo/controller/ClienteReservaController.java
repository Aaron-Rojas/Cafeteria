
package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.MesaService;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ClienteReservaController {

    private final ReservaService reservaService;
    private final MesaService mesaService;

    @Autowired
    public ClienteReservaController(ReservaService reservaService, MesaService mesaService) {
        this.mesaService = mesaService;
        this.reservaService = reservaService;
    }

    // GET /reserva/form
    @GetMapping("/form")
    public String mostrarFormularioReserva(Model model,
            @RequestParam(required = false) String success,
            @RequestParam(required = false) String error) {

        System.out.println("--- DEBUG ClienteReservaController: Entrando a /reserva/form ---");
        // Instancia vacía para binding en Thymeleaf
        model.addAttribute("reserva", new Reserva());
        // Obtener solo las mesas disponibles
        List<Mesa> mesasDisponibles = mesaService.obtenerTodasLasMesas()
                .stream()
                .filter(m -> m.getEstado() == Mesa.EstadoMesa.Disponible)
                .toList();
        model.addAttribute("mesas", mesasDisponibles);

        // Indicador para mensajes de éxito/error
        if (success != null) {
            model.addAttribute("mensaje", "Reserva realizada exitosamente!");
        }
        if (error != null) {
            model.addAttribute("error", error); // Pasa el mensaje de error
        }

        // Pasamos también las mesas disponibles
        model.addAttribute("mesas", mesaService.obtenerTodasLasMesas()
                .stream()
                .filter(m -> m.getEstado() == Mesa.EstadoMesa.Disponible)
                .toList());
        // Indicador para mensaje de éxito
        System.out.println("--- DEBUG ClienteReservaController: Retornando 'reserva-cliente' vista ---");
        return "reserva-cliente"; // Nombre del archivo HTML
    }

    // POST /reserva/guardar
    @PostMapping("/guardar")
    public String guardarReservaCliente(
            @RequestParam Long usuarioId,
            @RequestParam Long mesaId,
            @RequestParam String fechaHoraReserva,
            @RequestParam Integer duracionMinutos,
            @RequestParam Integer cantidadPersonas,
            @RequestParam(required = false) String notas,
            RedirectAttributes redirectAttributes) {

        System.out.println("--- DEBUG ClienteReservaController: Guardando reserva de cliente ---");
        Reserva reserva = new Reserva();

        // Buscar la Mesa por ID usando Optional
        Optional<Mesa> mesaOptional = mesaService.obtenerMesaPorId(mesaId);

        if (mesaOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Mesa seleccionada no encontrada.");
            System.err.println("--- ERROR ClienteReservaController: Mesa no encontrada para ID: " + mesaId + " ---");
            return "redirect:/reserva/form"; // Redirige de vuelta al formulario con error
        }

        reserva.setUsuarioId(usuarioId);
        reserva.setMesa(mesaOptional.get());
        try {
            // Parsear la fecha y hora. Asegurarse que el formato del input HTML coincide.
            reserva.setFechaHoraReserva(
                    LocalDateTime.parse(fechaHoraReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Formato de fecha y hora inválido.");
            System.err.println(
                    "--- ERROR ClienteReservaController: Formato de fecha/hora inválido: " + e.getMessage() + " ---");
            return "redirect:/reserva/form";
        }

        reserva.setDuracionMinutos(duracionMinutos);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado(Reserva.EstadoReserva.Pendiente);
        reserva.setNotas(notas);
        try {
            reservaService.guardarReserva(reserva);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva realizada exitosamente!"); // Mensaje de éxito
            System.out.println("--- DEBUG ClienteReservaController: Reserva de cliente guardada exitosamente. ---");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al realizar la reserva: " + e.getMessage()); // Mensaje
                                                                                                              // de
                                                                                                              // error
            System.err.println("--- ERROR ClienteReservaController: Error al guardar reserva de cliente: "
                    + e.getMessage() + " ---");
        }

        return "redirect:/reserva/form?success";
    }
}