package org.lpzneider.veterinaria.repository;

import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUsuarioImpl implements Repository<Usuario> {
    private Connection conn;

    public RepositoryUsuarioImpl(Connection conn) {
        this.conn = conn;
    }

    public RepositoryUsuarioImpl() {

    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Usuario> read() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuario")) {
            while (rs.next()) {
                usuarios.add(crearUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario getById(Long id) throws SQLException {
        Usuario encontrado = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuario WHERE id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    encontrado = crearUsuario(rs);
                }
            }

        }
        return encontrado;
    }

    @Override
    public Usuario saveOrEdit(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0 ){
            sql = "UPDATE usuario SET nombre=? direccion=? mascotas=?  WHERE id=? ";
        } else {
            sql = "INSERT INTO usuario (nombre,direccion,mascotas) VALUES(?,?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getDireccion());
            stmt.setLong(3, usuario.getMascotas());

            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setLong(4, usuario.getId());
            }

            stmt.executeUpdate();

            if (usuario.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getLong(1));
                    }
                }
            }
        }
        return usuario;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuario WHERE id=?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }
    private static Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario c = new Usuario();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        c.setMascotas(rs.getLong("mascotas"));
        return c;
    }
}
