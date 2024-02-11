package org.lpzneider.veterinaria.models;

public class Veterinario {
    private Long id;
    private String nombre;
    private Long veterinaria_registrada;

    public Veterinario() {
    }

    public Veterinario(Long id, String nombre, Long veterinaria_registrada) {
        this.id = id;
        this.nombre = nombre;
        this.veterinaria_registrada = veterinaria_registrada;
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

    public Long getVeterinaria_registrada() {
        return veterinaria_registrada;
    }

    public void setVeterinaria_registrada(Long veterinaria_registrada) {
        this.veterinaria_registrada = veterinaria_registrada;
    }
}
