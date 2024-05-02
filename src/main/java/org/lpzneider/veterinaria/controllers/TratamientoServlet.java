package org.lpzneider.veterinaria.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lpzneider.veterinaria.configs.ServicePrincipal;
import org.lpzneider.veterinaria.exceptions.ServiceJpaException;
import org.lpzneider.veterinaria.models.*;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/tratamientos")
public class TratamientoServlet extends HttpServlet {
    @Inject
    @ServicePrincipal
    private Service service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        Long idVeterinario = null;
        String json = null;
        try {
            id = Long.valueOf(req.getParameter("id"));
            idVeterinario = Long.valueOf(req.getParameter("idVeterinario"));
            if (id <= 0) {
                throw new ServiceJpaException("el id a buscar no puede ser 0 o negativo");
            }
        } catch (NumberFormatException e) {
            id = null;
        }
        try {
            if (id != null) {
                Optional<Veterinario> veterinario = service.getByIdVeterinario(idVeterinario);
                    Optional<Mascota> mascota = service.getByIdMascota(id);
                if (veterinario.isPresent() && mascota.isPresent()) {
                    json = ConversorJSON.convertirObjetoAJSON(mascota.get().getTratamientos());
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                Optional<Veterinario> veterinario = service.getByIdVeterinario(idVeterinario);
                if(veterinario.isPresent()) {
                    json = ConversorJSON.convertirObjetoAJSON(veterinario.get().getTratamientos());
                    resp.setStatus(HttpServletResponse.SC_OK);
                }else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } catch (JsonProcessingException e) {
            ManejadorErrores.enviarErrorInterno(resp);
        }
        resp.setContentType("application/json");

        assert json != null;
        resp.getWriter().write(json);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String idVeterinario = req.getParameter("id_veterinario");
        String descripcion = req.getParameter("descripcion");
        String nombre = req.getParameter("nombre");
        String idMascota = req.getParameter("id_mascota");

        if (nombre == null || nombre.isEmpty() || idVeterinario == null || descripcion == null || descripcion.isEmpty() || idMascota == null || idMascota.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Long idVet;
        Long idMasco;
        try {
            idVet = Long.valueOf(idVeterinario);
            idMasco = Long.valueOf(idMascota);
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }
        Optional<Veterinario> optionalVeterinario = service.getByIdVeterinario(idVet);
        Optional<Mascota> optionalMascota = service.getByIdMascota(idMasco);

        if (optionalVeterinario.isEmpty() || optionalMascota.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Tratamiento tratamiento = new Tratamiento(nombre, descripcion,optionalVeterinario.get(), optionalMascota.get());
        service.saveOrEditTratamiento(tratamiento);

        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(optionalVeterinario.get().getVeterinariaRegistrada().getUsuarios());
        } catch (Exception e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }

        if (json == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error al generar la respuesta JSON: 'json' es null");
            return;
        }

        resp.setContentType("application/json");
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = null;
        Long idVeterinario =null;

        try {
            id = Long.valueOf(req.getParameter("id"));
            idVeterinario = Long.valueOf(req.getParameter("id_veterinario"));
        } catch (NumberFormatException e) {
            throw new ServiceJpaException("Error al convertir el ID de veterinario a número", e);
        }

        Optional<Veterinario> veterinarioOptional = service.getByIdVeterinario(idVeterinario);
        Optional<Tratamiento> tratamientoOptional = service.getByIdTratamiento(id);
        if (veterinarioOptional.isEmpty() || tratamientoOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Veterinario veterinario = veterinarioOptional.get();
        Tratamiento tratamiento = tratamientoOptional.get();
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");


        nombre = (nombre == null) ? tratamiento.getNombre() : nombre;
        descripcion= (descripcion == null)? tratamiento.getDescripcion() : descripcion;

        tratamiento.setNombre(nombre);
        tratamiento.setDescripcion(descripcion);
        service.saveOrEditTratamiento(tratamiento);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(veterinario.getVeterinariaRegistrada().getUsuarios());
            if (json != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(json);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                ManejadorErrores.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar la respuesta JSON: 'json' es null");
            }
        } catch (Exception e) {
            ManejadorErrores.enviarErrorInterno(resp);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String json = null;

        try {
            Long id = Long.valueOf(req.getParameter("id"));
            Long idVeterinario = Long.valueOf(req.getParameter("idVeterinario"));

            Optional<Tratamiento> tratamientoOptional = service.getByIdTratamiento(id);
            Optional<Veterinario> veterinarioOptional = service.getByIdVeterinario(idVeterinario);

            if (tratamientoOptional.isPresent() && veterinarioOptional.isPresent()) {
                service.deleteTratamiento(id);
                json = ConversorJSON.convertirObjetoAJSON(veterinarioOptional.get().getVeterinariaRegistrada().getUsuarios());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "El tratamiento con el ID " + id + " no existe"));
                resp.getWriter().write(json);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "ID inválido"));
            resp.getWriter().write(json);
        } catch (ServiceJpaException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "Error interno del servidor"));
            resp.getWriter().write(json);
        }
    }
}
