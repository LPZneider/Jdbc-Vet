package org.lpzneider.veterinaria.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "veterinarias")
public class Veterinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_usuarios_veterinarias", joinColumns = @JoinColumn(name = "usuario_id")
            , inverseJoinColumns = @JoinColumn(name = "veterinaria_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "veterinaria_id"}))
    private List<Usuario> usuarios;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "veterinariaRegistrada")
    private List<Veterinario> veterinarios;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "veterinaria")
    private List<Producto> productos;


    @Embedded
    private Registro registro;

    public Veterinaria() {
        this.usuarios = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
    }

    public Veterinaria(String nombre, String direccion) {
        this();
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Veterinaria(String nombre, Registro registro) {
        this.nombre = nombre;
        this.registro = registro;
    }

    public Veterinaria(Long id, String nombre, String direccion, List<Veterinario> veterinarios) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.veterinarios = veterinarios;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getVeterinarias().add(this);
    }
    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.getVeterinarias().remove(this);
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Veterinaria that = (Veterinaria) object;
        return Objects.equals(registro, that.registro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registro);
    }
}
