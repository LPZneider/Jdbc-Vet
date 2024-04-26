package org.lpzneider.veterinaria.service;

import jakarta.inject.Inject;
import org.lpzneider.veterinaria.configs.ServicePrincipal;
import org.lpzneider.veterinaria.exceptions.ServiceJpaException;
import org.lpzneider.veterinaria.interceptor.Transactional;
import org.lpzneider.veterinaria.models.*;
import org.lpzneider.veterinaria.repository.Repository;
import org.lpzneider.veterinaria.repository.RepositoryRegister;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ServicePrincipal
@org.lpzneider.veterinaria.configs.Service
@Transactional
public class ServiceVeterinaria implements Service {


    @Inject
    private Repository<Mascota> mascotaRepository;
    @Inject
    private RepositoryRegister<Usuario> usuarioRepository;
    @Inject
    private RepositoryRegister<Veterinario> veterinarioRepository;
    @Inject
    private RepositoryRegister<Veterinaria> veterinariaRepository;
    @Inject
    private Repository<Raza> razaRepository;
    @Inject
    private Repository<Producto> productoRepository;


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
    public void saveOrEditMascota(Mascota mascota) {
        try {
            mascotaRepository.saveOrEdit(mascota);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
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
    public List<Producto> readProducto() {
        try {
            return productoRepository.read();
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> getByIdProducto(Long id) {
        try {
            return Optional.ofNullable(productoRepository.getById(id));
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void saveOrEditProducto(Producto producto) {
        try {
            productoRepository.saveOrEdit(producto);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteProducto(Long id) {
        try {
            productoRepository.delete(id);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> loginUsuario(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.byEmail(username)).filter(u -> u.getRegistro().getPassword().equals(password));
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
    public void saveOrEditUsuario(Usuario usuario) {
        try {
            usuarioRepository.saveOrEdit(usuario);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
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
    public Optional<Veterinario> loginVeterinario(String username, String password) {
        try {
            return Optional.ofNullable(veterinarioRepository.byEmail(username)).filter(u -> u.getRegistro().getPassword().equals(password));
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
    public void saveOrEditVeterinario(Veterinario veterinario) {
        try {
            veterinarioRepository.saveOrEdit(veterinario);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
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
    public Optional<Veterinaria> loginVeterinaria(String username, String password) {
        try {
            return Optional.ofNullable(veterinariaRepository.byEmail(username)).filter(u -> u.getRegistro().getPassword().equals(password));
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
    public void saveOrEditVeterinaria(Veterinaria veterinaria) {
        try {
            veterinariaRepository.saveOrEdit(veterinaria);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
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
    public void saveOrEditRaza(Raza mascota) {
        try {
            razaRepository.saveOrEdit(mascota);
        } catch (Exception e) {
            throw new ServiceJpaException(e.getMessage(), e.getCause());
        }
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
