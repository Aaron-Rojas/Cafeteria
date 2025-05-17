package com.cafeteria.demo.controller;

import com.cafeteria.demo.model.Cart;
import com.cafeteria.demo.model.CartItem;
import com.cafeteria.demo.model.MenuItem;
import com.cafeteria.demo.service.MenuService;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.Optional; 

@Controller 
@RequestMapping("/cart") // Mapea todas las peticiones que empiezan con /cart a este controlador
public class CartController {

    @Autowired 
    private MenuService menuService;

    // Endpoint para añadir un ítem al carrito
    @PostMapping("/add") // Mapea las peticiones POST a /cart/add a este método
    @ResponseBody // Indica que el valor de retorno debe ser el cuerpo de la respuesta HTTP (no un nombre de vista)
    public ResponseEntity<String> addItemToCart(
            @RequestParam("productId") Long productId, // Recibe el ID del producto del JavaScript
            @RequestParam("size") String size,         // Recibe el tamaño seleccionado
            @RequestParam("quantity") int quantity,     // Recibe la cantidad seleccionada
            HttpSession session) { // Accede a la sesión HTTP del usuario


             
        // --- LOGS TEMPORALES PARA VERIFICAR EN LA TERMINAL ---
        System.out.println("Recibida solicitud para añadir al carrito:");
        System.out.println("  Product ID: " + productId);
        System.out.println("  Size: " + size);
        System.out.println("  Quantity: " + quantity);
        // --- FIN LOGS TEMPORALES ---
        
        
        // 1. Validar la cantidad
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("La cantidad debe ser al menos 1.");
        }

        // 2. Buscar el MenuItem por su ID
        Optional<MenuItem> productOptional = menuService.getMenuItemById(productId);

        if (!productOptional.isPresent()) {
            // Si el producto no se encuentra, devuelve un error 404
            return ResponseEntity.notFound().build();
        }

        MenuItem product = productOptional.get();

        // 3. Obtener o crear el carrito de la sesión
        // Busca en la sesión un atributo llamado "shoppingCart"
        Cart cart = (Cart) session.getAttribute("shoppingCart");

        if (cart == null) {
            // Si no existe un carrito en la sesión, crea uno nuevo
            cart = new Cart();
            // Y guárdalo en la sesión
            session.setAttribute("shoppingCart", cart);
            System.out.println("Nuevo carrito creado en la sesión.");

        }

        // 4. Crear el CartItem y añadirlo al carrito
        CartItem newItem = new CartItem(product, size, quantity);
        cart.addItem(newItem); // La lógica de addItem en Cart ya maneja si es un ítem nuevo o se actualiza la cantidad


         // --- LOGUEAR EL ESTADO ACTUAL DEL CARRITO ---
        System.out.println("Ítem añadido al carrito.");
        System.out.println("Estado actual del carrito (número de ítems distintos): " + cart.getItems().size());
        System.out.println("Estado actual del carrito (total): S/ " + cart.getTotal());
        // Opcional: loguear todos los ítems en el carrito (usando el toString() de CartItem)
        // System.out.println("Contenido del carrito:");
        // cart.getItems().forEach(item -> System.out.println("  - " + item.toString()));
        // --- FIN LOGUEO ESTADO DEL CARRITO ---

        // 5. El total del carrito se recalcula automáticamente en cart.addItem()

        // 6. Devolver una respuesta de éxito al cliente
        return ResponseEntity.ok("Producto añadido al carrito correctamente.");
        // También podrías devolver el número actual de ítems en el carrito, el total, etc.
    }

      // Método para ver el contenido del carrito
    @GetMapping // Mapea las peticiones GET a /cart (la raíz del RequestMapping de la clase)
    public String viewCart(HttpSession session, Model model) {
        // Obtener el carrito de la sesión
        Cart cart = (Cart) session.getAttribute("shoppingCart");

        // Si no hay carrito en la sesión, crea un carrito vacío para mostrar
        if (cart == null) {
            cart = new Cart();
            // Opcional: podrías no guardar el carrito vacío en la sesión si prefieres que solo exista cuando tiene ítems
            // session.setAttribute("shoppingCart", cart);
        }

        // Añadir el carrito al modelo para que la plantilla Thymeleaf acceda a él
        model.addAttribute("cart", cart);

        // Retornar el nombre de la plantilla HTML (ej: cart-view.html)
        return "carrito"; // <-- Asegúrate de que este sea el nombre de tu archivo HTML en templates sin la extensión
    }

    @PostMapping("/remove") // Mapea peticiones POST a /cart/remove
    @ResponseBody // Retorna cuerpo de respuesta HTTP
    public ResponseEntity<String> removeItemFromCart(
            @RequestParam("productId") Long productId, // Recibe ID del producto
            @RequestParam("size") String size,         // Recibe tamaño
            HttpSession session) { // Accede a la sesión

        // --- LOGS TEMPORALES PARA VERIFICAR ---
        System.out.println("Recibida solicitud para eliminar del carrito:");
        System.out.println("  Product ID: " + productId);
        System.out.println("  Size: " + size);
        // --- FIN LOGS TEMPORALES ---

        // 1. Obtener el carrito de la sesión
        Cart cart = (Cart) session.getAttribute("shoppingCart");

        // Si no hay carrito o está vacío, no hay nada que eliminar
        if (cart == null || cart.getItems().isEmpty()) {
            // También logueamos
            System.out.println("Error: No hay carrito en sesión o está vacío.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado o vacío.");
        }

        // 2. Buscar y eliminar el ítem
        boolean itemRemoved = false;
        // Usamos un Iterator para poder eliminar elementos de la lista mientras la recorremos
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            // Comparamos por ID del producto y tamaño seleccionado
            if (item.getProduct().getId().equals(productId) && item.getSelectedSize().equals(size)) {
                iterator.remove(); // Elimina el ítem actual de la lista
                itemRemoved = true;
                // Logueamos la eliminación
                System.out.println("Ítem con ID " + productId + " y tamaño " + size + " eliminado.");
                break; // Salimos del bucle una vez eliminado el ítem
            }
        }

        // 3. Recalcular el total del carrito si se eliminó un ítem
        if (itemRemoved) {
            cart.recalculateTotal();
            // Logueamos el nuevo estado
            System.out.println("Total del carrito recalculado.");
            System.out.println("Estado actual del carrito (número de ítems distintos): " + cart.getItems().size());
            System.out.println("Estado actual del carrito (total): S/ " + cart.getTotal());

             // 4. Devolver respuesta de éxito
            return ResponseEntity.ok("Ítem eliminado correctamente.");
        } else {
            // Si el ítem no se encontró en el carrito (ya fue eliminado o nunca estuvo)
            System.out.println("Error: Ítem con ID " + productId + " y tamaño " + size + " no encontrado en el carrito.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ítem no encontrado en el carrito.");
        }
    }

    // ... (método GetMapping / para ver el carrito y otros métodos) ...
}

    // Opcional: Añadir un método para ver el carrito (lo haremos después)
    /*
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("shoppingCart");
        if (cart == null) {
            cart = new Cart(); // Si no hay carrito en sesión, muestra uno vacío
            session.setAttribute("shoppingCart", cart); // Podrías no guardar el vacío si prefieres
        }
        model.addAttribute("cart", cart);
        return "cart-view"; // Nombre de la plantilla Thymeleaf para mostrar el carrito (la crearemos después)
    }
    */

