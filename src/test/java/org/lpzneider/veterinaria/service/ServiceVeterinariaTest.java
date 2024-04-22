package org.lpzneider.veterinaria.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinario;
import org.lpzneider.veterinaria.repository.Repository;
import org.lpzneider.veterinaria.repository.RepositoryRegister;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ServiceVeterinariaTest {
    @Mock
    private Repository<Mascota> mascotaRepository;
    @Mock
    private RepositoryRegister<Veterinario> veterinarioRepository;
    @Mock
    private RepositoryRegister<Usuario> usuarioRepository;

    @InjectMocks
    private ServiceVeterinaria serviceVeterinaria;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveMascotaNueva() throws Exception {
        // Crear una mascota nueva
        Mascota mascotaNueva = new Mascota();

        // Simular el comportamiento del repositorio
        doNothing().when(mascotaRepository).saveOrEdit(mascotaNueva);

        // Guardar la mascota
        serviceVeterinaria.saveOrEditMascota(mascotaNueva);

        // Verificar que se llamó al método saveOrEdit del repositorio con la mascota nueva
        verify(mascotaRepository).saveOrEdit(mascotaNueva);
    }

    @Test
    public void testEditMascotaExistente() throws Exception {
        // Crear una mascota existente
        Mascota mascotaExistente = new Mascota();
        mascotaExistente.setId(1L);

        // Simular el comportamiento del repositorio
        doNothing().when(mascotaRepository).saveOrEdit(mascotaExistente);

        // Guardar la mascota
        serviceVeterinaria.saveOrEditMascota(mascotaExistente);

        // Verificar que se llamó al método saveOrEdit del repositorio con la mascota existente
        verify(mascotaRepository).saveOrEdit(mascotaExistente);
    }

    @Test
    public void testSaveVeterinario() throws Exception {
        // Crear un veterinario
        Veterinario veterinario = new Veterinario();

        // Configurar el comportamiento del repositorio mock
        doNothing().when(veterinarioRepository).saveOrEdit(veterinario);

        // Llamar al método saveOrEditVeterinario del servicio
        serviceVeterinaria.saveOrEditVeterinario(veterinario);

        // Verificar que se llamó al método saveOrEdit del repositorio mock con el veterinario
        verify(veterinarioRepository).saveOrEdit(veterinario);
    }
    @Test
    public void testEditSaveVeterinario() throws Exception {
        // Crear un veterinario existente
        Veterinario veterinario = new Veterinario();
        veterinario.setId(1L);

        // Configurar el comportamiento del repositorio mock
        doNothing().when(veterinarioRepository).saveOrEdit(veterinario);

        // Llamar al método saveOrEditVeterinario del servicio
        serviceVeterinaria.saveOrEditVeterinario(veterinario);

        // Verificar que se llamó al método saveOrEdit del repositorio mock con el veterinario
        verify(veterinarioRepository).saveOrEdit(veterinario);
    }
    @Test
    public void testSaveUsuario() throws Exception {
        // Crear un usuario
        Usuario usuario = new Usuario();

        // Configurar el comportamiento del repositorio mock
        doNothing().when(usuarioRepository).saveOrEdit(usuario);

        // Llamar al método saveOrEditUsuario del servicio
        serviceVeterinaria.saveOrEditUsuario(usuario);

        // Verificar que se llamó al método saveOrEdit del repositorio mock con el usuario
        verify(usuarioRepository).saveOrEdit(usuario);
    }
    @Test
    public void testEditUsuario() throws Exception {
        // Crear un usuario existente
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        // Configurar el comportamiento del repositorio mock
        doNothing().when(usuarioRepository).saveOrEdit(usuario);

        // Llamar al método saveOrEditUsuario del servicio
        serviceVeterinaria.saveOrEditUsuario(usuario);

        // Verificar que se llamó al método saveOrEdit del repositorio mock con el usuario
        verify(usuarioRepository).saveOrEdit(usuario);
    }

    @Test
    public void testGetByIdUsuario() throws Exception {
        // Crear un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Configurar el comportamiento del repositorio mock
        when(usuarioRepository.getById(1L)).thenReturn(usuario);

        // Llamar al método getByIdUsuario del servicio
        Optional<Usuario> result = serviceVeterinaria.getByIdUsuario(1L);

        // Verificar que se llamó al método getById del repositorio mock con el ID proporcionado
        verify(usuarioRepository).getById(1L);

        // Verificar que el usuario devuelto por el servicio es el mismo que el usuario de prueba
        assertEquals(usuario, result.orElse(null));
    }

}
