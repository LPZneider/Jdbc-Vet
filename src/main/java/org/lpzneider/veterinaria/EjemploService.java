package org.lpzneider.veterinaria;

import org.lpzneider.veterinaria.models.Mascota;
import org.lpzneider.veterinaria.service.Service;
import org.lpzneider.veterinaria.service.ServiceVeterinaria;

import java.sql.SQLException;
import java.util.Date;

public class EjemploService {
    public static void main(String[] args) throws SQLException {
        Service service = new ServiceVeterinaria();
        System.out.println("================= listar Mascota ======================");
        service.readMascota().forEach(mascota -> System.out.println(mascota.getNombre()));
        System.out.println("================= Guardando Mascota ======================");
        service.saveOrEditMascota(new Mascota("mechas",1L,new Date(),1L));
        service.readMascota().forEach(mascota -> System.out.println(mascota.getNombre()));
        System.out.println("================= editar Mascota ======================");
        service.saveOrEditMascota(new Mascota(3L,"mechas",1L,new Date(),1L));
        service.readMascota().forEach(mascota -> System.out.println(mascota.getNombre()));
        System.out.println("================= eliminar Mascota ======================");
        service.deleteMascota(3L);
        service.readMascota().forEach(mascota -> System.out.println(mascota.getNombre()));




    }
}
