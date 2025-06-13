package com.cafeteria.demo.controller;


import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.MesaService;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/admin/reserva")
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

    @PostMapping("/eliminar")
    public String eliminarReserva(@RequestParam Long id) {
        reservaService.eliminarReserva(id);
        return "redirect:/admin/reserva/admin";
    }

    @PostMapping("/guardar")
    public String guardarReserva(@RequestParam Long usuarioId,
                                 @RequestParam Long mesaId,
                                 @RequestParam String fechaHoraReserva,
                                 @RequestParam Integer duracionMinutos,
                                 @RequestParam Integer cantidadPersonas,
                                 @RequestParam String estado,
                                 @RequestParam(required = false) String notas) {

        Reserva reserva = new Reserva();
        reserva.setUsuarioId(usuarioId);
        reserva.setMesa(mesaService.obtenerMesaPorId(mesaId));
        reserva.setFechaHoraReserva(LocalDateTime.parse(fechaHoraReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        reserva.setDuracionMinutos(duracionMinutos);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado(Reserva.EstadoReserva.valueOf(estado));
        reserva.setNotas(notas);

        reservaService.guardarReserva(reserva);
        return "redirect:/admin/reserva/admin";
    }
}


