package org.lpzneider.veterinaria.service;

import jakarta.inject.Inject;
import org.lpzneider.veterinaria.exceptions.ServiceJpaException;
import org.lpzneider.veterinaria.models.*;
import org.lpzneider.veterinaria.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.lpzneider.veterinaria.configs.Service
public class ServiceVeterinaria implements Service {


    @Inject
    private Repository<Mascota> mascotaRepository;
    @Inject
    private Repository<Usuario> usuarioRepository;
    @Inject
    private Repository<Veterinario> veterinarioRepository;
    @Inject
    private Repository<Veterinaria> veterinariaRepository;
    @Inject
    private Repository<Raza> razaRepository;


    @Override
    public List<Mascota> readMascota() {
        try {
            return mascotaRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Mascota> getByIdMascota(Long id) {
        try {
            return Optional.ofNullable(mascotaRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Mascota saveOrEditMascota(Mascota mascota) {
        Mascota newMascota = null;
        try {
            newMascota = mascotaRepository.saveOrEdit(mascota);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
        return newMascota;
    }


    @Override
    public void deleteMascota(Long id) {
        try {
            mascotaRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public List<Usuario> readUsuario() {
        try {
            return usuarioRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> getByIdUsuario(Long id) {
        try {
            return Optional.ofNullable(usuarioRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Usuario saveOrEditUsuario(Usuario usuario) {
        Usuario newUsuario = null;
        try {
            newUsuario = usuarioRepository.saveOrEdit(usuario);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
        return newUsuario;
    }


    @Override
    public void deleteUsuario(Long id) {
        try {
            usuarioRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public List<Veterinario> readVeterinario() {
        try {
            return veterinarioRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Veterinario> getByIdVeterinario(Long id) {
        try {
            return Optional.ofNullable(veterinarioRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Veterinario saveOrEditVeterinario(Veterinario veterinario) {
        Veterinario newVeterinario = null;
        try {
            newVeterinario = veterinarioRepository.saveOrEdit(veterinario);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
        return newVeterinario;
    }


    @Override
    public void deleteVeterinario(Long id) {
        try {
            veterinarioRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Veterinaria> readVeterinaria() {
        try {
            return veterinariaRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Veterinaria> getByIdVeterinaria(Long id) {
        try {
            return Optional.ofNullable(veterinariaRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Veterinaria saveOrEditVeterinaria(Veterinaria veterinaria) {
        Veterinaria newVeterinaria = null;
        try {
            newVeterinaria = veterinariaRepository.saveOrEdit(veterinaria);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
        return newVeterinaria;
    }

    @Override
    public void deleteVeterinaria(Long id) {
        try {
            veterinariaRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }


    @Override
    public List<Raza> readRaza() {
        try {
            return razaRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Raza> getByIdRaza(Long id) {
        try {
            return Optional.ofNullable(razaRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Raza saveOrEditRaza(Raza mascota) {
        Raza newRaza = null;
        try {
            newRaza = razaRepository.saveOrEdit(mascota);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
        return newRaza;
    }


    @Override
    public void deleteRaza(Long id) {
        try {
            razaRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }
}
