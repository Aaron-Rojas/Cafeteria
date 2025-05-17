package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {


    @GetMapping("/registrar") // <--- Este método recibe la petición a /registrar
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new User()); // <--- Y este añade el objeto 'usuario'
         return "Registrar"; // Usa la plantilla Registrar.html
     }
    // ...

    @GetMapping("/ingresarprimero")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new User());
        return "Ingresar";
    }

    @GetMapping("/adminprime")
    public String mostrarAdmin() {
        return "admin";
    }
}
