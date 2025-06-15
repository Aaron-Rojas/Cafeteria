package com.cafeteria.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa")
    private int numeroMesa;

    @Column(name = "capacidad")
    private int capacidad;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoMesa estado;

    private String descripcion;

    public enum EstadoMesa {
        Disponible,
        Ocupada,
        Mantenimiento
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public EstadoMesa getEstado() { return estado; }
    public void setEstado(EstadoMesa estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Mesa #" + numeroMesa + " (" + capacidad + " personas, Estado: " + estado + ")";
    }
}