-- Insertar datos para la tabla de razas con IDs personalizados
INSERT INTO razas (id, nombre, tamanio, altura, peso) VALUES (1, 'Mestizo', 'Mediano', 50, 25);
INSERT INTO razas (id, nombre, tamanio, altura, peso) VALUES (2, 'Pastor Holandés', 'Mediano-Grande', 62, 30);

-- Insertar datos para la tabla de usuarios con IDs personalizados
INSERT INTO usuarios (id, nombre, direccion) VALUES (1, 'Neider Alexis Lopez', 'Calle A S 5 SD');
INSERT INTO usuarios (id, nombre, direccion) VALUES (2, 'Steven Lopez', 'Calle 5 AS 23 12');

-- Insertar datos para la tabla de veterinarias con IDs personalizados
INSERT INTO veterinarias (id, nombre, direccion) VALUES (1, 'Veterinaria Patitas', 'Calle 4 A Sur 81-54');
INSERT INTO veterinarias (id, nombre, direccion) VALUES (2, 'Clínica Veterinaria Peluditos', 'Calle 4 A Sur 20-89');

-- Insertar datos para la tabla de veterinarios con IDs personalizados
INSERT INTO veterinarios (id, nombre, id_veterinaria) VALUES (1, 'Neider Alexis Lopez Mancera', 1);
INSERT INTO veterinarios (id, nombre, id_veterinaria) VALUES (2, 'Alex Pereira', 1);
INSERT INTO veterinarios (id, nombre, id_veterinaria) VALUES (3, 'Jon Mircha', 2);

-- Insertar datos para la tabla de la relación entre usuarios y veterinarias con IDs personalizados
INSERT INTO tbl_usuarios_veterinarias (usuario_id, veterinaria_id) VALUES (1, 1);
INSERT INTO tbl_usuarios_veterinarias (usuario_id, veterinaria_id) VALUES (2, 1);
INSERT INTO tbl_usuarios_veterinarias (usuario_id, veterinaria_id) VALUES (2, 2);

-- Insertar datos para la tabla de mascotas con IDs personalizados
INSERT INTO mascotas (id, nombre, id_raza, fecha_de_nacimiento, id_propietario) VALUES (1, 'Mara', 1, '2020-12-16', 1);
INSERT INTO mascotas (id, nombre, id_raza, fecha_de_nacimiento, id_propietario) VALUES (2, 'Luna', 1, '2021-01-16', 2);

