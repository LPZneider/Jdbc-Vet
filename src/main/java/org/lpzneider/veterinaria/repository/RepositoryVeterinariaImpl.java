package org.lpzneider.veterinaria.repository;

import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryVeterinariaImpl implements Repository<Veterinaria> {

    private Connection conn;

    public RepositoryVeterinariaImpl(Connection conn) {
        this.conn = conn;
    }

    public RepositoryVeterinariaImpl() {

    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Veterinaria> read() throws SQLException {
        List<Veterinaria> veterinarias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM veterinaria")) {
            while (rs.next()) {
                veterinarias.add(crearVeterinaria(rs));
            }
        }
        return veterinarias;
    }

    @Override
    public Veterinaria getById(Long id)  throws SQLException{
        Veterinaria encontrada = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM veterinaria WHERE id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    encontrada = crearVeterinaria(rs);
                }
            }

        }
        return encontrada;
    }

    @Override
    public Veterinaria saveOrEdit(Veterinaria veterinario) throws SQLException {
        String sql;
        if (veterinario.getId() != null && veterinario.getId() > 0 ){
            sql = "UPDATE veterinaria SET nombre=? direccion=? WHERE id=? ";
        } else {
            sql = "INSERT INTO veterinaria(nombre,direccion) VALUES(?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veterinario.getNombre());
            stmt.setString(2, veterinario.getDireccion());

            if (veterinario.getId() != null && veterinario.getId() > 0) {
                stmt.setLong(3, veterinario.getId());
            }

            stmt.executeUpdate();

            if (veterinario.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        veterinario.setId(rs.getLong(1));
                    }
                }
            }
        }
        return veterinario;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM veterinaria WHERE id=?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }
    private static Veterinaria crearVeterinaria(ResultSet rs) throws SQLException {
        Veterinaria c = new Veterinaria();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        return c;
    }
}
