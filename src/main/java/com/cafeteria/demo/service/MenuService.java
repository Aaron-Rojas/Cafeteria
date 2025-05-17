package com.cafeteria.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Importar Optional
import java.util.stream.Collectors; // Importar Collectors si usas streams



import org.springframework.stereotype.Service;

import com.cafeteria.demo.model.MenuItem;
import jakarta.annotation.PostConstruct;


@Service
public class MenuService {
    
    // Lista para almacenar los artículos del menú.
    // Usamos 'final' porque la referencia a la lista no cambiará,
    private final List<MenuItem> listaMenu = new ArrayList<>();
      private Long nextId = 1L;

    // Bloque de inicialización: Este código se ejecuta una vez cuando Spring crea una instancia de este servicio.
    public MenuService() {
        // Puedes llenar la lista aquí directamente en el constructor, o en un bloque initializer {}
        // Vamos a usar el constructor para mayor claridad:

        listaMenu.add(new MenuItem(1L,"Café frio", "Café Espresso de cuerpo completo con agua y hielo. Sin endulzantes.", "https://imgs.search.brave.com/U4J_9iPkt18HSKBxDeJT1x09RqVqmBXrpujwijz9_vo/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly9zMS5h/YmNzdGF0aWNzLmNv/bS9tZWRpYS9iaWVu/ZXN0YXIvMjAyMi8w/Ny8yMC9jYWZlLWZy/aW8tazRnSC0tNTEw/eDI4N0BhYmMuanBn", "Bebida", 3.50)); // Precio de ejemplo
        listaMenu.add(new MenuItem(2L,"Té Chai Latte Alto", "Té negro con canela, clavo y otras especias calientes se combina con leche vaporizada y espuma, logrando un equilibrio perfecto.",   "https://imgs.search.brave.com/Au94ih_iirJWoUTT2nS5oBrsT8NW11xJBn53lRZpZg8/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pMi53/cC5jb20vYmFraW5n/bWlzY2hpZWYuY29t/L3dwLWNvbnRlbnQv/dXBsb2Fkcy8yMDIw/LzA1L2NoYWktdGVh/LWxhdHRlLWltYWdl/LmpwZw", "Bebida", 4.00)); // Precio de ejemplo
        listaMenu.add(new MenuItem(3L,"Café Frappuccino", "Clásico frappuccino con base de café, mezclado con leche y hielo. Sin endulzantes.", "https://imgs.search.brave.com/cm7hagYptiKcAqXQDZ7KHA4YokA8TvB9EFkDQarghyU/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly93d3cu/c3dlZXRmaXhiYWtl/ci5jb20vd3AtY29u/dGVudC91cGxvYWRz/LzIwMjMvMDUvVmFu/aWxsYS1Db2ZmZWUt/RnJhcHB1Y2Npbm8t/UmVjaXBlLTgxOXgx/MDI0LmpwZw", "Bebida", 4.75)); // Precio de ejemplo

        // --- Postres ---
        listaMenu.add(new MenuItem(4L,"Trufas de Chocolate", "Elaborado de una mezcla ee chocolate fundido, mantequilla, azúcar glas, yemas de huevos y crema de leche", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgch-juA8WMYGKsGQYdZ3gqTqeoKCmKTP7MA&s", "Postre", 2.00)); // Precio de ejemplo
        listaMenu.add(new MenuItem(5L,"Relámpago", "Es un bollo fino y alargado, hornado hasta que sea crujiente y hueco.\r\n" + //
                        "              Rellenado con crema pastelera de vainilla o chocolate y cubierto de una capa de glaseado blanco o negro", "https://imgs.search.brave.com/yulxz0LcxdqVSNV23b5uldy8PGp5vZT6bSaz4cAK_Rk/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9lcy5j/cmF2aW5nc2pvdXJu/YWwuY29tL3dwLWNv/bnRlbnQvdXBsb2Fk/cy8yMDIzLzA0L2Vj/bGFpcnMtNC5qcGc", "Postre", 2.50)); // Precio de ejemplo
        listaMenu.add(new MenuItem(6L,"Keke de zanahoria", "Keke elaborado con harina, azucar, manteca, ralladura de zanahoria, trozos de pecanas y con frostry zona superior", "https://imgs.search.brave.com/frk4Mo8rFmpkY07GZGl1PFvzHIzTS57Tbm07UEATCEo/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly93d3cu/aW5mb2JhZS5jb20v/cmVzaXplci92Mi9J/Uk9JUk5SRzdGR0pQ/SzNFRUxGSkpWV1JY/VS5qcGc_YXV0aD0z/YWM2OWI4NmY3YzU1/YzliODU4MDEzYzll/ZGE3MTA5OTJlZDUw/Y2FlMzg1MzA4YzY5/MTFhNzcxZTY5YTZj/ZTE0JnNtYXJ0PXRy/dWUmd2lkdGg9MzUw/JmhlaWdodD0xOTcm/cXVhbGl0eT04NQ", "Postre", 3.00)); // Precio de ejemplo

        // --- Combos ---
         listaMenu.add(new MenuItem(7L,"Desayuno Dúo", "Disfruta de un Americano, Latte, Vainilla Latte o Chocolate Caliente y combínalo con un Croissant de mantequilla, Sandwich Panino", "https://imgs.search.brave.com/3zWIsTyRv2nNUD8cogmCx0p07LUqKqmmGGyu4C8ZUAE/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9rZW5v/bmZvb2QuY29tL21l/ZGlhLzIwMjQvMDEv/Y2FwcHVjY2luby1q/dWxpZS1hYWdhYXJk/LTIzNTEyNzUtNzY4/eDEwMjQuanBn", "Combo", 6.00)); // Precio de ejemplo
         listaMenu.add(new MenuItem(8L,"Almuerzo Tarde de empanadas", "Llevate una Vainilla Créme Frapuccino y combínalo con 3 empanadas de pollo, carne y mixta", "https://imgs.search.brave.com/o3bZ5sv20oHRiWBXv_8Qd1wFYfk36T5byvFZgVIIG6Q/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly90Yi1z/dGF0aWMudWJlci5j/b20vcHJvZC9pbWFn/ZS1wcm9jL3Byb2Nl/c3NlZF9pbWFnZXMv/NDQ2ZTY4MmM4ZDdm/NzZlN2M3M2I0NTA1/NDZkOThhNzYvZjZk/ZWIwYWZjMjRmZWU2/ZjRiZDMxYTM1ZTZi/Y2JkNDcuanBlZw", "Combo", 7.50)); // Precio de ejemplo
         listaMenu.add(new MenuItem(9L,"Cena Noche tranquila", "Llevate una vaso de leche caliente y junto a ellas unas galletas", "https://imgs.search.brave.com/tieh-Ep0ALMcXWEvZeX6fP3zj19ZVuWtRL713szxpIQ/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly9pLmJs/b2dzLmVzL2U3Yjdk/YS9nYWxsZXRhcy1k/ZS1hdmVuYS80NTBf/MTAwMC5qcGc", "Combo", 5.00)); // Precio de ejemplo
    }

    // Método para obtener la lista completa de todos los artículos del menú
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(listaMenu); // Retorna una copia para evitar modificaciones externas
    }

    // --- Métodos adicionales para filtrar 

    // Método para obtener artículos por categoría
    public List<MenuItem> getMenuItemsByCategory(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return getAllMenuItems();
        }
        
        List<MenuItem> filteredList = new ArrayList<>();
        for (MenuItem item : listaMenu) {
            // Compara la categoría ignorando mayúsculas/minúsculas para flexibilidad
            if (item.getCategoria().equalsIgnoreCase(categoria.trim())) {
                filteredList.add(item);
            }
        }
        return filteredList;

    }

     // Método para buscar artículos por nombre o descripción
    public List<MenuItem> searchMenuItems(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllMenuItems(); // Si el término de búsqueda está vacío, devuelve todos
        }
        String lowerCaseSearchTerm = searchTerm.trim().toLowerCase();
        List<MenuItem> filteredList = new ArrayList<>();
        for (MenuItem item : listaMenu) {
            // Busca el término en el nombre O en la descripción (ignorando mayúsculas/minúsculas)
            if (item.getNombre().toLowerCase().contains(lowerCaseSearchTerm) ||
                item.getDescripcion().toLowerCase().contains(lowerCaseSearchTerm)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

        // Método para encontrar un MenuItem por su ID
        public Optional<MenuItem> getMenuItemById(Long id) {
        // Usar Streams de Java 8+ para buscar en la lista
        return listaMenu.stream()
        .filter(item -> item.getId() != null && item.getId().equals(id)) // Buscar por ID
        .findFirst(); // Devolver el primer resultado (debería ser único)
        }
}


