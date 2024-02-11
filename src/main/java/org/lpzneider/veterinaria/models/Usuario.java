package org.lpzneider.veterinaria.models;

import java.util.List;

public class Usuario {
    private Long id;
    private String nombre;
    private String direccion;
    private Long mascotas;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String direccion, Long mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.mascotas = mascotas;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getMascotas() {
        return mascotas;
    }

    public void setMascotas(Long mascotas) {
        this.mascotas = mascotas;
    }
}
