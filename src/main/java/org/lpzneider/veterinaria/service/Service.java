package org.lpzneider.veterinaria.service;

import org.lpzneider.veterinaria.models.*;

import java.util.List;
import java.util.Optional;

public interface Service {
    List<Mascota> readMascota();

    Optional<Mascota> getByIdMascota(Long id);

    void saveOrEditMascota(Mascota mascota);

    void deleteMascota(Long id);

    List<Producto> readProducto();

    Optional<Producto> getByIdProducto(Long id);

    void saveOrEditProducto(Producto producto);

    void deleteProducto(Long id);

    Optional<Usuario> loginUsuario(String username, String password);
    List<Usuario> readUsuario();

    Optional<Usuario> getByIdUsuario(Long id);

    void saveOrEditUsuario(Usuario usuario);

    void deleteUsuario(Long id);

    Optional<Veterinario> loginVeterinario(String username, String password);
    List<Veterinario> readVeterinario();

    Optional<Veterinario> getByIdVeterinario(Long id);

    void saveOrEditVeterinario(Veterinario veterinario);

    void deleteVeterinario(Long id);

    Optional<Veterinaria> loginVeterinaria(String username, String password);
    List<Veterinaria> readVeterinaria();

    Optional<Veterinaria> getByIdVeterinaria(Long id);

    void saveOrEditVeterinaria(Veterinaria veterinaria);

    void deleteVeterinaria(Long id);

    List<Raza> readRaza();

    Optional<Raza> getByIdRaza(Long id);

    void saveOrEditRaza(Raza raza);

    void deleteRaza(Long id);


    List<Tratamiento> readTratamiento();

    Optional<Tratamiento> getByIdTratamiento(Long id);

    void saveOrEditTratamiento(Tratamiento tratamiento);

    void deleteTratamiento(Long id);
}
