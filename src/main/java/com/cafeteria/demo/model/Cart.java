package com.cafeteria.demo.model;

import com.cafeteria.demo.model.CartItem;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Cart {
    
    private List<CartItem> items; // Lista de ítems en el carrito
    private Double total; // Precio total de todos los ítems

    // Constructor
    public Cart() {
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    // Método para añadir un ítem al carrito
    public void addItem(CartItem item) {
        // Implementar lógica: si el ítem ya existe (mismo producto y tamaño),
        // simplemente incrementa la cantidad. Si no, añade un nuevo ítem.
        boolean itemExists = false;
        for (CartItem existingItem : items) {
            // Lógica simple de comparación: mismo producto (por ID) y mismo tamaño
            if (existingItem.getProduct().getId().equals(item.getProduct().getId()) &&
                existingItem.getSelectedSize().equals(item.getSelectedSize())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                itemExists = true;
                break; // Salir del bucle una vez que encontramos y actualizamos el ítem
            }
        }

        if (!itemExists) {
            this.items.add(item); // Si el ítem no existía, añadirlo como uno nuevo
        }

        recalculateTotal(); // Recalcular el total después de añadir/actualizar
    }

    // Método para recalcular el total del carrito sumando los subtotales de los ítems
    public void recalculateTotal() {
        this.total = 0.0;
        for (CartItem item : items) {
            this.total += item.getSubtotal();
        }
    }

    // Método para obtener los ítems del carrito
    public List<CartItem> getItems() {
        return items;
    }

    // Método para obtener el total del carrito
    public Double getTotal() {
        return total;
    }

    // Puedes añadir métodos adicionales como removeItem(Long productId), updateItemQuantity(Long productId, int newQuantity), clearCart(), etc.

    // Opcional: toString() para depuración
    @Override
    public String toString() {
        return "Cart{" +
               "items=" + items.size() +
               ", total=" + total +
               '}';
    }
}
