package org.lpzneider.veterinaria.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lpzneider.veterinaria.configs.ServicePrincipal;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Inject
    @ServicePrincipal
    private Service service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String idRol = req.getParameter("idRol");
        String json = null;

        switch (idRol) {
            case "1":
                Optional<Usuario> usuario = service.loginUsuario(email, password);
                if (usuario.isEmpty()) {
                    ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
                    return;
                } else {
                    json = ConversorJSON.convertirObjetoAJSON(usuario.get());
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                break;
            case "2":
                Optional<Veterinario> veterinario = service.loginVeterinario(email, password);
                if (veterinario.isEmpty()) {
                    ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
                    return;
                } else {
                    Veterinaria veterinaria = veterinario.get().getVeterinariaRegistrada();
                    veterinaria.setVeterinarios(new ArrayList<>());
                    veterinaria.getVeterinarios().add(veterinario.get());
                    veterinaria.setProductos(null);
                    veterinaria.setRegistro(null);
                    List<Usuario> usuarios = veterinaria.getUsuarios();
                    //(Long id, String nombre, String direccion, List<Mascota> mascotas
                    usuarios = usuarios.stream().map(usuario1 -> new Usuario(usuario1.getId(),
                            usuario1.getNombre(),usuario1.getDireccion(), usuario1.getMascotas())).toList();
                    veterinaria.setUsuarios(usuarios);

                    json = ConversorJSON.convertirObjetoAJSON(veterinaria);
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                break;
            case "3":
                Optional<Veterinaria> veterinaria = service.loginVeterinaria(email, password);
                if (veterinaria.isEmpty()) {
                    ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
                    return;
                } else {
                    json = ConversorJSON.convertirObjetoAJSON(veterinaria.get());
                    resp.setStatus(HttpServletResponse.SC_OK);
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
