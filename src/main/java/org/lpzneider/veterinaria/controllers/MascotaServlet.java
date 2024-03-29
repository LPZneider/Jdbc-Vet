package org.lpzneider.veterinaria.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.models.Raza;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.service.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@WebServlet("/mascotas")
public class MascotaServlet extends HttpServlet {
    @Inject
    private Service service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = null;
        }
        if (id == null) {
            json = mapper.writeValueAsString(service.readMascota());
        } else if (id > 0) {
            json = mapper.writeValueAsString(service.getByIdMascota(id));
        }
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        String nombre = req.getParameter("nombre");
        String fechaNacimientoStr = req.getParameter("fechaNacimiento");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        Long idRaza = null;
        Long idPropietario = null;
        try {
            idRaza = Long.valueOf(req.getParameter("idRaza"));
            idPropietario = Long.valueOf(req.getParameter("idPropietario"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parámetros inválidos de id");
        }

        if (nombre == null || nombre.isEmpty() || fechaNacimientoStr == null || fechaNacimientoStr.isEmpty()) {
            throw new IllegalArgumentException("Parámetros inválidos nombres");
        }

        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formatoFecha.parse(fechaNacimientoStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formaro fecha invalido");
        }


        Optional<Raza> raza = service.getByIdRaza(idRaza);
        Optional<Usuario> usuario = service.getByIdUsuario(idPropietario);
        if (raza.isPresent() && usuario.isPresent()) {
            Mascota mascota = new Mascota(nombre, raza.get(), fechaNacimiento, usuario.get());
            service.saveOrEditMascota(mascota);
            json = mapper.writeValueAsString(service.readVeterinaria());
            resp.setStatus(HttpServletResponse.SC_OK);
        }


        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPut(req, resp);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doDelete(req, resp);
//    }
}
