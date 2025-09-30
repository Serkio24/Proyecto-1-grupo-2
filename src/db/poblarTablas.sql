-- BORRAR DATOS DE TODAS LAS TABLAS
DELETE FROM reviews;
DELETE FROM viajes;
DELETE FROM servicios_destinos;
DELETE FROM servicios;
DELETE FROM disponibilidades;
DELETE FROM franjas_horarias;
DELETE FROM tarifas;
DELETE FROM conductor_vehiculos;
DELETE FROM vehiculos;
DELETE FROM tarjetas_credito;
DELETE FROM puntos_geograficos;
DELETE FROM ciudades;
DELETE FROM usuarios;
COMMIT;

-- USUARIOS
BEGIN
    -- 150 conductores
    FOR i IN 1..150 LOOP
        INSERT INTO usuarios(idUsuario, nombre, numeroCelular, numeroCedula, correoElectronico, tipoUsuario)
        VALUES (i, 'Conductor_' || i, '30000000' || LPAD(i,3,'0'), 'CC' || LPAD(i,6,'0'), 'conductor_' || i || '@mail.com', 'Conductor');
    END LOOP;

    -- 250 pasajeros
    FOR i IN 151..400 LOOP
        INSERT INTO usuarios(idUsuario, nombre, numeroCelular, numeroCedula, correoElectronico, tipoUsuario)
        VALUES (i, 'Pasajero_' || i, '31100000' || LPAD(i,3,'0'), 'CC' || LPAD(i,6,'0'), 'pasajero_' || i || '@mail.com', 'Cliente');
    END LOOP;
END;
/
COMMIT;

-- VEHÍCULOS
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO vehiculos(idVehiculo, tipo, marca, modelo, color, placa, ciudadExpedicion, capacidadPasajeros)
        VALUES (i, 'Carro', 'Marca_' || MOD(i,10), 'Modelo_' || i, 'Color_' || MOD(i,5), 'PLA' || LPAD(i,4,'0'), 'Ciudad_' || MOD(i,10), 4 + MOD(i,3));
    END LOOP;
END;
/
COMMIT;

-- ASIGNACIÓN VEHÍCULOS A CONDUCTORES
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO conductor_vehiculos(idConductor, idVehiculo)
        VALUES (i, i);
    END LOOP;
END;
/
COMMIT;

-- CIUDADES
BEGIN
    FOR i IN 1..20 LOOP
        INSERT INTO ciudades(idCiudad, nombre) VALUES (i, 'Ciudad_' || i);
    END LOOP;
END;
/
COMMIT;

-- PUNTOS GEOGRÁFICOS
BEGIN
    FOR i IN 1..100 LOOP
        INSERT INTO puntos_geograficos(idPunto, nombre, latitud, longitud, direccionAproximada, idCiudad)
        VALUES (i, 'Punto_' || i, 4.6 + i/100, -74 + i/100, 'Calle ' || i, MOD(i,20)+1);
    END LOOP;
END;
/
COMMIT;

-- TARIFAS
BEGIN
    FOR i IN 1..3 LOOP
        INSERT INTO tarifas(idTarifa, tipoServicio, nivel, precioPorKm, vigenciaDesde, vigenciaHasta)
        VALUES (i, 'Transporte Pasajeros', CASE WHEN i=1 THEN 'Estandar' WHEN i=2 THEN 'Confort' ELSE 'Large' END,
                300 + i*50, DATE '2025-01-01', DATE '2025-12-31');
    END LOOP;
END;
/
COMMIT;

-- FRANJAS HORARIAS
BEGIN
    FOR i IN 1..21 LOOP
        INSERT INTO franjas_horarias(idFranja, diaSemana, horaInicio, horaFin, tipoServicio)
        VALUES (i, CASE MOD(i-1,7) WHEN 0 THEN 'Lunes' WHEN 1 THEN 'Martes' WHEN 2 THEN 'Miércoles'
                                   WHEN 3 THEN 'Jueves' WHEN 4 THEN 'Viernes' WHEN 5 THEN 'Sábado' ELSE 'Domingo' END,
                '08:00', '12:00', 'Transporte Pasajeros');
    END LOOP;
END;
/
COMMIT;

-- DISPONIBILIDADES DE VEHÍCULOS
BEGIN
    FOR v IN 1..150 LOOP
        FOR f IN 1..21 LOOP
            INSERT INTO disponibilidades(idVehiculo, idFranja, disponible)
            VALUES (v, f, 'Y');
        END LOOP;
    END LOOP;
END;
/
COMMIT;

-- SERVICIOS Y VIAJES
DECLARE
    v_servicio_id NUMBER := 1;
    v_viaje_id NUMBER := 1;
BEGIN
    FOR pasajero_id IN 151..400 LOOP
        FOR j IN 1..5 LOOP
            INSERT INTO servicios(idServicio, idCliente, fechaHora, tipoServicio, nivelRequerido, estado, idPuntoPartida)
            VALUES (v_servicio_id, pasajero_id, SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100), 'Transporte Pasajeros', 'Estandar', 'Pendiente', MOD(pasajero_id,100)+1);

            INSERT INTO viajes(idViaje, fechaHoraInicio, fechaHoraFin, longitudTrayecto, idServicio, idConductor, idVehiculo)
            VALUES (v_viaje_id, SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100), SYSTIMESTAMP - DBMS_RANDOM.VALUE(0,99),
                    DBMS_RANDOM.VALUE(2,20), v_servicio_id, MOD(v_viaje_id,150)+1, MOD(v_viaje_id,150)+1);

            INSERT INTO servicios_destinos(idDestino, idServicio, idPuntoLlegada)
            VALUES (v_servicio_id, v_servicio_id, MOD(pasajero_id,100)+1);

            v_servicio_id := v_servicio_id + 1;
            v_viaje_id := v_viaje_id + 1;
        END LOOP;
    END LOOP;
END;
/
COMMIT;

-- REVIEWS
DECLARE
    v_review_id NUMBER := 1;
BEGIN
    FOR viaje_id IN 1..1250 LOOP
        IF MOD(viaje_id,3)=0 THEN
            INSERT INTO reviews(idRevision, idUsuarioCalificador, idUsuarioCalificado, puntuacion, comentario, fecha, idViaje)
            VALUES (v_review_id, MOD(viaje_id,250)+151, MOD(viaje_id,150)+1, MOD(viaje_id,5)+1, 'Buen viaje', SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100), viaje_id);
            v_review_id := v_review_id + 1;
        END IF;
    END LOOP;
END;
/
COMMIT;

-- TARJETAS DE CRÉDITO
BEGIN
    FOR cliente_id IN 151..400 LOOP
        INSERT INTO tarjetas_credito(idTarjetaCredito, titularDeLaTarjeta, numeroTarjeta, fechaExpiracion, codigoSeguridad, clienteId)
        VALUES (cliente_id, 'Pasajero_' || cliente_id, '400000000000' || LPAD(cliente_id,4,'0'), ADD_MONTHS(SYSDATE, 12*3), MOD(cliente_id,999)+100, cliente_id);
    END LOOP;
END;
/
COMMIT;
