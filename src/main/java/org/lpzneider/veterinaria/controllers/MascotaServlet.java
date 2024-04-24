package org.lpzneider.veterinaria.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lpzneider.veterinaria.configs.ServicePrincipal;
import org.lpzneider.veterinaria.exceptions.ServiceJpaException;
import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Raza;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/mascotas")
public class MascotaServlet extends HttpServlet {
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
        Long idPropietario = null;
        try {
            idPropietario = Long.valueOf(req.getParameter("idPropietario"));
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "formato incorrect del id del propietario");
        }
        try {
            Optional<Usuario> usuario = service.getByIdUsuario(idPropietario);
            if (id == null) {
                List<Mascota> mascotas = service.readMascota();

                if (!mascotas.isEmpty()) {
                    json = ConversorJSON.convertirObjetoAJSON(usuario.get().getMascotas());
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                Mascota mascota = service.getByIdMascota(id)
                        .orElseThrow(() -> new ServiceJpaException("Mascota no encontrada"));
                json = ConversorJSON.convertirObjetoAJSON(mascota);
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
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        String nombre = req.getParameter("nombre");
        String fechaNacimientoStr = req.getParameter("fechaNacimiento");
        String idRazaStr = req.getParameter("idRaza");
        String idPropietarioStr = req.getParameter("idPropietario");

        if (nombre == null || nombre.isEmpty() || fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()
                || idRazaStr == null || idRazaStr.isEmpty() || idPropietarioStr == null || idPropietarioStr.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Date fechaNacimiento;
        Long idRaza;
        Long idPropietario;

        try {
            fechaNacimiento = formatoFecha.parse(fechaNacimientoStr);
            idRaza = Long.valueOf(idRazaStr);
            idPropietario = Long.valueOf(idPropietarioStr);
        } catch (ParseException | NumberFormatException e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }

        Optional<Raza> raza = service.getByIdRaza(idRaza);
        Optional<Usuario> usuario = service.getByIdUsuario(idPropietario);

        if (raza.isEmpty() || usuario.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Mascota mascota = new Mascota(nombre, fechaNacimiento);
        mascota.addRaza(raza.get());
        mascota.addUsuario(usuario.get());
        service.saveOrEditMascota(mascota);

        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(usuario.get().getMascotas());
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

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Long id = null;

        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServiceJpaException("Error al convertir el ID de mascota a número", e);
        }

        Optional<Mascota> mascotaOptional = service.getByIdMascota(id);
        if (mascotaOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Mascota mascota = mascotaOptional.get();

        String nombre = req.getParameter("nombre");
        String idRazaStr = req.getParameter("idRaza");
        String idPropietarioStr = req.getParameter("idPropietario");
        String fechaNacimientoStr = req.getParameter("fechaNacimiento");

        nombre = (nombre == null) ? mascota.getNombre() : nombre;
        Long idRaza = (idRazaStr == null) ? mascota.getRaza().getId() : Long.parseLong(idRazaStr);
        Long idPropietario = (idPropietarioStr == null) ? mascota.getPropietario().getId() : Long.parseLong(idPropietarioStr);
        Date fechaNacimiento = null;

        try {
            fechaNacimiento = (fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) ? mascota.getFechaNacimiento() : formatoFecha.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Formato fecha inválido");
            return;
        }

        Optional<Raza> raza = service.getByIdRaza(idRaza);
        Optional<Usuario> usuario = service.getByIdUsuario(idPropietario);

        if (raza.isEmpty() || usuario.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        mascota.setNombre(nombre);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.addRaza(raza.get());
        mascota.addUsuario(usuario.get());

        service.saveOrEditMascota(mascota);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(usuario.get().getMascotas());
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
        Long idPropietario = null;
        try {
            idPropietario = Long.valueOf(req.getParameter("idPropietario"));
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "formato incorrect del id del propietario");
        }

        try {
            Long id = Long.valueOf(req.getParameter("id"));
            Optional<Mascota> mascota = service.getByIdMascota(id);
            Optional<Usuario> usuario = service.getByIdUsuario(idPropietario);
            if (mascota.isPresent()) {
                service.deleteMascota(id);
                json = ConversorJSON.convertirObjetoAJSON(usuario.get().getMascotas());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "La mascota con el ID " + id + " no existe"));
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
