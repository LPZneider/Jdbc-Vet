package org.lpzneider.veterinaria.service;

import org.lpzneider.veterinaria.models.*;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<Mascota> readMascota();

    Optional<Mascota> getByIdMascota(Long id);

    void saveOrEditMascota(Mascota mascota);

    void deleteMascota(Long id);


    List<Usuario> readUsuario();

    Optional<Usuario> getByIdUsuario(Long id);

    void saveOrEditUsuario(Usuario usuario);

    void deleteUsuario(Long id);


    List<Veterinario> readVeterinario();

    Optional<Veterinario> getByIdVeterinario(Long id);

    void saveOrEditVeterinario(Veterinario veterinario);

    void deleteVeterinario(Long id);


    List<Veterinaria> readVeterinaria();

    Optional<Veterinaria> getByIdVeterinaria(Long id);

    void saveOrEditVeterinaria(Veterinaria veterinaria);

    void deleteVeterinaria(Long id);

    List<Raza> readRaza();

    Optional<Raza> getByIdRaza(Long id);

    void saveOrEditRaza(Raza raza);

    void deleteRaza(Long id);
}
