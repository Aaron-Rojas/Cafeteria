package com.cafeteria.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Para usar Optional si buscas ítems

public class Cart implements Serializable { // Implementar Serializable es bueno para la sesión

    private List<CartItem> items;
    private double total;

    public Cart() {
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    // Método para añadir un ítem al carrito o actualizar su cantidad
    public void addItem(CartItem newItem) {
        // Busca si el ítem (mismo producto y tamaño) ya existe en el carrito
        Optional<CartItem> existingItem = items.stream()
            .filter(item -> item.getProduct().getId().equals(newItem.getProduct().getId()) &&
                             item.getSelectedSize().equals(newItem.getSelectedSize()))
            .findFirst();

        if (existingItem.isPresent()) {
            // Si el ítem ya existe, actualiza su cantidad
            existingItem.get().setQuantity(existingItem.get().getQuantity() + newItem.getQuantity());
        } else {
            // Si el ítem no existe, añádelo a la lista
            items.add(newItem);
        }
        recalculateTotal(); // Recalcula el total después de añadir
    }

    // Método para recalcular el total del carrito
    public void recalculateTotal() {
        this.total = items.stream()
                .mapToDouble(CartItem::getSubtotal) // Usa el método getSubtotal de CartItem
                .sum();
    }

    // Puedes añadir métodos para eliminar ítems, vaciar el carrito, etc.
    // (Tu CartController ya tiene un método para eliminar)
}