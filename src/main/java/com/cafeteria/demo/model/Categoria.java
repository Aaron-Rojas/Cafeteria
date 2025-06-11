package com.cafeteria.demo.model; 

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 

@Entity // Indica que esta clase es una entidad JPA y se mapea a una tabla
@Table(name = "categorias") // Mapea a la tabla 'categorias' en la BD
public class Categoria {

    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID 
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100) // Mapea a la columna 'nombre', no nula, única, longitud 100
    private String nombre;

    @Column(name = "descripcion", length = 255) // Mapea a la columna 'descripcion'
    private String descripcion;

    // Constructor vacío (necesario para JPA)
    public Categoria() {
    }

    // Constructor con campos (útil para crear objetos)
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return "Categoria{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               '}';
    }
}