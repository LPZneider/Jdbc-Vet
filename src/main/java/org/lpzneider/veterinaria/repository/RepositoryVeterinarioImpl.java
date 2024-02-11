package org.lpzneider.veterinaria.repository;

import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryVeterinarioImpl implements Repository<Veterinario> {
    private Connection conn;

    public RepositoryVeterinarioImpl(Connection conn) {
        this.conn = conn;
    }

    public RepositoryVeterinarioImpl() {

    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Veterinario> read() throws SQLException {
        List<Veterinario> veterinarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM veterinarios")) {
            while (rs.next()) {
                veterinarios.add(crearVeterinario(rs));
            }
        }
        return veterinarios;
    }

    @Override
    public Veterinario getById(Long id)  throws SQLException{
        Veterinario encontrada = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM veterinarios WHERE id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    encontrada = crearVeterinario(rs);
                }
            }

        }
        return encontrada;
    }

    @Override
    public Veterinario saveOrEdit(Veterinario veterinario) throws SQLException {
        String sql;
        if (veterinario.getId() != null && veterinario.getId() > 0 ){
            sql = "UPDATE veterinarios SET nombre=? veterinaria_registrada=? WHERE id=? ";
        } else {
            sql = "INSERT INTO veterinarios(nombre,veterinaria_registrada) VALUES(?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veterinario.getNombre());
            stmt.setLong(2, veterinario.getVeterinaria_registrada());

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
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM veterinarios WHERE id=?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }
    private static Veterinario crearVeterinario(ResultSet rs) throws SQLException {
        Veterinario c = new Veterinario();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setVeterinaria_registrada(rs.getLong("veterinaria_registrada"));
        return c;
    }
}
