package com.cafeteria.demo.model; 


import jakarta.persistence.*;
import java.math.BigDecimal; // Para manejar precios con precisión

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "productos") // Mapea a la tabla 'productos' en la BD
public class Producto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT") // Mapea a TEXT en SQL
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2) // DECIMAL(10,2)
    private BigDecimal precio; // Usa BigDecimal para precisión con dinero

    @Column(name = "url_imagen", length = 255)
    private String urlImagen; 

    @ManyToOne // Muchos productos pueden tener una categoría
    @JoinColumn(name = "categoria_id", nullable = true) // Mapea la clave foránea categoria_id
    private Categoria categoria; // Un objeto Categoria para la relación

    // Constructor vacío (necesario para JPA)
    public Producto() {
    }

    // Constructor con campos (útil para crear objetos, puedes ajustarlo si lo necesitas)
    public Producto(String nombre, String descripcion, BigDecimal precio, String urlImagen, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", precio=" + precio +
               ", categoria=" + (categoria != null ? categoria.getNombre() : "N/A") +
               '}';
    }
    
}