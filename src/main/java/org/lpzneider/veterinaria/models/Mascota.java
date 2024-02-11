package org.lpzneider.veterinaria.models;

import java.util.Date;

public class Mascota {
    private Long id;
    private String nombre;
    private Long raza;
    private Date fecha_nacimiento;
    private Long propietario;

    public Mascota() {
    }

    public Mascota(Long id, String nombre, Long raza, Date fecha_nacimiento, Long propietario) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.fecha_nacimiento = fecha_nacimiento;
        this.propietario = propietario;
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

    public Long getRaza() {
        return raza;
    }

    public void setRaza(Long raza) {
        this.raza = raza;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Long getPropietario() {
        return propietario;
    }

    public void setPropietario(Long propietario) {
        this.propietario = propietario;
    }
}
