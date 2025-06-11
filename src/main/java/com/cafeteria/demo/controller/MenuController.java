
package com.cafeteria.demo.controller; 

import com.cafeteria.demo.model.Producto; 
import com.cafeteria.demo.service.MenuService; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/") 
public class MenuController {

    private final MenuService menuService; // Inyectamos nuestro servicio

    // Inyección de dependencias a través del constructor
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/Carta") 
    public String mostrarMenu(Model model) {
        // Obtener todos los productos del servicio, que a su vez los trae de la BD
        List<Producto> productos = menuService.obtenerTodosLosProductos();

        // Añadir la lista de productos al modelo para que la vista HTML pueda acceder a ella
        model.addAttribute("productos", productos);

        return "Carta"; 
    }

    // Puedes añadir otros métodos GetMapping o PostMapping aquí para otras funcionalidades
    // Por ejemplo, para mostrar detalles de un producto, etc.
}