-- INSERTS USUARIOS
INSERT INTO usuarios VALUES (1, 'Juan Perez', '3001112233', '123456789', 'juan@email.com', 'Cliente');
INSERT INTO usuarios VALUES (2, 'Ana Gomez', '3002223344', '987654321', 'ana@email.com', 'Conductor');
INSERT INTO usuarios VALUES (3, 'Carlos Ruiz', '3003334455', '456789123', 'carlos@email.com', 'Cliente');
INSERT INTO usuarios VALUES (4, 'Maria Lopez', '3004445566', '789123456', 'maria@email.com', 'Conductor');
INSERT INTO usuarios VALUES (5, 'Luis Torres', '3005556677', '321654987', 'luis@email.com', 'Cliente');

-- INSERTS VEHICULOS
INSERT INTO vehiculos VALUES (1, 'Carro', 'Toyota', 'Corolla', 'Rojo', 'ABC123', 'Bogota', 4);
INSERT INTO vehiculos VALUES (2, 'Moto', 'Yamaha', 'FZ', 'Negro', 'XYZ789', 'Medellin', 2);
INSERT INTO vehiculos VALUES (3, 'Carro', 'Mazda', '3', 'Azul', 'DEF456', 'Cali', 4);
INSERT INTO vehiculos VALUES (4, 'Carro', 'Renault', 'Logan', 'Blanco', 'GHI321', 'Barranquilla', 5);
INSERT INTO vehiculos VALUES (5, 'Moto', 'Honda', 'CBR', 'Rojo', 'JKL654', 'Bogota', 2);

-- INSERTS NIVELES_VEHICULO
INSERT INTO niveles_vehiculo VALUES ('Corolla', 4, 'Estandar');
INSERT INTO niveles_vehiculo VALUES ('FZ', 2, 'Estandar');
INSERT INTO niveles_vehiculo VALUES ('3', 4, 'Confort');
INSERT INTO niveles_vehiculo VALUES ('Logan', 5, 'Large');
INSERT INTO niveles_vehiculo VALUES ('CBR', 2, 'Confort');

-- INSERTS CONDUCTOR_VEHICULOS
INSERT INTO conductor_vehiculos VALUES (2, 1);
INSERT INTO conductor_vehiculos VALUES (4, 3);
INSERT INTO conductor_vehiculos VALUES (2, 2);
INSERT INTO conductor_vehiculos VALUES (4, 4);
INSERT INTO conductor_vehiculos VALUES (2, 5);

-- INSERTS TARJETAS_CREDITO
INSERT INTO tarjetas_credito VALUES (1, 'Juan Perez', '1111222233334444', TO_DATE('2027-12-31','YYYY-MM-DD'), 123, 1);
INSERT INTO tarjetas_credito VALUES (2, 'Ana Gomez', '2222333344445555', TO_DATE('2026-11-30','YYYY-MM-DD'), 234, 2);
INSERT INTO tarjetas_credito VALUES (3, 'Carlos Ruiz', '3333444455556666', TO_DATE('2028-10-31','YYYY-MM-DD'), 345, 3);
INSERT INTO tarjetas_credito VALUES (4, 'Maria Lopez', '4444555566667777', TO_DATE('2025-09-30','YYYY-MM-DD'), 456, 4);
INSERT INTO tarjetas_credito VALUES (5, 'Luis Torres', '5555666677778888', TO_DATE('2029-08-31','YYYY-MM-DD'), 567, 5);

-- INSERTS FRANJAS_HORARIAS
INSERT INTO franjas_horarias VALUES (1, 'Lunes', '08:00', '10:00', 'Transporte Pasajeros');
INSERT INTO franjas_horarias VALUES (2, 'Martes', '10:00', '12:00', 'Domicilio Comida');
INSERT INTO franjas_horarias VALUES (3, 'Miercoles', '12:00', '14:00', 'Transporte Paquete');
INSERT INTO franjas_horarias VALUES (4, 'Jueves', '14:00', '16:00', 'Transporte Pasajeros');
INSERT INTO franjas_horarias VALUES (5, 'Viernes', '16:00', '18:00', 'Domicilio Comida');

-- INSERTS DISPONIBILIDADES
INSERT INTO disponibilidades VALUES (1, 1, 'Y');
INSERT INTO disponibilidades VALUES (2, 2, 'N');
INSERT INTO disponibilidades VALUES (3, 3, 'Y');
INSERT INTO disponibilidades VALUES (4, 4, 'Y');
INSERT INTO disponibilidades VALUES (5, 5, 'N');

-- INSERTS CIUDADES
INSERT INTO ciudades VALUES (1, 'Bogota');
INSERT INTO ciudades VALUES (2, 'Medellin');
INSERT INTO ciudades VALUES (3, 'Cali');
INSERT INTO ciudades VALUES (4, 'Barranquilla');
INSERT INTO ciudades VALUES (5, 'Cartagena');

-- INSERTS PUNTOS_GEOGRAFICOS
INSERT INTO puntos_geograficos VALUES (1, 'Centro', 4.60971, -74.08175, 'Cra 7 # 12-34', 1);
INSERT INTO puntos_geograficos VALUES (2, 'Sur', 6.25184, -75.56359, 'Cll 10 # 20-30', 2);
INSERT INTO puntos_geograficos VALUES (3, 'Norte', 3.45164, -76.53198, 'Av 3N # 45-67', 3);
INSERT INTO puntos_geograficos VALUES (4, 'Oeste', 10.96854, -74.78132, 'Cll 50 # 60-70', 4);
INSERT INTO puntos_geograficos VALUES (5, 'Este', 10.39972, -75.51444, 'Cll 100 # 110-120', 5);

-- INSERTS TARIFAS
INSERT INTO tarifas VALUES (1, 'Transporte Pasajeros', 'Estandar', 2500, TO_DATE('2025-01-01','YYYY-MM-DD'), TO_DATE('2025-12-31','YYYY-MM-DD'));
INSERT INTO tarifas VALUES (2, 'Domicilio Comida', 'Confort', 3000, TO_DATE('2025-02-01','YYYY-MM-DD'), TO_DATE('2025-12-31','YYYY-MM-DD'));
INSERT INTO tarifas VALUES (3, 'Transporte Paquete', 'Large', 3500, TO_DATE('2025-03-01','YYYY-MM-DD'), TO_DATE('2025-12-31','YYYY-MM-DD'));
INSERT INTO tarifas VALUES (4, 'Transporte Pasajeros', 'Confort', 2800, TO_DATE('2025-04-01','YYYY-MM-DD'), TO_DATE('2025-12-31','YYYY-MM-DD'));
INSERT INTO tarifas VALUES (5, 'Domicilio Comida', 'Estandar', 2200, TO_DATE('2025-05-01','YYYY-MM-DD'), TO_DATE('2025-12-31','YYYY-MM-DD'));

-- INSERTS SERVICIOS
INSERT INTO servicios VALUES (1, 1, TO_TIMESTAMP('2025-09-29 08:00:00','YYYY-MM-DD HH24:MI:SS'), 'Transporte Pasajeros', 'Estandar', 'Pendiente', 1, NULL, 1);
INSERT INTO servicios VALUES (2, 3, TO_TIMESTAMP('2025-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'), 'Domicilio Comida', 'Confort', 'Asignado', 2, 'Restaurante ABC', 2);
INSERT INTO servicios VALUES (3, 5, TO_TIMESTAMP('2025-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'), 'Transporte Paquete', 'Large', 'Cancelado', 3, NULL, 3);
INSERT INTO servicios VALUES (4, 1, TO_TIMESTAMP('2025-09-29 11:00:00','YYYY-MM-DD HH24:MI:SS'), 'Transporte Pasajeros', 'Confort', 'Pendiente', 4, NULL, 4);
INSERT INTO servicios VALUES (5, 3, TO_TIMESTAMP('2025-09-29 12:00:00','YYYY-MM-DD HH24:MI:SS'), 'Domicilio Comida', 'Estandar', 'Asignado', 5, 'Restaurante XYZ', 5);

-- INSERTS SERVICIOS_DESTINOS
INSERT INTO servicios_destinos VALUES (1, 1, 2);
INSERT INTO servicios_destinos VALUES (2, 2, 3);
INSERT INTO servicios_destinos VALUES (3, 3, 4);
INSERT INTO servicios_destinos VALUES (4, 4, 5);
INSERT INTO servicios_destinos VALUES (5, 5, 1);

-- INSERTS VIAJES
INSERT INTO viajes VALUES (1, TO_TIMESTAMP('2025-09-29 08:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-29 08:30:00','YYYY-MM-DD HH24:MI:SS'), 12.5, 1, 2, 1);
INSERT INTO viajes VALUES (2, TO_TIMESTAMP('2025-09-29 09:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-29 09:45:00','YYYY-MM-DD HH24:MI:SS'), 8.0, 2, 4, 3);
INSERT INTO viajes VALUES (3, TO_TIMESTAMP('2025-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-29 10:20:00','YYYY-MM-DD HH24:MI:SS'), 5.5, 3, 2, 5);
INSERT INTO viajes VALUES (4, TO_TIMESTAMP('2025-09-29 11:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-29 11:40:00','YYYY-MM-DD HH24:MI:SS'), 15.0, 4, 4, 4);
INSERT INTO viajes VALUES (5, TO_TIMESTAMP('2025-09-29 12:00:00','YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-09-29 12:25:00','YYYY-MM-DD HH24:MI:SS'), 7.2, 5, 2, 2);

-- INSERTS REVIEWS
INSERT INTO reviews VALUES (1, 1, 2, 5, 'Excelente servicio', TO_TIMESTAMP('2025-09-29 08:35:00','YYYY-MM-DD HH24:MI:SS'), 1);
INSERT INTO reviews VALUES (2, 3, 4, 4, 'Buen viaje', TO_TIMESTAMP('2025-09-29 09:50:00','YYYY-MM-DD HH24:MI:SS'), 2);
INSERT INTO reviews VALUES (3, 5, 2, 3, 'Regular', TO_TIMESTAMP('2025-09-29 10:25:00','YYYY-MM-DD HH24:MI:SS'), 3);
INSERT INTO reviews VALUES (4, 1, 4, 5, 'Muy amable', TO_TIMESTAMP('2025-09-29 11:45:00','YYYY-MM-DD HH24:MI:SS'), 4);
INSERT INTO reviews VALUES (5, 3, 2, 2, 'Demorado', TO_TIMESTAMP('2025-09-29 12:30:00','YYYY-MM-DD HH24:MI:SS'), 5);
