package org.lpzneider.veterinaria.repository;

import org.lpzneider.veterinaria.models.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryMascotaImpl implements Repository<Mascota> {
    private Connection conn;

    public RepositoryMascotaImpl(Connection conn) {
        this.conn = conn;
    }

    public RepositoryMascotaImpl() {

    }


    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Mascota> read() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mascotas")) {
            while (rs.next()) {
                mascotas.add(crearMascota(rs));
            }
        }
        return mascotas;
    }

    @Override
    public Mascota getById(Long id)  throws SQLException{
       Mascota encontrada = null;
       try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM mascotas WHERE id=?")) {
           stmt.setLong(1, id);
           try(ResultSet rs = stmt.executeQuery()) {
               if (rs.next()){
                   encontrada = crearMascota(rs);
               }
           }

       }
       return encontrada;
    }

    @Override
    public Mascota saveOrEdit(Mascota mascota) throws SQLException {
        String sql;
        if (mascota.getId() != null && mascota.getId() > 0 ){
            sql = "UPDATE mascotas SET nombre=? raza=? fecha_de_nacimiento=? propietario=? WHERE id=? ";
        } else {
            sql = "INSERT INTO mascotas(nombre,raza,fecha_de_nacimiento,propietario) VALUES(?,?,?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setLong(2, mascota.getRaza());
            stmt.setDate(3, new Date(mascota.getFecha_nacimiento().getTime()));
            stmt.setLong(4, mascota.getPropietario());

            if (mascota.getId() != null && mascota.getId() > 0) {
                stmt.setLong(5, mascota.getId());
            }

            stmt.executeUpdate();

            if (mascota.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        mascota.setId(rs.getLong(1));
                    }
                }
            }
        }
        return mascota;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM mascotas WHERE id=?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }
    private static Mascota crearMascota(ResultSet rs) throws SQLException {
        Mascota c = new Mascota();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setRaza(rs.getLong("raza"));
        c.setFecha_nacimiento(rs.getDate("fecha_de_nacimiento"));
        c.setPropietario(rs.getLong("propietario"));
        return c;
    }
}
