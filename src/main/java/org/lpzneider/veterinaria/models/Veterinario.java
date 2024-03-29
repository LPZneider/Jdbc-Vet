package org.lpzneider.veterinaria.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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

    public void setVeterinariaRegistrada(Veterinaria veterinariaRegistrada) {
        this.veterinariaRegistrada = veterinariaRegistrada;
    }
}
