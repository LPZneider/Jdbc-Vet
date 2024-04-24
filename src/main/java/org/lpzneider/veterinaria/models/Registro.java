package org.lpzneider.veterinaria.models;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Registro {
    private String email;
    private String password;

    public Registro() {
    }

    public Registro(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Registro registro = (Registro) object;
        return Objects.equals(email, registro.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
