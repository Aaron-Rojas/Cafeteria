package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AdministradorController {

    @Autowired
    private ReservaService reservaService;

   @GetMapping("/admin")
public String mostrarReservas(Model model) {
    List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
    System.out.println("📦 Total reservas encontradas: " + reservas.size());
    reservas.forEach(System.out::println);

    model.addAttribute("listaReservas", reservas);
    return "admin";
}

@GetMapping("/debug")
@ResponseBody
public List<Reserva> debugReservas() {
    return reservaService.obtenerTodasLasReservas();
}


}