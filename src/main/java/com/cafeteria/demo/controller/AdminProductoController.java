package com.cafeteria.demo.controller;


import com.cafeteria.demo.model.Categoria;
import com.cafeteria.demo.model.Producto;
import com.cafeteria.demo.service.ProductoAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {
   
@Autowired
private ProductoAdminService productoAdminService;


 @GetMapping // Maneja la petición GET a /admin/productos
    public String listarProductosAdmin(Model model) {
        // Llamamos al método del servicio que usa el procedimiento almacenado
        List<Object[]> productosConCategoria = productoAdminService.obtenerTodosLosProductosConCategoriaParaAdmin();

        // Puedes inspeccionar esto en la consola para ver la estructura
        System.out.println("\n--- DEBUG: Datos obtenidos del SP para Admin ---");
        productosConCategoria.forEach(row -> {
            System.out.println("  ID: " + row[0] +
                               ", Nombre: " + row[1] +
                               ", Categoria: " + row[5] + // <-- Aquí está el nombre de la categoría
                               ", URL Imagen: " + row[4]); // <-- Aquí está la URL de la imagen
        });
        System.out.println("--- FIN DEBUG SP ---");


        model.addAttribute("productosData", productosConCategoria); // Pasamos los datos al modelo
        return "productos_lista"; // Retorna el nombre de la plantilla HTML
    }

     // 2. CREATE (Mostrar formulario para nuevo producto)
    @GetMapping("/nuevo") 
    public String mostrarFormularioNuevoProducto(Model model) {
        System.out.println("\n--- DEBUG AdminProductoController: Entrando a mostrarFormularioNuevoProducto ---");
        Producto producto = new Producto(); // Crea un nuevo objeto Producto vacío
        List<Categoria> categorias = productoAdminService.findAllCategorias(); // Obtiene todas las categorías

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        System.out.println("--- DEBUG AdminProductoController: Saliendo de mostrarFormularioNuevoProducto, retornando 'producto_form' ---");
        return "producto_form"; // Vista Thymeleaf para el formulario
    }

    // 3. CREATE/UPDATE (Guardar o Actualizar producto desde el formulario)
    @PostMapping("/guardar") // /admin/productos/guardar
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        System.out.println("\n--- DEBUG AdminProductoController: Entrando a guardarProducto ---");
        System.out.println("--- DEBUG AdminProductoController: Datos recibidos del formulario: " + producto.toString() + " ---");
        
        if (producto.getId() == null) {
            // Si el ID es null, es un nuevo producto
            System.out.println("--- DEBUG AdminProductoController: Creando nuevo producto ---");
            
            productoAdminService.guardarProductoConSP(producto); 

            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado exitosamente!");
        } else {
            // Si el ID existe, es una actualización
            System.out.println("--- DEBUG AdminProductoController: Actualizando producto existente con ID: " + producto.getId() + " ---");
            productoAdminService.actualizarProductoConSP(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado exitosamente!");
        }
        System.out.println("--- DEBUG AdminProductoController: Saliendo de guardarProducto, redirigiendo a '/admin/productos' ---");
        return "redirect:/admin/productos"; // Redirige a la lista de productos
    }

    // 4. UPDATE (Mostrar formulario para editar producto)
    @GetMapping("/editar/{id}") // /admin/productos/editar/{id}
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
        System.out.println("\n--- DEBUG AdminProductoController: Entrando a mostrarFormularioEditarProducto con ID: " + id + " ---");
        Optional<Producto> productoOptional = productoAdminService.findById(id);
        
        if (!productoOptional.isPresent()) {
            System.out.println("--- ERROR AdminProductoController: Producto con ID " + id + " no encontrado para editar. ---");
            // Puedes redirigir a una página de error o a la lista con un mensaje
            throw new IllegalArgumentException("ID de producto inválido:" + id);
        }
        
        Producto producto = productoOptional.get();
        List<Categoria> categorias = productoAdminService.findAllCategorias();

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        System.out.println("--- DEBUG AdminProductoController: Saliendo de mostrarFormularioEditarProducto, retornando 'producto_form' ---");
        return "producto_form"; // Reutiliza el mismo formulario para edición
    }

    // 5. DELETE (Eliminar producto)
    @GetMapping("/eliminar/{id}") // /admin/productos/eliminar/{id}
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("\n--- DEBUG AdminProductoController: Entrando a eliminarProducto con ID: " + id + " ---");
        productoAdminService.eliminarProductoConSP(id);
        redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente!");
        System.out.println("--- DEBUG AdminProductoController: Saliendo de eliminarProducto, redirigiendo a '/admin/productos' ---");
        return "redirect:/admin/productos"; // Redirige a la lista de productos
    }


}
