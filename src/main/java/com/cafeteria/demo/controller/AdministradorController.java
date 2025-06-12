package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Reserva;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestParam;
 




@Controller
public class AdministradorController {
    


    @GetMapping("/adminReservas")
    public String mostrarReservas(Model model) {
        // Simulación de una lista de reservas (puedes conectar a BD más adelante)
        List<Reserva> reservas = new ArrayList<>();


        //reservas.add(new Reserva(1, "Mark", "Rodríguez", "Rodri23@gmail.com", ,"567 567 567"));
        //reservas.add(new Reserva(2, "Jacob", "López", "Jaclop22@gmail.com", "787 878 787"));
        //reservas.add(new Reserva(3, "John", "Castillo", "Jcasti20@hotmail.com", "453 453 345"));

        // Pasar la lista al modelo
        model.addAttribute("listaReservas",reservas);

        // Mostrar la vista "reservas.html"
        return "admin";
    }

}