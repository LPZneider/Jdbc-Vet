package org.lpzneider.veterinaria;

import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.service.ServiceVeterinaria;

import java.sql.SQLException;

public class EjemploService {
    public static void main(String[] args) throws SQLException {
        Service service = new ServiceVeterinaria();
        service.readMascota().forEach(mascota -> System.out.println(mascota.getNombre()));
        service.readUsuario().forEach(usuario -> System.out.println(usuario.getNombre()));
    }
}
