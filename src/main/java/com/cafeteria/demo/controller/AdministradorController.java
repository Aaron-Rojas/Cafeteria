package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Reserva;
import com.cafeteria.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class AdministradorController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/admin/reservas")
    public String mostrarReservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
         System.out.println("Reservas: " + reservas);
        model.addAttribute("listaReservas", reservas);
        return "admin"; // Este sería tu archivo admin.html
    }

    
}