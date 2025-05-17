package com.cafeteria.demo.controller; // Asegúrate de que el paquete sea correcto

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.cafeteria.demo.model.MenuItem;
import com.cafeteria.demo.service.MenuService;
import com.cafeteria.demo.model.User;
@Controller // Con esta anotación, Spring Boot reconoce esta clase como un Controlador
public class controller {

    @Autowired // Le dice a Spring que inyecte una instancia de MenuService aquí
    private MenuService menuService; // Los otros métodos para páginas estáticas pueden quedarse igual si los
                                     // necesitas

    // Este método manejará las peticiones GET a la URL raíz ("/")
    @GetMapping("/")
    public String showHomePage() {
        // Spring Boot, usando Thymeleaf, buscará un archivo llamado "inicio.html"
        // en la carpeta src/main/resources/templates/
        return "inicio";
    }

    // Maneja la URL "/Menu.html" y muestra Menu.html
    @GetMapping("/Carta.html")
    public String showMenuPage(Model model, @RequestParam(value = "searchTerm", required = false) String searchTerm) { // <--
                                                                                                                       // Añadimos
                                                                                                                       // este
                                                                                                                       // parámetro
        List<MenuItem> menuItems;
        // Si se proporcionó un término de búsqueda, usar el método de búsqueda del
        // servicio
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            menuItems = menuService.searchMenuItems(searchTerm); // <-- Usamos tu método existente
            // Opcional: añadir el término de búsqueda al modelo para mostrarlo en la página
            model.addAttribute("searchTerm", searchTerm);
        } else {
            // Si no hay término de búsqueda, obtener todos los ítems
            menuItems = menuService.getAllMenuItems(); // <-- Usamos tu método existente (o searchMenuItems(null))
        }

        // Añadir la lista de ítems (filtrada o completa) al Model
        model.addAttribute("menuItems", menuItems);

        // Retornar el nombre de la plantilla
        return "Carta"; // Busca Carta.html en templates
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
