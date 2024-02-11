package org.lpzneider.veterinaria.service;

import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;

import java.sql.SQLException;
import java.util.List;

public interface Service {
    List<Mascota> readMascota() throws SQLException;

    Mascota getByIdMascota(Long id) throws SQLException;

    Mascota saveOrEditMascota(Mascota mascota) throws SQLException;

    void deleteMascota(Long id) throws SQLException;


    List<Usuario> readUsuario() throws SQLException;

    Usuario getByIdUsuario(Long id) throws SQLException;

    Usuario saveOrEditUsuario(Usuario usuario) throws SQLException;

    void deleteUsuario(Long id) throws SQLException;


    List<Veterinario> readVeterinario() throws SQLException;

    Veterinario getByIdVeterinario(Long id) throws SQLException;

    Veterinario saveOrEditVeterinario(Veterinario veterinario) throws SQLException;

    void deleteVeterinario(Long id) throws SQLException;


    List<Veterinaria> readVeterinaria() throws SQLException;

    Veterinaria getByIdVeterinaria(Long id) throws SQLException;

    Veterinaria saveOrEditVeterinaria(Veterinaria veterinaria) throws SQLException;

    void deleteVeterinaria(Long id) throws SQLException;

}
