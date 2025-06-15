package com.cafeteria.demo.controller; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.cafeteria.demo.model.MenuItem;
import com.cafeteria.demo.model.Producto;
import com.cafeteria.demo.service.ProductoService;
import com.cafeteria.demo.model.User;

@Controller // Con esta anotación, Spring Boot reconoce esta clase como un Controlador
public class HomeController {

    @Autowired // Le dice a Spring que inyecte una instancia de MenuService aquí
     // Los otros métodos para páginas estáticas pueden quedarse igual los necesitas
    private final ProductoService menuService;

    // Inyección de dependencias a través del constructor
    public HomeController(ProductoService menuService) {
        this.menuService = menuService;
    }                                     

    // Este método manejará las peticiones GET a la URL raíz ("/")
    @GetMapping("/")
    public String showHomePage() {
        // Spring Boot, usando Thymeleaf, buscará un archivo llamado "inicio.html"
        // en la carpeta src/main/resources/templates/
        return "inicio";
    }


    // Maneja las URLs "/Carta.html" y "/menu" para mostrar el menú principal
    // Hemos añadido "/menu" para que sea una URL más limpia, pero /Carta.html también funcionará.
    @GetMapping({"/Carta.html", "/menu"}) 
    public String showMenuPage(Model model, @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        List<Producto> productos; 

        // Si se proporcionó un término de búsqueda, usar el método de búsqueda del servicio
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            productos = menuService.buscarProductosPorNombre(searchTerm); // <-- Usamos el nuevo método
            // Añadimos el término de búsqueda al modelo para que la caja de texto lo recuerde
            model.addAttribute("searchTerm", searchTerm); 
        } else {
            // Si no hay término de búsqueda, obtener todos los productos
            productos = menuService.obtenerTodosLosProductos(); // <-- Usamos el nuevo método
        }

        // Añadir la lista de productos (filtrada o completa) al Model
        // ¡Importante: el nombre del atributo es "productos", como lo espera tu Cartas.html!
        model.addAttribute("productos", productos); 

        return "Carta";
        }

    // Maneja la URL "/SobreNosotros.html" y muestra SobreNosotros.html
    @GetMapping("/SobreNosotros.html")
    public String showAboutUsPage() {
        return "SobreNosotros"; // Busca SobreNosotros.html en templates
    }

    @GetMapping("/Inicio.html")
    public String showLoginPag() {
        return "Inicio";
    }

    // Maneja la URL "/Eventos.html" y muestra Eventos.html
    @GetMapping("/Eventos.html")
    public String showEventsPage() {
        return "Eventos"; // Busca Eventos.html en templates
    }

    @GetMapping("/Registrar.html")
    public String showRegistrartPage() {
        return "Registrar"; // Busca Registrar.html en templates
    }

    @GetMapping("/Ingresar.html")
    public String showSesionPage(Model model) {
        model.addAttribute("usuario", new User());
        return "Ingresar"; // Busca Ingresar.html en templates
    }
    
    @GetMapping("/admin.html")
    public String showAdmin() {
    return "admin"; 
    }
}
