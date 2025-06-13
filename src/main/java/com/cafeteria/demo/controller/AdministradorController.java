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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    System.out.println("Total reservas encontradas: " + reservas.size());
    reservas.forEach(System.out::println);
    model.addAttribute("reservaForm", new Reserva()); // evita errores Thymeleaf
    model.addAttribute("listaReservas", reservas);
    model.addAttribute("listaMesas", mesas);
    return "admin";
}

@PostMapping("/admin/reserva/eliminar")
public String eliminarReserva(@RequestParam Long id) {
    reservaService.eliminarReserva(id);
    return "redirect:/admin"; // REDIRECCIÓN CORRECTA
}



@GetMapping("/debug")
@ResponseBody
public List<Reserva> debugReservas() {
    return reservaService.obtenerTodasLasReservas();
}


}