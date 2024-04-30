package org.lpzneider.veterinaria.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tratamientos")
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_veterinario")
    private Veterinario veterinario;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    public Tratamiento() {
    }


    public Tratamiento(String nombre, String descripcion, Veterinario veterinario, Mascota mascota) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.mascota = mascota;
    }

    public Tratamiento(Long id, String nombre, String descripcion, Veterinario veterinario, Mascota mascota) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.mascota = mascota;
    }

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

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
