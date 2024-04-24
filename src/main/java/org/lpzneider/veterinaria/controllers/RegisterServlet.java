package org.lpzneider.veterinaria.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lpzneider.veterinaria.configs.ServicePrincipal;
import org.lpzneider.veterinaria.models.Registro;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Inject
    @ServicePrincipal
    private Service service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String idRol = req.getParameter("idRol");
        String json = null;
        if (password == null || password.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "La contraseña no puede estar vacía.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "El nombre no puede estar vacío.");
        }
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "El formato del correo electrónico no es válido.");
        }
        Registro registro = new Registro(email, password);
        switch (idRol) {
            case "1":
                Usuario usuario = new Usuario(nombre, registro);
                if (service.readUsuario().stream().anyMatch(u -> u == usuario)) {
                    ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "El usuario ya existe.");
                } else {
                    service.saveOrEditUsuario(usuario);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("registro", "existoso"));
                }
                break;
            case "3":
                Veterinaria veterinaria = new Veterinaria(nombre, registro);
                if (service.readVeterinaria().stream().anyMatch(u -> u == veterinaria)) {
                    ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "la veterinaria ya existe.");
                } else {
                    service.saveOrEditVeterinaria(veterinaria);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("registro", "existoso"));
                }
                break;
            default:
                ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
                return;
        }
        resp.setContentType("application/json");

        assert json != null;
        resp.getWriter().write(json);
    }

}
