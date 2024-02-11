package org.lpzneider.veterinaria.service;

import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;
import org.lpzneider.veterinaria.repository.*;
import org.lpzneider.veterinaria.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceVeterinaria implements Service {

    private Repository<Mascota> mascotaRepository;
    private Repository<Usuario> usuarioRepository;
    private Repository<Veterinario> veterinarioRepository;
    private Repository<Veterinaria> veterinariaRepository;

    public ServiceVeterinaria(){
        this.mascotaRepository = new RepositoryMascotaImpl();
        this.usuarioRepository = new RepositoryUsuarioImpl();
        this.veterinarioRepository = new RepositoryVeterinarioImpl();
        this.veterinariaRepository = new RepositoryVeterinariaImpl();
    }

    @Override
    public List<Mascota> readMascota() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            mascotaRepository.setConn(conn);
            return mascotaRepository.read();

        }
    }

    @Override
    public Mascota getByIdMascota(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            mascotaRepository.setConn(conn);
            return mascotaRepository.getById(id);
        }
    }

    @Override
    public Mascota saveOrEditMascota(Mascota mascota) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            mascotaRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            Mascota newMascota = null;
            try {
                newMascota = mascotaRepository.saveOrEdit(mascota);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
            return newMascota;
        }
    }

    @Override
    public void deleteMascota(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            mascotaRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            try {
                mascotaRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
        }
    }

    @Override
    public List<Usuario> readUsuario() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            usuarioRepository.setConn(conn);
            return usuarioRepository.read();

        }
    }

    @Override
    public Usuario getByIdUsuario(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            usuarioRepository.setConn(conn);
            return usuarioRepository.getById(id);
        }
    }

    @Override
    public Usuario saveOrEditUsuario(Usuario usuario) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            usuarioRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            Usuario newUsuario = null;
            try {
                newUsuario = usuarioRepository.saveOrEdit(usuario);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
            return newUsuario;
        }
    }

    @Override
    public void deleteUsuario(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            usuarioRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            try {
                usuarioRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
        }
    }

    @Override
    public List<Veterinario> readVeterinario() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinarioRepository.setConn(conn);
            return veterinarioRepository.read();

        }
    }

    @Override
    public Veterinario getByIdVeterinario(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinarioRepository.setConn(conn);
            return veterinarioRepository.getById(id);
        }
    }

    @Override
    public Veterinario saveOrEditVeterinario(Veterinario veterinario) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinarioRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            Veterinario newVeterinario = null;
            try {
                newVeterinario = veterinarioRepository.saveOrEdit(veterinario);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
            return newVeterinario;
        }
    }

    @Override
    public void deleteVeterinario(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinarioRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            try {
                veterinarioRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
        }
    }

    @Override
    public List<Veterinaria> readVeterinaria() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinariaRepository.setConn(conn);
            return veterinariaRepository.read();

        }
    }

    @Override
    public Veterinaria getByIdVeterinaria(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinariaRepository.setConn(conn);
            return veterinariaRepository.getById(id);
        }
    }

    @Override
    public Veterinaria saveOrEditVeterinaria(Veterinaria veterinaria) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinariaRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            Veterinaria newVeterinaria = null;
            try {
                newVeterinaria = veterinariaRepository.saveOrEdit(veterinaria);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
            return newVeterinaria;
        }
    }

    @Override
    public void deleteVeterinaria(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnetion()) {
            veterinariaRepository.setConn(conn);
            if (conn.getAutoCommit()) conn.setAutoCommit(false);
            try {
                veterinariaRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();

            }
        }
    }
}
