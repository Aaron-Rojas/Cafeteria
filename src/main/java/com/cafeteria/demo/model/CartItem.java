package com.cafeteria.demo.model;

import java.math.BigDecimal;

// importar la clase Producto!
import com.cafeteria.demo.model.Producto; // o el paquete donde esté tu Producto.java

public class CartItem {
    // El ítem de menú ahora será de tipo Producto
    private Producto product;
    private String selectedSize;
    private int quantity;

    // Constructor que acepta un objeto Producto
    public CartItem(Producto product, String selectedSize, int quantity) {
        this.product = product;
        this.selectedSize = selectedSize;
        this.quantity = quantity;
    }

    // Constructor vacío
    public CartItem() {
    }

    // Getters
    public Producto getProduct() {
        return product;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters 
    public void setProduct(Producto product) {
        this.product = product;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    // Método corregido para calcular el subtotal
    public double getSubtotal() {
        // Multiplicar BigDecimal por un int
        // Primero convertimos la cantidad a BigDecimal para la operación
        BigDecimal totalBigDecimal = product.getPrecio().multiply(BigDecimal.valueOf(quantity));
        
        // Luego, convertimos el resultado a double, ya que getSubtotal() devuelve double
        return totalBigDecimal.doubleValue();
    }


    @Override
    public String toString() {
        return "CartItem{" +
               "product=" + product.getNombre() + // Solo mostramos el nombre del producto para evitar bucles infinitos con toString()
               ", selectedSize='" + selectedSize + '\'' +
               ", quantity=" + quantity +
               ", subtotal=" + getSubtotal() +
               '}';
    }

    // Opcional: Si quieres que los ítems con el mismo producto y tamaño se "sumen" en el carrito
    // Puedes implementar equals y hashCode para la comparación
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        // Compara por ID del producto y tamaño seleccionado
        return product.getId().equals(cartItem.product.getId()) &&
               selectedSize.equals(cartItem.selectedSize);
    }

    @Override
    public int hashCode() {
        // Genera un hash basado en el ID del producto y el tamaño
        return java.util.Objects.hash(product.getId(), selectedSize);
    }
}