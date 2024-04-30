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
import org.lpzneider.veterinaria.models.Registro;
import org.lpzneider.veterinaria.models.Tratamiento;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
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
            if (id == null) {
                Optional<Veterinario> veterinario = service.getByIdVeterinario(idVeterinario);
                if (!veterinario.isEmpty()) {
                    Long finalId = id;
                    Tratamiento tratamiento = veterinario.get().getTratamientos().stream().filter((tratamiento1) -> tratamiento1.getId() == finalId).findAny().get();
                    json = ConversorJSON.convertirObjetoAJSON(tratamiento);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                Optional<Veterinario> veterinario = service.getByIdVeterinario(idVeterinario);
                json = ConversorJSON.convertirObjetoAJSON(veterinario.get().getTratamientos());
                resp.setStatus(HttpServletResponse.SC_OK);
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


        String idVeterinaria = req.getParameter("idVeterinaria");
        String password = req.getParameter("password");
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");

        if (nombre == null || nombre.isEmpty() || idVeterinaria == null || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Long idVet;
        try {
            idVet = Long.valueOf(idVeterinaria);
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }

        Optional<Veterinaria> optionalVeterinaria = service.getByIdVeterinaria(idVet);

        if (optionalVeterinaria.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }
        Registro registro = new Registro(email, password);
        Veterinario veterinario = new Veterinario(nombre, optionalVeterinaria.get(), registro);
        service.saveOrEditVeterinario(veterinario);

        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(optionalVeterinaria.get().getVeterinarios());
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


        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServiceJpaException("Error al convertir el ID de veterinario a número", e);
        }

        Optional<Veterinario> veterinarioOptional = service.getByIdVeterinario(id);
        if (veterinarioOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Veterinario veterinario = veterinarioOptional.get();
        String nombre = req.getParameter("nombre");
        String idVeterinaria = req.getParameter("idVeterinaria");


        nombre = (nombre == null) ? veterinario.getNombre() : nombre;
        Long idVet = (idVeterinaria == null) ? veterinario.getVeterinariaRegistrada().getId() : Long.valueOf(idVeterinaria);

        Optional<Veterinaria> optionalVeterinaria = service.getByIdVeterinaria(idVet);

        if (optionalVeterinaria.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        veterinario.setNombre(nombre);
        veterinario.setVeterinariaRegistrada(optionalVeterinaria.get());

        service.saveOrEditVeterinario(veterinario);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(optionalVeterinaria.get().getVeterinarios());
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
            Long idVeterinaria = Long.valueOf(req.getParameter("idVeterinaria"));
            Optional<Veterinario> veterinario = service.getByIdVeterinario(id);
            Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(idVeterinaria);

            if (veterinario.isPresent() && veterinaria.isPresent()) {
                service.deleteVeterinario(id);
                json = ConversorJSON.convertirObjetoAJSON(veterinaria.get().getVeterinarios());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "El veterinario con el ID " + id + " no existe" + idVeterinaria));
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
