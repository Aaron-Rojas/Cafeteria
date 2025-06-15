package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.User;
import com.cafeteria.demo.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UserService usuarioservicio;

    @Autowired
    public UsuarioController (UserService usuarioservicio){
        this.usuarioservicio=usuarioservicio;
    }

    // --- MÉTODOS DE REGISTRO ---

    @GetMapping("/nuevousuario") //Muestra el formulario
    public String mostrarregistronuevo(Model model) {
        System.out.println("--- DEBUG UsuarioController: Entrando a mostrarregistronuevo ---");
        model.addAttribute("user", new User());
        System.out.println("--- DEBUG UsuarioController: Retornando 'Registrar' ---");
        return "Registrar";
    }


    @PostMapping("/registrarusuario") // Procesa el formulario de registro
    public String registrarusuarios(@ModelAttribute User user, RedirectAttributes redirectat) {
        System.out.println("--- DEBUG UsuarioController: Entrando a registrarusuarios ---");
        System.out.println("--- DEBUG UsuarioController: Usuario recibido: " + user.getEmail() + " ---");
        try {
            usuarioservicio.registrausuarios(user);
            redirectat.addFlashAttribute("mensaje", "Usuario registrado exitosamente!");

            if (user.getEmail().toLowerCase().startsWith("admin")) /*si el correo inicia con Admin*/ { 
                System.out.println("--- DEBUG UsuarioController: Redirigiendo admin a /adminReservas ---");
                return "redirect:/admin"; // URL del controlador de AdministradorController para admin.html
            } else {
                System.out.println("--- DEBUG UsuarioController: Redirigiendo cliente a / ---");
                return "redirect:/"; // Redirige a la página principal para clientes
            }
        } catch (IllegalArgumentException e) {
            System.out.println("--- ERROR UsuarioController: Error al registrar usuario: " + e.getMessage() + " ---");
            redirectat.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuario/nuevousuario";
        }
    }

    @GetMapping("/ingresar") // Muestra el formulario de inicio de sesión (Ingresar.html)
    public String mostrarFormularioLogin(Model model) {
        System.out.println("--- DEBUG UsuarioController: Entrando a mostrarFormularioLogin ---");
        model.addAttribute("user", new User()); // También necesita un objeto User para el formulario de login
        System.out.println("--- DEBUG UsuarioController: Retornando 'Ingresar' ---");
        return "Ingresar"; // Nombre del archivo HTML para el formulario de login
    }
    
    @PostMapping("/ingresar") // Procesa el formulario de inicio de sesión
    public String procesarLogin(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        System.out.println("--- DEBUG UsuarioController: Entrando a procesarLogin ---");
        System.out.println("--- DEBUG UsuarioController: Intento de login para email: " + user.getEmail() + " ---");

        // *** LÓGICA DE VALIDACIÓN DE CREDENCIALES (AUN NO IMPLEMENTADA COMPLETAMENTE) ***
        // Aquí deberías consultar la BD y verificar la contraseña
        Optional<User> foundUser = usuarioservicio.findByEmail(user.getEmail());

        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            // Credenciales válidas (MUY BÁSICO, NO USAR ASÍ EN PROD SIN ENCRIPTACIÓN)
            User loggedInUser = foundUser.get();
            redirectAttributes.addFlashAttribute("mensaje", "Bienvenido, " + loggedInUser.getUsername() + "!");
            System.out.println("--- DEBUG UsuarioController: Login exitoso para: " + loggedInUser.getEmail() + " ---");

            if (loggedInUser.getRolusuario() == 1L) { // Asumiendo 1L para rol de administrador
                System.out.println("--- DEBUG UsuarioController: Redirigiendo administrador a /adminReservas ---");
                return "redirect:/admin"; // Redirige al panel de administración
            } else {
                System.out.println("--- DEBUG UsuarioController: Redirigiendo usuario normal a / ---");
                return "redirect:/"; // Redirige a la página principal para usuarios normales
            }
        } else {
            // Credenciales inválidas
            redirectAttributes.addFlashAttribute("loginError", "Email o contraseña incorrectos.");
            System.out.println("--- DEBUG UsuarioController: Login fallido para: " + user.getEmail() + " ---");
            return "redirect:/usuario/ingresar"; // Vuelve al formulario de login con el error
        }
        // ***********************************************************************************
    }

}