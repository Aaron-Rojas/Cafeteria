
package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.User;
import com.cafeteria.demo.service.UserService; // Usaremos el UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/usuarios") // Ruta base para el admin de usuarios
public class AdminUsuarioController {

    @Autowired
    private UserService userService; // Inyectamos el servicio de usuario

    // 1. READ (Listar todos los usuarios en una tabla)
    @GetMapping
    public String listarUsuariosAdmin(Model model) {
        System.out.println("\n--- DEBUG AdminUsuarioController: Entrando a listarUsuariosAdmin ---");
        List<User> usuarios = userService.listausuarios(); // Obtenemos todos los usuarios

        System.out.println("--- DEBUG AdminUsuarioController: Usuarios encontrados: " + usuarios.size() + " ---");
        usuarios.forEach(u -> System.out.println("  ID: " + u.getUsuarioid() + ", Username: " + u.getUsername() + ", Email: " + u.getEmail() + ", Rol ID: " + u.getRolusuario()));

        model.addAttribute("usuarios", usuarios); // Pasamos la lista al modelo
        System.out.println("--- DEBUG AdminUsuarioController: Saliendo de listarUsuariosAdmin, retornando 'usuarios_lista' ---");
        return "usuarios_lista"; // Vista Thymeleaf para listar usuarios
    }

    // 2. CREATE (Mostrar formulario para nuevo usuario - si el admin lo crea)
    // Nota: El registro "público" ya lo maneja UsuarioController, este es para el admin
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        System.out.println("\n--- DEBUG AdminUsuarioController: Entrando a mostrarFormularioNuevoUsuario (Admin) ---");
        model.addAttribute("user", new User());
        // Aquí podrías cargar roles si los tuvieras en una tabla de roles para un dropdown
        System.out.println("--- DEBUG AdminUsuarioController: Saliendo de mostrarFormularioNuevoUsuario, retornando 'usuario_form' ---");
        return "usuario_form"; // Vista Thymeleaf para el formulario de usuario (admin)
    }

    // 3. CREATE/UPDATE (Guardar o Actualizar usuario desde el formulario)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        System.out.println("\n--- DEBUG AdminUsuarioController: Entrando a guardarUsuario (Admin) ---");
        System.out.println("--- DEBUG AdminUsuarioController: Datos recibidos: ID=" + user.getUsuarioid() + ", Username=" + user.getUsername() + ", Email=" + user.getEmail() + " ---");

        if (user.getUsuarioid() == null) {
            // Si el ID es null, es un nuevo usuario (admin lo está creando)
            System.out.println("--- DEBUG AdminUsuarioController: Creando nuevo usuario por Admin ---");
            // Nota: La contraseña para un nuevo usuario creado por admin debería manejarse con cuidado.
            // Por simplicidad, aquí usa el metodo de registro que asigna rol por email
            userService.registrausuarios(user);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado exitosamente!");
        } else {
            // Si el ID existe, es una actualización
            System.out.println("--- DEBUG AdminUsuarioController: Actualizando usuario existente con ID: " + user.getUsuarioid() + " ---");
            userService.modificarusuarios(user);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado exitosamente!");
        }
        System.out.println("--- DEBUG AdminUsuarioController: Saliendo de guardarUsuario, redirigiendo a '/admin/usuarios' ---");
        return "redirect:/admin/usuarios";
    }

    // 4. UPDATE (Mostrar formulario para editar usuario)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) {
        System.out.println("\n--- DEBUG AdminUsuarioController: Entrando a mostrarFormularioEditarUsuario con ID: " + id + " ---");
        Optional<User> userOptional = userService.findById(id);

        if (!userOptional.isPresent()) {
            System.out.println("--- ERROR AdminUsuarioController: Usuario con ID " + id + " no encontrado para editar. ---");
            throw new IllegalArgumentException("ID de usuario inválido:" + id);
        }

        User user = userOptional.get();
        model.addAttribute("user", user);
        System.out.println("--- DEBUG AdminUsuarioController: Saliendo de mostrarFormularioEditarUsuario, retornando 'usuario_form' ---");
        return "usuario_form"; // Reutiliza el mismo formulario
    }

    // 5. DELETE (Eliminar usuario)
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("\n--- DEBUG AdminUsuarioController: Entrando a eliminarUsuario con ID: " + id + " ---");
        userService.borrarusuarioporid(id);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente!");
        System.out.println("--- DEBUG AdminUsuarioController: Saliendo de eliminarUsuario, redirigiendo a '/admin/usuarios' ---");
        return "redirect:/admin/usuarios";
    }
}