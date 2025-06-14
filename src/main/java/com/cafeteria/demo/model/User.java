package com.cafeteria.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id") // <--- ¡CAMBIO CRUCIAL AQUÍ! Mapea usuarioid a la columna 'id'
    private Long usuarioid;

    @Column(name="email")
    private String email;

    @Column(name="nombre_usuario")
    private String username;

    @Column(name="contrasena")
    private String password;

    @Column(name="rol_id")
    private Long rolusuario;

    public User() {
    }

    public User(String email, String username, String password) { // Usar 'username' para el campo 'username'
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // --- Getters y Setters ---

    public Long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRolusuario() {
        return rolusuario;
    }

    public void setRolusuario(Long rolusuario) {
        this.rolusuario = rolusuario;
    }

    @Override
    public String toString() {
        return "User{" +
                "usuarioid=" + usuarioid +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", rolusuario=" + rolusuario +
                // Considera no imprimir la contraseña por seguridad en entornos de producción
                '}';
    }
}