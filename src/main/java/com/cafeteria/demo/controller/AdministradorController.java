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


import java.time.LocalDateTime;
import java.util.List;


@Controller
public class AdministradorController {

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private MesaService mesaService;

    @GetMapping("/admin")
    public String mostrarReservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        List<Mesa> mesas = mesaService.obtenerTodasLasMesas();
        model.addAttribute("reservaForm", new Reserva());
        model.addAttribute("listaReservas", reservas);
        model.addAttribute("listaMesas", mesas);
        return "admin";
    }

    @PostMapping("/admin/reserva/eliminar")
    public String eliminarReserva(@RequestParam Long id) {
        reservaService.eliminarReserva(id);
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
        @RequestParam(required = false) String notas) {

    // Obtener la mesa primero
    Mesa mesa = mesaService.obtenerMesaPorId(mesaId);
    if(mesa == null) {
        return "redirect:/admin?error=Mesa+no+encontrada";
    }

    Reserva reserva = new Reserva();
    reserva.setUsuarioId(usuarioId);
    reserva.setMesa(mesa); // Asignar el objeto mesa completo
    reserva.setFechaHoraReserva(fechaHoraReserva);
    reserva.setDuracionMinutos(duracionMinutos);
    reserva.setCantidadPersonas(cantidadPersonas);
    reserva.setEstado(Reserva.EstadoReserva.valueOf(estado));
    reserva.setNotas(notas);

    reservaService.guardarReserva(reserva);
    
    return "redirect:/admin";
}
}