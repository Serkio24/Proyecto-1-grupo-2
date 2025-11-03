-- BORRAR TABLAS SI EXISTEN
DROP TABLE reviews CASCADE CONSTRAINTS;
DROP TABLE conductor_vehiculos CASCADE CONSTRAINTS;
DROP TABLE tarjetas_credito CASCADE CONSTRAINTS;
DROP TABLE vehiculos CASCADE CONSTRAINTS;
DROP TABLE niveles_vehiculo CASCADE CONSTRAINTS;
DROP TABLE franjas_horarias CASCADE CONSTRAINTS;
DROP TABLE disponibilidades CASCADE CONSTRAINTS;
DROP TABLE puntos_geograficos CASCADE CONSTRAINTS;
DROP TABLE ciudades CASCADE CONSTRAINTS;
DROP TABLE servicios_destinos CASCADE CONSTRAINTS;
DROP TABLE servicios CASCADE CONSTRAINTS;
DROP TABLE tarifas CASCADE CONSTRAINTS;
DROP TABLE usuarios CASCADE CONSTRAINTS;
DROP TABLE viajes CASCADE CONSTRAINTS;

-- TABLA USUARIOS
CREATE TABLE usuarios (
    idUsuario NUMBER NOT NULL,
    nombre VARCHAR2(100 BYTE) NOT NULL,
    numeroCelular VARCHAR2(20 BYTE) NOT NULL,
    numeroCedula VARCHAR2(20 BYTE) NOT NULL,
    correoElectronico VARCHAR2(100 BYTE) NOT NULL,
    tipoUsuario VARCHAR2(20 BYTE) CHECK (tipoUsuario IN ('Cliente','Conductor')),
    CONSTRAINT usuarios_pk PRIMARY KEY (idUsuario)
);

-- TABLA VEHICULOS
CREATE TABLE vehiculos (
    idVehiculo NUMBER NOT NULL,
    tipo VARCHAR2(50 BYTE) NOT NULL,
    marca VARCHAR2(50 BYTE) NOT NULL,
    modelo VARCHAR2(50 BYTE) NOT NULL,
    color VARCHAR2(30 BYTE) NOT NULL,
    placa VARCHAR2(20 BYTE) NOT NULL,
    ciudadExpedicion VARCHAR2(50 BYTE) NOT NULL,
    capacidadPasajeros NUMBER NOT NULL,
    CONSTRAINT vehiculos_pk PRIMARY KEY (idVehiculo)
);

-- TABLA NIVELES_VEHICULO
CREATE TABLE niveles_vehiculo (
    modelo VARCHAR2(50 BYTE) NOT NULL,
    capacidadPasajeros NUMBER NOT NULL CHECK (capacidadPasajeros > 0),
    nivel VARCHAR2(20 BYTE) CHECK (nivel IN ('Estandar','Confort','Large')),
    CONSTRAINT niveles_vehiculo_pk PRIMARY KEY (modelo, capacidadPasajeros)
);


-- TABLA CONDUCTOR_VEHICULOS
CREATE TABLE conductor_vehiculos (
    idConductor NUMBER NOT NULL,
    idVehiculo NUMBER NOT NULL,
    CONSTRAINT conductor_vehiculos_pk PRIMARY KEY (idConductor, idVehiculo),
    CONSTRAINT conductor_vehiculos_fk1 FOREIGN KEY (idConductor) REFERENCES usuarios(idUsuario),
    CONSTRAINT conductor_vehiculos_fk2 FOREIGN KEY (idVehiculo) REFERENCES vehiculos(idVehiculo)
);

-- TABLA TARJETAS_CREDITO
CREATE TABLE tarjetas_credito (
    idTarjetaCredito NUMBER NOT NULL,
    titularDeLaTarjeta VARCHAR2(100 BYTE) NOT NULL,
    numeroTarjeta VARCHAR2(20 BYTE) NOT NULL,
    fechaExpiracion DATE NOT NULL,
    codigoSeguridad NUMBER NOT NULL,
    clienteId NUMBER NOT NULL,
    CONSTRAINT tarjetas_credito_pk PRIMARY KEY (idTarjetaCredito),
    CONSTRAINT tarjetas_credito_fk FOREIGN KEY (clienteId) REFERENCES usuarios(idUsuario)
);


-- TABLA FRANJAS_HORARIAS
CREATE TABLE franjas_horarias (
    idFranja NUMBER NOT NULL,
    diaSemana VARCHAR2(20 BYTE) NOT NULL,
    horaInicio VARCHAR2(10 BYTE) NOT NULL,
    horaFin VARCHAR2(10 BYTE) NOT NULL,
    tipoServicio VARCHAR2(50 BYTE) CHECK (tipoServicio IN ('Transporte Pasajeros', 'Domicilio Comida', 'Transporte Paquete')),
    CONSTRAINT franjas_horarias_pk PRIMARY KEY (idFranja)
);

-- TABLA DISPONIBILIDADES
CREATE TABLE disponibilidades (
    idVehiculo NUMBER NOT NULL,
    idFranja NUMBER NOT NULL,
    disponible CHAR(1) CHECK (disponible IN ('Y','N')),
    CONSTRAINT disponibilidades_pk PRIMARY KEY (idVehiculo, idFranja),
    CONSTRAINT disponibilidades_fk1 FOREIGN KEY (idVehiculo) REFERENCES vehiculos(idVehiculo),
    CONSTRAINT disponibilidades_fk2 FOREIGN KEY (idFranja) REFERENCES franjas_horarias(idFranja)
);

-- TABLA CIUDADES
CREATE TABLE ciudades (
    idCiudad NUMBER NOT NULL,
    nombre VARCHAR2(50 BYTE) NOT NULL,
    CONSTRAINT ciudades_pk PRIMARY KEY (idCiudad)
);

-- TABLA PUNTOS_GEOGRAFICOS
CREATE TABLE puntos_geograficos (
    idPunto NUMBER NOT NULL,
    nombre VARCHAR2(100 BYTE),
    latitud NUMBER NOT NULL,
    longitud NUMBER NOT NULL,
    direccionAproximada VARCHAR2(150 BYTE),
    idCiudad NUMBER NOT NULL,
    CONSTRAINT puntos_geograficos_pk PRIMARY KEY (idPunto),
    CONSTRAINT puntos_geograficos_fk FOREIGN KEY (idCiudad) REFERENCES ciudades(idCiudad)
);

-- TABLA SERVICIOS
CREATE TABLE servicios (
    idServicio NUMBER NOT NULL,
    idCliente NUMBER NOT NULL,
    fechaHora TIMESTAMP NOT NULL,
    tipoServicio VARCHAR2(50 BYTE) CHECK (tipoServicio IN ('Transporte Pasajeros','Domicilio Comida','Transporte Paquete')),
    nivelRequerido VARCHAR2(20 BYTE) CHECK (nivelRequerido IN ('Estandar','Confort','Large')),
    estado VARCHAR2(20 BYTE) CHECK (estado IN ('Pendiente','Asignado','Cancelado')),
    orden NUMBER,
    restaurante VARCHAR2(100 BYTE),
    idPuntoPartida NUMBER NOT NULL,
    CONSTRAINT servicios_pk PRIMARY KEY (idServicio),
    CONSTRAINT servicios_fk_cliente FOREIGN KEY (idCliente) REFERENCES usuarios(idUsuario),
    CONSTRAINT servicios_fk_punto FOREIGN KEY (idPuntoPartida) REFERENCES puntos_geograficos(idPunto)
);

-- TABLA SERVICIOS_DESTINOS
CREATE TABLE servicios_destinos (
    idDestino NUMBER NOT NULL,
    idServicio NUMBER NOT NULL,
    idPuntoLlegada NUMBER NOT NULL,
    CONSTRAINT servicios_destinos_pk PRIMARY KEY (idDestino),
    CONSTRAINT servicios_destinos_fk_servicio FOREIGN KEY (idServicio) REFERENCES servicios(idServicio),
    CONSTRAINT servicios_destinos_fk_punto FOREIGN KEY (idPuntoLlegada) REFERENCES puntos_geograficos(idPunto)
);

-- TABLA TARIFAS
CREATE TABLE tarifas (
    idTarifa NUMBER NOT NULL,
    tipoServicio VARCHAR2(50 BYTE) CHECK (tipoServicio IN ('Transporte Pasajeros','Domicilio Comida','Transporte Paquete')),
    nivel VARCHAR2(20 BYTE) CHECK (nivel IN ('Estandar','Confort','Large')),
    precioPorKm NUMBER NOT NULL CHECK (precioPorKm > 0),
    vigenciaDesde TIMESTAMP NOT NULL,
    vigenciaHasta TIMESTAMP NOT NULL,
    CONSTRAINT tarifas_pk PRIMARY KEY (idTarifa)
);


-- TABLA VIAJES
CREATE TABLE viajes (
    idViaje NUMBER NOT NULL,
    fechaHoraInicio TIMESTAMP NOT NULL,
    fechaHoraFin TIMESTAMP,
    longitudTrayecto NUMBER,
    idServicio NUMBER NOT NULL,
    idConductor NUMBER NOT NULL,
    idVehiculo NUMBER NOT NULL,
    idTarifa NUMBER, -- nuevo atributo: FK a tarifas
    costo NUMBER,    -- nuevo atributo: costo calculado
    CONSTRAINT viajes_pk PRIMARY KEY (idViaje),
    CONSTRAINT viajes_fk_servicio FOREIGN KEY (idServicio) REFERENCES servicios(idServicio),
    CONSTRAINT viajes_fk_conductor FOREIGN KEY (idConductor) REFERENCES usuarios(idUsuario),
    CONSTRAINT viajes_fk_vehiculo FOREIGN KEY (idVehiculo) REFERENCES vehiculos(idVehiculo),
    CONSTRAINT viajes_fk_tarifa FOREIGN KEY (idTarifa) REFERENCES tarifas(idTarifa)
);

-- TABLA REVIEWS
CREATE TABLE reviews (
    idRevision NUMBER NOT NULL,
    idUsuarioCalificador NUMBER NOT NULL,
    idUsuarioCalificado NUMBER NOT NULL,
    puntuacion NUMBER NOT NULL,
    comentario VARCHAR2(250 BYTE),
    fecha TIMESTAMP NOT NULL,
    idViaje NUMBER NOT NULL,
    CONSTRAINT reviews_pk PRIMARY KEY (idRevision),
    CONSTRAINT reviews_fk_calificador FOREIGN KEY (idUsuarioCalificador) REFERENCES usuarios(idUsuario),
    CONSTRAINT reviews_fk_calificado FOREIGN KEY (idUsuarioCalificado) REFERENCES usuarios(idUsuario),
    CONSTRAINT reviews_fk_viaje FOREIGN KEY (idViaje) REFERENCES viajes(idViaje)
);
