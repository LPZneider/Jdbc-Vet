package org.lpzneider.veterinaria.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "propietario")
    private List<Mascota> mascotas;

    @ManyToMany(mappedBy = "usuarios")
    private List<Veterinaria> veterinarias;

    @Embedded
    private Registro registro;



    public Usuario() {
        this.mascotas = new ArrayList<>();
        this.veterinarias = new ArrayList<>();
    }

    public Usuario(String nombre, String direccion) {
        this();
        this.nombre = nombre;
        this.direccion = direccion;
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

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public void addMascota(Mascota mascota) {
        this.mascotas.add(mascota);
        mascota.setPropietario(this);
    }
}
