package com.cafeteria.demo.model;

public class MenuItem {
    
    private Long id;
    private String nombre; // Ej: "Café frio", "Trufas de Chocolate", "Desayuno"
    private String descripcion; // Ej: "Café Espresso de cuerpo completo...", "Elaborado de una mezcla ee chocolate fundido..."
    private String imagenUrl; // La URL o ruta a la imagen del artículo
    private String categoria; // Ej: "Bebida", "Postre", "Combo" (Esto es útil para organizar)
    private double precio; // El precio del artículo
   
    
    
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getImagenUrl() {
        return imagenUrl;
    }
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public Long getId() {
        return id;
    }

    
    public MenuItem() {

    }   
    public MenuItem(long id, String nombre, String descripcion, String imagenUrl, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.precio = precio;
    }
      
    // --- Método toString (Opcional pero útil para depuración) ---
    // Facilita imprimir el objeto en la consola para ver sus valores
    @Override
    public String toString() {
        return "MenuItem{" +
               "nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", imagenUrl='" + imagenUrl + '\'' +
               ", categoria='" + categoria + '\'' +
               ", precio=" + precio +
               '}';
    }


}
