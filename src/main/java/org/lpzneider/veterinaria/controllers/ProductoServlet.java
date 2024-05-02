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
import org.lpzneider.veterinaria.models.Producto;
import org.lpzneider.veterinaria.models.Raza;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.util.ConversorJSON;
import org.lpzneider.veterinaria.util.ManejadorErrores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
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
                List<Producto> producto = service.readProducto();

                if (!producto.isEmpty()) {
                    json = ConversorJSON.convertirObjetoAJSON(producto);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                Optional<Producto> producto = service.getByIdProducto(id);
                if(producto.isPresent()) {
                    json = ConversorJSON.convertirObjetoAJSON(producto.get());
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


        String nombre = req.getParameter("nombre");
        String cantidadStr = req.getParameter("cantidad");
        String precioStr = req.getParameter("precio");
        String idVeterinariaStr = req.getParameter("idVeterinaria");


        if (nombre == null || nombre.isEmpty() || cantidadStr == null || cantidadStr.isEmpty()
                || idVeterinariaStr == null || idVeterinariaStr.isEmpty() || precioStr == null || precioStr.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Integer cantidad;
        Integer precio;
        Long idVeterinaria;

        try {
            cantidad = Integer.valueOf(cantidadStr);
            idVeterinaria = Long.valueOf(idVeterinariaStr);
            precio = Integer.valueOf(precioStr);
        } catch (NumberFormatException e) {
            ManejadorErrores.enviarErrorInterno(resp);
            return;
        }

        Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(idVeterinaria);
        if (veterinaria.isEmpty()){
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_NO_CONTENT, "veterinaria no encontrada");
            return;
        }
        Producto producto = new Producto(nombre, cantidad,precio, veterinaria.get());
        service.saveOrEditProducto(producto);
        String json;
        try {
            json = ConversorJSON.convertirObjetoAJSON(veterinaria.get().getProductos());
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
            throw new ServiceJpaException("Error al convertir el ID de producto a número", e);
        }

        Optional<Producto> productoOptional = service.getByIdProducto(id);
        if (productoOptional.isEmpty()) {
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
            return;
        }

        Producto producto = productoOptional.get();

        String nombre = req.getParameter("nombre");
        String cantidadStr = req.getParameter("cantidad");
        String precioStr = req.getParameter("precio");
        String idVeterinariaStr = req.getParameter("idVeterinaria");

        nombre = (nombre == null) ? producto.getNombre() : nombre;
        Integer cantidad = (cantidadStr == null) ? producto.getCantidad() : Integer.valueOf(cantidadStr);
        Integer precio = (precioStr == null) ? producto.getPrecio() : Integer.valueOf(precioStr);
        Long idVeterinaria = (idVeterinariaStr == null) ? producto.getVeterinaria().getId() : Long.valueOf(idVeterinariaStr);


        Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(idVeterinaria);
        if (veterinaria.isEmpty()){
            ManejadorErrores.enviarError(resp, HttpServletResponse.SC_NO_CONTENT, "veterinaria no encontrada");
            return;
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setVeterinaria(veterinaria.get());


        service.saveOrEditProducto(producto);

        try {
            String json = ConversorJSON.convertirObjetoAJSON(veterinaria.get().getProductos() );
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
            Long idVeterinaria = Long.valueOf(req.getParameter("idVeterinaria"));
            Long id = Long.valueOf(req.getParameter("id"));

            Optional<Producto> producto = service.getByIdProducto(id);
            Optional<Veterinaria> veterinaria = service.getByIdVeterinaria(idVeterinaria);
            if (producto.isPresent() && veterinaria.isPresent()) {
                service.deleteProducto(id);
                json = ConversorJSON.convertirObjetoAJSON(veterinaria.get().getProductos());
                resp.getWriter().write(json);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                json = ConversorJSON.convertirObjetoAJSON(Collections.singletonMap("error", "El producto con el ID " + id + " no existe" + idVeterinaria));
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
