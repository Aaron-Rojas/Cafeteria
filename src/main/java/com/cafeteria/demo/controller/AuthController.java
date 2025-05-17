package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.User; // Importar la clase User
import com.cafeteria.demo.service.UserService; // Importar el servicio de usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importar Model
import org.springframework.validation.BindingResult; // Importar BindingResult
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Importar ModelAttribute
import org.springframework.web.bind.annotation.PostMapping; // Importar PostMapping
import org.springframework.web.bind.annotation.RequestMapping; // Opcional: mapeo a nivel de clase
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importar RedirectAttributes
import jakarta.servlet.http.HttpSession; // Importar HttpSession (si lo usas para login en el futuro)
import org.springframework.http.HttpStatus; // Importar HttpStatus (si lo usas en respuestas ResponseEntity, aunque aquí no retornamos ResponseEntity)


@Controller // Indica que es un controlador de Spring
@RequestMapping("/auth") 
public class AuthController {

    @Autowired 
    private UserService userService;


    @GetMapping("/registrar") // Mapea la petición GET a /auth/registrar
    public String mostrarFormularioRegistro(Model model) {
        // Añade un nuevo objeto User vacío al modelo con el nombre "usuario"
        model.addAttribute("usuario", new User());
        System.out.println("Serviendo formulario de registro."); // Log para verificar
        return "Registrar"; // Retorna el nombre de la plantilla HTML (Registrar.html)
    }

    // --- MÉTODO COPIADO DESDE UserController (@PostMapping("/registrar")) ---
    // Lo renombraremos y/o ajustaremos el mapeo según el @RequestMapping de la clase
    @PostMapping("/registrar") // Mapea la petición POST a /auth/registrar
    public String procesarRegistro(@ModelAttribute("usuario") User user,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {

        System.out.println("Procesando registro para usuario: " + user.getUsername() + " (" + user.getEmail() + ")"); // Log

        // Validar campos básicos si es necesario (ej: si email o password llegan nulos/vacíos)
         if (result.hasErrors()) {
             System.out.println("Errores de vinculación: " + result.getAllErrors());
             return "Registrar"; // Vuelve a mostrar el formulario con errores
         }
        // Aunque th:field y required en HTML ya manejan parte de esto, es bueno tenerlo en server.
        // Para validaciones más robustas, necesitarías anotaciones @Valid en User y dependencias adicionales.


        // Verificar si el email ya existe usando el UserService
        if (userService.existeUsuario(user.getEmail())) {
            redirectAttributes.addFlashAttribute("registroError", "El email '" + user.getEmail() + "' ya está registrado.");
            System.out.println("Error: Email ya registrado."); // Log
            // Limpiar la contraseña del objeto user antes de redirigir si vuelves al formulario
             user.setPassword(null); // Evita mostrar la contraseña en el formulario si hay otro error
             // Puedes añadir el objeto user de vuelta al model si quieres repoblar otros campos,
             // pero addFlashAttribute ya guarda los campos automáticamente si hay errores de validación con @Valid
             // model.addAttribute("usuario", user); // Si no usas @Valid con flash attributes

            return "redirect:/auth/registrar"; // Redirigir de nuevo al formulario de registro
        }

        // Si no hay errores y el email no existe, guardar el usuario
        // Opcional: Encriptar la contraseña antes de guardar en una app real
        userService.saveUser(user);
        System.out.println("Usuario guardado exitosamente: " + user.getEmail()); // Log

        // Redirigir a una página de éxito o a la página de login
        redirectAttributes.addFlashAttribute("registroExito", "¡Registro exitoso! Ya puedes iniciar sesión.");
        return "redirect:/auth/registrar"; // Redirigir a la página de ingreso
    }

    // --- MÉTODO COPIADO DESDE controller (@GetMapping("/Ingresar.html")) ---
     // Lo renombraremos y/o ajustaremos el mapeo según el @RequestMapping de la clase
    @GetMapping("/ingresar") // Mapea la petición GET a /auth/ingresar
    public String mostrarFormularioLogin(Model model) {
         // Añade un nuevo objeto User vacío al modelo con el nombre "usuario"
         model.addAttribute("usuario", new User());
         System.out.println("Serviendo formulario de ingreso."); // Log
         return "Ingresar"; // Retorna el nombre de la plantilla HTML (Ingresar.html)
    }

    // --- Opcional: Método para procesar el login (lo añadiremos después) ---
    @PostMapping("/ingresar") // Mapea la petición POST a /auth/ingresar
    public String procesarLogin(@ModelAttribute("usuario") User user,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        System.out.println("Intentando login para email: " + user.getEmail()); // Log

         // Validar campos básicos si es necesario
         if (result.hasErrors()) {
             System.out.println("Errores de vinculación en login: " + result.getAllErrors());
             return "Ingresar"; // Vuelve a mostrar el formulario con errores
         }

        // Buscar y autenticar al usuario usando el servicio (usando el método findUserByEmailAndPassword de UserService)
        User authenticatedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());

        if (authenticatedUser == null) {
            // Si las credenciales son inválidas o el usuario no existe
            redirectAttributes.addFlashAttribute("loginError", "Email o contraseña incorrectos.");
             System.out.println("Error: Credenciales inválidas para email: " + user.getEmail()); // Log
            // Limpiar la contraseña del objeto user antes de redirigir
             user.setPassword(null);
             // Puedes añadir el objeto user de vuelta al model para mantener el email
             // model.addAttribute("usuario", user); // Si no usas @Valid con flash attributes

            return "redirect:/auth/ingresar"; // Redirigir de nuevo a la página de ingreso
        }

        // Si la autenticación es exitosa, guardar al usuario en la sesión
        session.setAttribute("loggedInUser", authenticatedUser); // Guarda el objeto User en la sesión
        System.out.println("Login exitoso para usuario: " + authenticatedUser.getUsername() + " (" + authenticatedUser.getEmail() + ")"); // Log


        // Redirigir a una página de inicio o a la página a la que intentaban acceder
        return "redirect:/admin.html"; // Redirigir a la página principal
    }

    // Opcional: Método para cerrar sesión
     @GetMapping("/logout")
     public String logout(HttpSession session) {
         session.invalidate(); // Invalida la sesión actual
         System.out.println("Sesión cerrada."); // Log
         return "redirect:/auth/ingresar?logout"; // Redirigir a la página de ingreso, quizás con un indicador de logout
     }

     


}