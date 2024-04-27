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
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet("/veterinaria-usuario")
public class VeterinariaUsuarioServlet extends HttpServlet {
    @Inject
    @ServicePrincipal
    private Service service;


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idUsuario = null;
        Long idVeterinaria = null;
        Integer isRemove = null;
        try {
            isRemove = Integer.valueOf(req.getParameter("isRemove"));
        } catch (Exception e) {
            isRemove = 0;
        }
        try {
            idUsuario = Long.valueOf(req.getParameter("idUsuario"));
            idVeterinaria = Long.valueOf(req.getParameter("idVeterinaria"));

        } catch (NumberFormatException e) {
            throw new ServiceJpaException("Error al convertir el ID de veterinaria a número", e);
        }

        Optional<Veterinaria> veterinariaOptional = service.getByIdVeterinaria(idVeterinaria);
        Optional<Usuario> usuarioOptional = service.getByIdUsuario(idUsuario);
        if (veterinariaOptional.isEmpty() || usuarioOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Veterinaria veterinaria = veterinariaOptional.get();
        Usuario usuario = usuarioOptional.get();

        if (isRemove > 0) {
            veterinaria.removeUsuario(usuario);
        } else {
            veterinaria.addUsuario(usuario);

        }

        service.saveOrEditVeterinaria(veterinaria);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(veterinaria);
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

}
