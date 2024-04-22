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
import org.lpzneider.veterinaria.models.Raza;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet("/razas")
public class RazaServlet extends HttpServlet {
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
                List<Raza> razas = service.readRaza();

                if (!razas.isEmpty()) {
                    json = ConversorJSON.convertirObjetoAJSON(razas);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                Raza raza = service.getByIdRaza(id)
                        .orElseThrow(() -> new ServiceJpaException("Raza no encontrada"));
                json = ConversorJSON.convertirObjetoAJSON(raza);
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
        String tamanio = req.getParameter("tamanio");
        String alturaStr = req.getParameter("alturaStr");
        String pesoStr = req.getParameter("pesoStr");

        if (nombre == null || nombre.isEmpty() || tamanio == null || tamanio.isEmpty()
                || alturaStr == null || alturaStr.isEmpty() || pesoStr == null || pesoStr.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Integer altura;
        Integer peso;

        try {
            altura = Integer.valueOf(alturaStr);
            peso = Integer.valueOf(pesoStr);
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }


        Raza raza = new Raza(nombre, tamanio, altura, peso);
        service.saveOrEditRaza(raza);
        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(service.readRaza());
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
            throw new ServiceJpaException("Error al convertir el ID de raza a número", e);
        }

        Optional<Raza> razaOptional = service.getByIdRaza(id);
        if (razaOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Raza raza = razaOptional.get();

        String nombre = req.getParameter("nombre");
        String tamanio = req.getParameter("tamanio");
        String alturaStr = req.getParameter("alturaStr");
        String pesoStr = req.getParameter("pesoStr");

        nombre = (nombre == null) ? raza.getNombre() : nombre;
        tamanio = (tamanio == null) ? raza.getTamanio() : tamanio;
        Integer altura = (alturaStr == null) ? raza.getAltura() : Integer.valueOf(alturaStr);
        Integer peso = (pesoStr == null) ? raza.getPeso() : Integer.valueOf(pesoStr);


        raza.setNombre(nombre);
        raza.setTamanio(tamanio);
        raza.setAltura(altura);
        raza.setPeso(peso);

        service.saveOrEditRaza(raza);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(service.readRaza());
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
            Optional<Raza> raza = service.getByIdRaza(id);

            if (raza.isPresent()) {
                service.deleteRaza(id);
                json = ConversorJSON.convertirObjetoAJSON(service.readRaza());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "La raza con el ID " + id + " no existe"));
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
