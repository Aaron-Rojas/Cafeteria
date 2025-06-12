package com.cafeteria.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDate fecha;
    private LocalTime horareserva;

    // Constructores
    public Reserva() {} // Obligatorio para JPA

    public Reserva(int id, String nombre, String apellido, String correo, LocalDate fecha, LocalTime horareserva, String telefono) {
        this.id = id;
        this.nombres = nombre;
        this.apellidos = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha = fecha;
        this.horareserva = horareserva;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHorareserva() { return horareserva; }
    public void setHorareserva(LocalTime horareserva) { this.horareserva = horareserva; }

    @Override
    public String toString() {
        return id + ": " + nombres + " " + apellidos + ", " + correo + ", " + telefono;
    }
}
