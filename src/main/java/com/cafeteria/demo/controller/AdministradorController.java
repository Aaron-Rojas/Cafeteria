package com.cafeteria.demo.controller;


import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.MesaService;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importar RedirectAttributes


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Controller
public class AdministradorController {
    private final ReservaService reservaService;
    private final MesaService mesaService;

    @Autowired // Inyección por constructor
    public AdministradorController(ReservaService reservaService, MesaService mesaService) {
        this.reservaService = reservaService;
        this.mesaService = mesaService;
    }

    @GetMapping("/admin")
    public String mostrarReservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        List<Mesa> mesas = mesaService.obtenerTodasLasMesas();
        model.addAttribute("reservaForm", new Reserva());
        model.addAttribute("listaReservas", reservas);
        model.addAttribute("listaMesas", mesas);
        System.out.println("--- DEBUG AdministradorController: Retornando 'admin' vista ---");
        return "admin";
    }

    @PostMapping("/admin/reserva/eliminar")
    public String eliminarReserva(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        System.out.println("--- DEBUG AdministradorController: Eliminando reserva con ID: " + id + " ---");
        try {
            reservaService.eliminarReserva(id);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva eliminada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la reserva: " + e.getMessage());
            System.err.println("--- ERROR AdministradorController: Error al eliminar reserva: " + e.getMessage());            
        }
        return "redirect:/admin";
    }


@PostMapping("/admin/reserva/guardar")
public String guardarReserva(
        @RequestParam Long usuarioId,
        @RequestParam Long mesaId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHoraReserva,
        @RequestParam int duracionMinutos,
        @RequestParam int cantidadPersonas,
        @RequestParam String estado,
        @RequestParam(required = false) String notas,
        RedirectAttributes redirectAttributes) {
            
        System.out.println("--- DEBUG AdministradorController: Guardando/Actualizando reserva ---");

        // Obtener la mesa primero
        Optional<Mesa> mesaOptional = mesaService.obtenerMesaPorId(mesaId);
        if(mesaOptional.isEmpty()) {
            System.out.println("--- ERROR AdministradorController: Mesa no encontrada para ID: " + mesaId + " ---");
            redirectAttributes.addFlashAttribute("error", "Mesa no encontrada para la reserva.");
            return "redirect:/admin";
        }
        Mesa mesa = mesaOptional.get(); // Obtener la mesa si Optional.isPresent()
        Reserva reserva = new Reserva();

        
        reserva.setUsuarioId(usuarioId);
        reserva.setMesa(mesa); // Asignar el objeto mesa completo
        reserva.setFechaHoraReserva(fechaHoraReserva);
        reserva.setDuracionMinutos(duracionMinutos);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setNotas(notas);
        // Convertir String a enum EstadoReserva
        try {
            reserva.setEstado(Reserva.EstadoReserva.valueOf(estado));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Estado de reserva inválido: " + estado);
            System.err.println("--- ERROR AdministradorController: Estado de reserva inválido: " + estado + " ---");
            return "redirect:/admin";
        }
        reserva.setNotas(notas);
        try {
            reservaService.guardarReserva(reserva); // Guarda la reserva
            redirectAttributes.addFlashAttribute("mensaje", "Reserva guardada exitosamente!");
            System.out.println("--- DEBUG AdministradorController: Reserva guardada exitosamente. ---");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la reserva: " + e.getMessage());
            System.err.println("--- ERROR AdministradorController: Error al guardar reserva: " + e.getMessage());
        }
        
        return "redirect:/admin"; // Redirige de vuelta al panel de admin
    }
   

}
