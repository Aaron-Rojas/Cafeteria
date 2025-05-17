package com.cafeteria.demo.model;
import com.cafeteria.demo.model.Administrador;

public class Administrador {
    

    // Atributos (puedes expandir esto según lo que administre)
    private String nombre;
    private String correo;
    private String contraseña;  // si usarás login

    // Constructor
    public Administrador(String nombre, String correo, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Métodos getter y setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    // Métodos de acción (puedes conectarlos a una base de datos)
    public void añadirReserva(Reserva reserva) {
        // Lógica para añadir reserva a una lista o BD
        System.out.println("Reserva añadida: " + reserva);
    }

    public void editarReserva(int id, Reserva nuevaReserva) {
        // Lógica para buscar reserva por ID y actualizarla
        System.out.println("Reserva " + id + " editada.");
    }

    public void eliminarReserva(int id) {
        // Lógica para eliminar una reserva
        System.out.println("Reserva " + id + " eliminada.");
    }

    @Override
    public String toString() {
        return "Administrador: " + nombre + ", Correo: " + correo;
    }
}
