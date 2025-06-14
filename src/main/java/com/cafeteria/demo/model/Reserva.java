package com.cafeteria.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reservas")
public class Reserva {

    public enum EstadoReserva {
        Pendiente,
        Confirmada,
        Cancelada,
        Completada
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "mesa_id") // Clave foránea
      @JsonIgnoreProperties({"reservas"}) // Evita recursión
    private Mesa mesa;
   
    @Column(name = "fecha_hora_reserva")
    private LocalDateTime fechaHoraReserva;

    @Column(name = "duracion_minutos")
    private int duracionMinutos;

    @Column(name = "cantidad_personas")
    private int cantidadPersonas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReserva estado;

    @Column(name = "notas")
    private String notas;

    public Reserva() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public LocalDateTime getFechaHoraReserva() { return fechaHoraReserva; }
    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) { this.fechaHoraReserva = fechaHoraReserva; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public int getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(int cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    @Override
    public String toString() {
        return "Reserva #" + id + " [" + fechaHoraReserva + "] - Estado: " + estado;
    }
}

