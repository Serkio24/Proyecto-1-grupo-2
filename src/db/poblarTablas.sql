
-- Borrar datos
BEGIN
    EXECUTE IMMEDIATE 'DELETE FROM reviews';
    EXECUTE IMMEDIATE 'DELETE FROM viajes';
    EXECUTE IMMEDIATE 'DELETE FROM servicios_destinos';
    EXECUTE IMMEDIATE 'DELETE FROM servicios';
    EXECUTE IMMEDIATE 'DELETE FROM disponibilidades';
    EXECUTE IMMEDIATE 'DELETE FROM franjas_horarias';
    EXECUTE IMMEDIATE 'DELETE FROM tarifas';
    EXECUTE IMMEDIATE 'DELETE FROM conductor_vehiculos';
    EXECUTE IMMEDIATE 'DELETE FROM vehiculos';
    EXECUTE IMMEDIATE 'DELETE FROM tarjetas_credito';
    EXECUTE IMMEDIATE 'DELETE FROM puntos_geograficos';
    EXECUTE IMMEDIATE 'DELETE FROM ciudades';
    EXECUTE IMMEDIATE 'DELETE FROM usuarios';
    COMMIT;
END;
/


-- Usuarios
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO usuarios(idUsuario, nombre, numeroCelular, numeroCedula, correoElectronico, tipoUsuario)
        VALUES (usuarios_SEQ.NEXTVAL, 'Conductor_' || i, '30000000' || LPAD(i,3,'0'), 'CC' || LPAD(i,6,'0'), 'conductor_' || i || '@mail.com', 'Conductor');
    END LOOP;

    FOR i IN 1..250 LOOP
        INSERT INTO usuarios(idUsuario, nombre, numeroCelular, numeroCedula, correoElectronico, tipoUsuario)
        VALUES (usuarios_SEQ.NEXTVAL, 'Pasajero_' || i, '31100000' || LPAD(i,3,'0'), 'CC' || LPAD(i,6,'0'), 'pasajero_' || i || '@mail.com', 'Cliente');
    END LOOP;
END;
/
COMMIT;

-- Ciudades
BEGIN
    FOR i IN 1..20 LOOP
        INSERT INTO ciudades(idCiudad, nombre) VALUES (ciudades_SEQ.NEXTVAL, 'Ciudad_' || i);
    END LOOP;
END;
/
COMMIT;

-- Vehículos
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO vehiculos(idVehiculo, tipo, marca, modelo, color, placa, ciudadExpedicion, capacidadPasajeros)
        VALUES (vehiculos_SEQ.NEXTVAL, 'Carro', 'Marca_' || MOD(i,10), 'Modelo_' || i, 'Color_' || MOD(i,5), 'PLA' || LPAD(i,4,'0'), 'Ciudad_' || MOD(i,10), 4 + MOD(i,3));
    END LOOP;
END;
/
COMMIT;

-- ConductorVehiculos
BEGIN
    FOR i IN 1..150 LOOP
        INSERT INTO conductor_vehiculos(idConductor, idVehiculo)
        VALUES (i, i); -- Se asume correlación 1:1
    END LOOP;
END;
/
COMMIT;

-- Puntos Geográficos
BEGIN
    FOR i IN 1..100 LOOP
        INSERT INTO puntos_geograficos(idPunto, nombre, latitud, longitud, direccionAproximada, idCiudad)
        VALUES (puntos_geograficos_SEQ.NEXTVAL, 'Punto_' || i, 4.6 + i/100, -74 + i/100, 'Calle ' || i, MOD(i,20)+1);
    END LOOP;
END;
/
COMMIT;

-- Franjas Horarias
BEGIN
    FOR i IN 1..21 LOOP
        INSERT INTO franjas_horarias(idFranja, diaSemana, horaInicio, horaFin, tipoServicio)
        VALUES (franjas_horarias_SEQ.NEXTVAL, 
                CASE MOD(i-1,7)
                     WHEN 0 THEN 'Lunes'
                     WHEN 1 THEN 'Martes'
                     WHEN 2 THEN 'Miércoles'
                     WHEN 3 THEN 'Jueves'
                     WHEN 4 THEN 'Viernes'
                     WHEN 5 THEN 'Sábado'
                     ELSE 'Domingo'
                END,
                '08:00', '12:00', 'Transporte Pasajeros');
    END LOOP;
END;
/
COMMIT;

-- Disponibilidades
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

-- Tarifas
BEGIN
    FOR i IN 1..3 LOOP
        INSERT INTO tarifas(idTarifa, tipoServicio, nivel, precioPorKm, vigenciaDesde, vigenciaHasta)
        VALUES (tarifas_SEQ.NEXTVAL, 'Transporte Pasajeros', 
                CASE WHEN i=1 THEN 'Estandar' WHEN i=2 THEN 'Confort' ELSE 'Large' END,
                300 + i*50, DATE '2025-01-01', DATE '2025-12-31');
    END LOOP;
END;
/
COMMIT;

-- Servicios y Viajes con idTarifa y costo
DECLARE
    v_servicio_id NUMBER;
    v_viaje_id NUMBER;
    v_tarifa_id NUMBER;
    v_precio_por_km NUMBER;
    v_costo NUMBER;
BEGIN
    FOR pasajero_id IN 151..400 LOOP
        FOR j IN 1..5 LOOP
            -- Generar IDs de secuencia
            v_servicio_id := servicios_SEQ.NEXTVAL;
            v_viaje_id := viajes_SEQ.NEXTVAL;

            -- Insertar servicio
            INSERT INTO servicios(idServicio, idCliente, fechaHora, tipoServicio, nivelRequerido, estado, idPuntoPartida)
            VALUES (
                v_servicio_id,
                pasajero_id,
                SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100),
                'Transporte Pasajeros',
                'Estandar',
                'Pendiente',
                MOD(pasajero_id,100)+1
            );

            -- Obtener tarifa válida usando TIMESTAMP
            SELECT idTarifa, precioPorKm INTO v_tarifa_id, v_precio_por_km
            FROM tarifas
            WHERE tipoServicio = 'Transporte Pasajeros'
              AND nivel = 'Estandar'
              AND SYSTIMESTAMP BETWEEN vigenciaDesde AND vigenciaHasta
            FETCH FIRST 1 ROWS ONLY;

            -- Calcular longitud aleatoria y costo
            v_costo := v_precio_por_km * DBMS_RANDOM.VALUE(2,20);

            -- Insertar viaje con idTarifa y costo
            INSERT INTO viajes(idViaje, fechaHoraInicio, fechaHoraFin, longitudTrayecto, idServicio, idConductor, idVehiculo, idTarifa, costo)
            VALUES (
                v_viaje_id,
                SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100),
                SYSTIMESTAMP - DBMS_RANDOM.VALUE(0,99),
                v_costo / v_precio_por_km, -- longitudTrayecto = costo / precioPorKm
                v_servicio_id,
                MOD(v_viaje_id,150)+1,
                MOD(v_viaje_id,150)+1,
                v_tarifa_id,
                v_costo
            );

            -- Insertar destino
            INSERT INTO servicios_destinos(idDestino, idServicio, idPuntoLlegada)
            VALUES (servicios_destinos_SEQ.NEXTVAL, v_servicio_id, MOD(pasajero_id,100)+1);

        END LOOP;
    END LOOP;
END;
/
COMMIT;


-- Reviews
DECLARE
    v_review_id NUMBER;
BEGIN
    FOR viaje_id IN 1..1250 LOOP
        IF MOD(viaje_id,3)=0 THEN
            v_review_id := reviews_SEQ.NEXTVAL;
            INSERT INTO reviews(idRevision, idUsuarioCalificador, idUsuarioCalificado, puntuacion, comentario, fecha, idViaje)
            VALUES (v_review_id, MOD(viaje_id,250)+151, MOD(viaje_id,150)+1, MOD(viaje_id,5)+1, 'Buen viaje', SYSTIMESTAMP - DBMS_RANDOM.VALUE(1,100), viaje_id);
        END IF;
    END LOOP;
END;
/
COMMIT;

-- Tarjetas de Crédito
BEGIN
    FOR cliente_id IN 151..400 LOOP
        INSERT INTO tarjetas_credito(idTarjetaCredito, titularDeLaTarjeta, numeroTarjeta, fechaExpiracion, codigoSeguridad, clienteId)
        VALUES (tarjetas_credito_SEQ.NEXTVAL, 'Pasajero_' || cliente_id, '400000000000' || LPAD(cliente_id,4,'0'), ADD_MONTHS(SYSDATE, 12*3), MOD(cliente_id,999)+100, cliente_id);
    END LOOP;
END;
/
COMMIT;
