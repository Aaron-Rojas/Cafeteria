package com.cafeteria.demo.model;

import com.cafeteria.demo.model.MenuItem;
import java.util.Optional;
import java.util.stream.Collectors;


public class CartItem {

    private MenuItem product; // El producto del menú
    private String selectedSize; // Tamaño seleccionado (Pequeño, Mediano, Grande)
    private int quantity; // Cantidad seleccionada
    private Double subtotal; // Precio total de este ítem (precio unitario * cantidad)

    public CartItem(){

    }

    public CartItem(MenuItem product, String selectedSize, int quantity) {
        this.product = product;
        this.selectedSize = selectedSize;
        this.quantity = quantity;
        this.subtotal = product.getPrecio()* quantity;
    }

    public MenuItem getProduct() {
        return product;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }


    public void setSelectedSize(String selectedSize) {
         this.selectedSize = selectedSize;
         // Si el tamaño impacta el precio, recalcular subtotal aquí.
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = this.product.getPrecio() * quantity; // Recalcular subtotal
    }

    // Opcional: Método para recalcular subtotal si algo cambia (ej. el precio del producto)
    public void recalculateSubtotal() {
        this.subtotal = this.product.getPrecio() * this.quantity;
    }


    // Opcional: toString() para depuración
    @Override
    public String toString() {
        return "CartItem{" +
               "product=" + product.getNombre() +
               ", selectedSize='" + selectedSize + '\'' +
               ", quantity=" + quantity +
               ", subtotal=" + subtotal +
               '}';
    }

}
