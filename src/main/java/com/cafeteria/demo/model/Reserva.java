package com.cafeteria.demo.model;
import com.cafeteria.demo.model.Reserva;
import java.time.LocalDate; 
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter;  
public class Reserva {
    
    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDate fecha;
    private LocalTime horareserva;
    
    DateTimeFormatter formatt= DateTimeFormatter.ofPattern("HH:mm"); 
    DateTimeFormatter formattfech= DateTimeFormatter.ofPattern("yy/mm/dd"); 

    public Reserva(int id, String nombre, String apellido, String correo, String fecha, String hora, String telefono) {
        this.id = id;
        this.nombres = nombre;
        this.apellidos = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha= LocalDate.now(); 
        this.horareserva=LocalTime.parse(hora,formatt); 
    }

    public int getId() { return id; }
    public String getNombre() { return nombres; }
    public String getApellido() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombres = nombre; }
    public void setApellido(String apellido) { this.apellidos = apellido; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }


    @Override
    public String toString() {
        return id + ": " + nombres + " " + apellidos + ", " + correo + ", " + telefono;
    }
}
