package org.lpzneider.veterinaria.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
@ApplicationScoped
public class ManejadorErrores {
    public static void enviarError(HttpServletResponse resp, int codigo, String mensaje) throws IOException {
        resp.setStatus(codigo);
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Collections.singletonMap("error", mensaje));
        resp.getWriter().write(json);
    }

    public static void enviarErrorInterno(HttpServletResponse resp) throws IOException {
        enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }
}
