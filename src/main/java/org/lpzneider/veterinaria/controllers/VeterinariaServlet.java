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
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/veterinarias")
public class VeterinariaServlet extends HttpServlet {
    @Inject
    @ServicePrincipal
    private Service service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        String json = null;
        try {
            id = Long.valueOf(req.getParameter("id"));
            if (id <= 0) {
                throw new ServiceJpaException("el id a buscar no puede ser 0 o negativo");
            }
        } catch (NumberFormatException e) {
            id = null;
        }

        try {
            if (id == null) {
                List<Veterinaria> veterinarias = service.readVeterinaria();
                List<Veterinaria> veterinariasReducido = veterinarias.stream().map(veterinaria ->
                        new Veterinaria(veterinaria.getId(),
                                veterinaria.getNombre(),
                                veterinaria.getDireccion(),
                                veterinaria.getVeterinarios())).toList();
                if (!veterinarias.isEmpty()) {
                    json = ConversorJSON.convertirObjetoAJSON(veterinariasReducido);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
               Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(id);
                json = ConversorJSON.convertirObjetoAJSON(veterinaria.get());
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


        String nombre = req.getParameter("nombre");
        String direccion = req.getParameter("direccion");

        if (nombre == null || nombre.isEmpty() || direccion == null) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Veterinaria veterinaria = new Veterinaria(nombre, direccion);
        service.saveOrEditVeterinaria(veterinaria);

        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(service.readVeterinaria());
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
            throw new ServiceJpaException("Error al convertir el ID de veterinaria a número", e);
        }

        Optional<Veterinaria> veterinariaOptional = service.getByIdVeterinaria(id);
        if (veterinariaOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Veterinaria veterinaria = veterinariaOptional.get();
        String nombre = req.getParameter("nombre");
        String direccion = req.getParameter("direccion");


        nombre = (nombre == null) ? veterinaria.getNombre() : nombre;
        direccion = (direccion == null) ? veterinaria.getDireccion() : direccion;


        veterinaria.setNombre(nombre);
        veterinaria.setDireccion(direccion);

        service.saveOrEditVeterinaria(veterinaria);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(service.readVeterinaria());
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
            Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(id);

            if (veterinaria.isPresent()) {
                service.deleteVeterinaria(id);
                json = ConversorJSON.convertirObjetoAJSON(service.readVeterinaria());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "La veterinaria con el ID " + id + " no existe"));
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
