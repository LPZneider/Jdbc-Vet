package org.lpzneider.veterinaria.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @JsonIgnore
    @ManyToMany(mappedBy = "usuarios")
    private List<Veterinaria> veterinarias;

    @Embedded
    private Registro registro;


    public Usuario() {
        this.mascotas = new ArrayList<>();
        this.veterinarias = new ArrayList<>();
    }

    public Usuario(Long id, String nombre, String direccion, List<Mascota> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.mascotas = mascotas;
    }

    public Usuario(String nombre, String direccion) {
        this();
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Usuario(String nombre, Registro registro) {
        this.nombre = nombre;
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

    public List<Veterinaria> getVeterinarias() {
        return veterinarias;
    }

    public void setVeterinarias(List<Veterinaria> veterinarias) {
       this.veterinarias = veterinarias;
   }

    public void addMascota(Mascota mascota) {
        this.mascotas.add(mascota);
        mascota.setPropietario(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Usuario usuario = (Usuario) object;
        return Objects.equals(registro, usuario.registro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registro);
    }
}
