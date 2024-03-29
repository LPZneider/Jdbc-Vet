package org.lpzneider.veterinaria.service;

import org.lpzneider.veterinaria.models.*;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<Mascota> readMascota();

    Optional<Mascota> getByIdMascota(Long id);

    Mascota saveOrEditMascota(Mascota mascota);

    void deleteMascota(Long id);


    List<Usuario> readUsuario();

    Optional<Usuario> getByIdUsuario(Long id);

    Usuario saveOrEditUsuario(Usuario usuario);

    void deleteUsuario(Long id);


    List<Veterinario> readVeterinario();

    Optional<Veterinario> getByIdVeterinario(Long id);

    Veterinario saveOrEditVeterinario(Veterinario veterinario);

    void deleteVeterinario(Long id);


    List<Veterinaria> readVeterinaria();

    Optional<Veterinaria> getByIdVeterinaria(Long id);

    Veterinaria saveOrEditVeterinaria(Veterinaria veterinaria);

    void deleteVeterinaria(Long id);

    List<Raza> readRaza();

    Optional<Raza> getByIdRaza(Long id);

    Raza saveOrEditRaza(Raza raza);

    void deleteRaza(Long id);
}
