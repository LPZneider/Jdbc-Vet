package org.lpzneider.veterinaria.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veterinarios")
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veterinaria")
    private Veterinaria veterinariaRegistrada;

    @Embedded
    private Registro registro;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "veterinario")
    private List<Tratamiento> tratamientos;

    public Veterinario() {
        tratamientos = new ArrayList<>();
    }

    public Veterinario(String nombre, Veterinaria veterinariaRegistrada) {
        this.nombre = nombre;
        this.veterinariaRegistrada = veterinariaRegistrada;
    }

    public Veterinario(String nombre, Veterinaria veterinariaRegistrada, Registro registro) {
        this.nombre = nombre;
        this.veterinariaRegistrada = veterinariaRegistrada;
        this.registro = registro;
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

    public Veterinaria getVeterinariaRegistrada() {
        return veterinariaRegistrada;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public void setVeterinariaRegistrada(Veterinaria veterinariaRegistrada) {
        this.veterinariaRegistrada = veterinariaRegistrada;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public void addTratamientos(Tratamiento tratamiento, Mascota mascota) {
        tratamiento.setVeterinario(this);
        mascota.getTratamientos().add(tratamiento);
        tratamiento.setMascota(mascota);
        this.tratamientos.add(tratamiento);
    }
}
