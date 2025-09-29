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
DROP TABLE usuarios_servicios CASCADE CONSTRAINTS;
DROP TABLE usuarios CASCADE CONSTRAINTS;

-- TABLA USUARIOS
CREATE TABLE usuarios (
    id_usuario NUMBER NOT NULL,
    nombre VARCHAR2(100 BYTE) NOT NULL,
    numero_celular VARCHAR2(20 BYTE) NOT NULL,
    numero_cedula VARCHAR2(20 BYTE) NOT NULL,
    correo_electronico VARCHAR2(100 BYTE) NOT NULL,
    tipo_usuario VARCHAR2(20 BYTE) CHECK (tipo_usuario IN ('Cliente','Conductor')),
    CONSTRAINT usuarios_pk PRIMARY KEY (id_usuario)
);

-- TABLA VEHICULOS
CREATE TABLE vehiculos (
    id_vehiculo NUMBER NOT NULL,
    tipo VARCHAR2(50 BYTE) NOT NULL,
    marca VARCHAR2(50 BYTE) NOT NULL,
    modelo VARCHAR2(50 BYTE) NOT NULL,
    color VARCHAR2(30 BYTE) NOT NULL,
    placa VARCHAR2(20 BYTE) NOT NULL,
    ciudad_expedicion VARCHAR2(50 BYTE) NOT NULL,
    capacidad_pasajeros NUMBER NOT NULL,
    CONSTRAINT vehiculos_pk PRIMARY KEY (id_vehiculo)
);

-- TABLA NIVELES_VEHICULO
CREATE TABLE niveles_vehiculo (
    id_vehiculo NUMBER NOT NULL,
    modelo VARCHAR2(50 BYTE) NOT NULL,
    capacidad_pasajeros NUMBER NOT NULL CHECK (capacidad_pasajeros > 0),
    nivel VARCHAR2(20 BYTE) CHECK (nivel IN ('Estandar','Confort','Large')),
    CONSTRAINT niveles_vehiculo_pk PRIMARY KEY (id_vehiculo)
);

-- TABLA CONDUCTOR_VEHICULOS
CREATE TABLE conductor_vehiculos (
    id_conductor NUMBER NOT NULL,
    id_vehiculo NUMBER NOT NULL,
    CONSTRAINT conductor_vehiculos_pk PRIMARY KEY (id_conductor, id_vehiculo),
    CONSTRAINT conductor_vehiculos_fk1 FOREIGN KEY (id_conductor) REFERENCES usuarios(id_usuario),
    CONSTRAINT conductor_vehiculos_fk2 FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo)
);

-- TABLA TARJETAS_CREDITO
CREATE TABLE tarjetas_credito (
    id_tarjeta_credito NUMBER NOT NULL,
    titular_tarjeta VARCHAR2(100 BYTE) NOT NULL,
    numero_tarjeta VARCHAR2(20 BYTE) NOT NULL,
    fecha_expiracion VARCHAR2(10 BYTE) NOT NULL,
    codigo_seguridad NUMBER NOT NULL,
    cliente_id NUMBER NOT NULL,
    CONSTRAINT tarjetas_credito_pk PRIMARY KEY (id_tarjeta_credito),
    CONSTRAINT tarjetas_credito_fk FOREIGN KEY (cliente_id) REFERENCES usuarios(id_usuario)
);

-- TABLA FRANJAS_HORARIAS
CREATE TABLE franjas_horarias (
    id_franja NUMBER NOT NULL,
    dia_semana VARCHAR2(20 BYTE) NOT NULL,
    hora_inicio VARCHAR2(10 BYTE) NOT NULL,
    hora_fin VARCHAR2(10 BYTE) NOT NULL,
    tipo_servicio VARCHAR2(50 BYTE) CHECK (tipo_servicio IN ('Transporte Pasajeros', 'Domicilio Comida', 'Transporte Paquete')),
    CONSTRAINT franjas_horarias_pk PRIMARY KEY (id_franja)
);

-- TABLA DISPONIBILIDADES
CREATE TABLE disponibilidades (
    id_vehiculo NUMBER NOT NULL,
    id_franja NUMBER NOT NULL,
    disponible CHAR(1) CHECK (disponible IN ('Y','N')),
    CONSTRAINT disponibilidades_pk PRIMARY KEY (id_vehiculo, id_franja),
    CONSTRAINT disponibilidades_fk1 FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo),
    CONSTRAINT disponibilidades_fk2 FOREIGN KEY (id_franja) REFERENCES franjas_horarias(id_franja)
);

-- TABLA CIUDADES
CREATE TABLE ciudades (
    id_ciudad NUMBER NOT NULL,
    nombre VARCHAR2(50 BYTE) NOT NULL,
    CONSTRAINT ciudades_pk PRIMARY KEY (id_ciudad)
);

-- TABLA PUNTOS_GEOGRAFICOS
CREATE TABLE puntos_geograficos (
    id_punto NUMBER NOT NULL,
    nombre VARCHAR2(100 BYTE),
    latitud NUMBER NOT NULL,
    longitud NUMBER NOT NULL,
    direccion_aproximada VARCHAR2(150 BYTE),
    id_ciudad NUMBER NOT NULL,
    CONSTRAINT puntos_geograficos_pk PRIMARY KEY (id_punto),
    CONSTRAINT puntos_geograficos_fk FOREIGN KEY (id_ciudad) REFERENCES ciudades(id_ciudad)
);

-- TABLA SERVICIOS
CREATE TABLE servicios (
    id_servicio NUMBER NOT NULL,
    id_cliente NUMBER NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    tipo_servicio VARCHAR2(50 BYTE) CHECK (tipo_servicio IN ('Transporte Pasajeros','Domicilio Comida','Transporte Paquete')),
    nivel_requerido VARCHAR2(20 BYTE) CHECK (nivel_requerido IN ('Estandar','Confort','Large')),
    estado VARCHAR2(20 BYTE) CHECK (estado IN ('Pendiente','Asignado','Cancelado')),
    orden NUMBER,
    restaurante VARCHAR2(100 BYTE),
    id_punto_partida NUMBER NOT NULL,
    CONSTRAINT servicios_pk PRIMARY KEY (id_servicio),
    CONSTRAINT servicios_fk_cliente FOREIGN KEY (id_cliente) REFERENCES usuarios(id_usuario),
    CONSTRAINT servicios_fk_punto FOREIGN KEY (id_punto_partida) REFERENCES puntos_geograficos(id_punto)
);

-- TABLA SERVICIOS_DESTINOS
CREATE TABLE servicios_destinos (
    id_destino NUMBER NOT NULL,
    id_servicio NUMBER NOT NULL,
    id_punto_llegada NUMBER NOT NULL,
    CONSTRAINT servicios_destinos_pk PRIMARY KEY (id_destino),
    CONSTRAINT servicios_destinos_fk_servicio FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio),
    CONSTRAINT servicios_destinos_fk_punto FOREIGN KEY (id_punto_llegada) REFERENCES puntos_geograficos(id_punto)
);

-- TABLA TARIFAS
CREATE TABLE tarifas (
    id_tarifa NUMBER NOT NULL,
    tipo_servicio VARCHAR2(50 BYTE) CHECK (tipo_servicio IN ('Transporte Pasajeros','Domicilio Comida','Transporte Paquete')),
    nivel VARCHAR2(20 BYTE) CHECK (nivel IN ('Estandar','Confort','Large')),
    precio_por_km NUMBER NOT NULL CHECK (precio_por_km > 0),
    vigencia_desde DATE NOT NULL,
    vigencia_hasta DATE NOT NULL,
    CONSTRAINT tarifas_pk PRIMARY KEY (id_tarifa)
);

-- TABLA VIAJES
CREATE TABLE viajes (
    id_viaje NUMBER NOT NULL,
    fecha_hora_inicio TIMESTAMP NOT NULL,
    fecha_hora_fin TIMESTAMP NOT NULL,
    longitud_trayecto NUMBER NOT NULL,
    id_servicio NUMBER NOT NULL,
    id_conductor NUMBER NOT NULL,
    id_vehiculo NUMBER NOT NULL,
    CONSTRAINT viajes_pk PRIMARY KEY (id_viaje),
    CONSTRAINT viajes_fk_servicio FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio),
    CONSTRAINT viajes_fk_conductor FOREIGN KEY (id_conductor) REFERENCES usuarios(id_usuario),
    CONSTRAINT viajes_fk_vehiculo FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo)
);

-- TABLA REVIEWS
CREATE TABLE reviews (
    id_revision NUMBER NOT NULL,
    id_usuario_calificador NUMBER NOT NULL,
    id_usuario_calificado NUMBER NOT NULL,
    puntuacion NUMBER NOT NULL,
    comentario VARCHAR2(250 BYTE),
    fecha TIMESTAMP NOT NULL,
    id_viaje NUMBER NOT NULL,
    CONSTRAINT reviews_pk PRIMARY KEY (id_revision),
    CONSTRAINT reviews_fk_calificador FOREIGN KEY (id_usuario_calificador) REFERENCES usuarios(id_usuario),
    CONSTRAINT reviews_fk_calificado FOREIGN KEY (id_usuario_calificado) REFERENCES usuarios(id_usuario),
    CONSTRAINT reviews_fk_viaje FOREIGN KEY (id_viaje) REFERENCES viajes(id_viaje)
);
