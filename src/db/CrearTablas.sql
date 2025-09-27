CREATE TABLE DatosUsuario (
    idUsuario NUMBER PRIMARY KEY  UNIQUE,
    nombre VARCHAR2(50) NOT NULL,
    numeroCelular NUMBER(10) NOT NULL UNIQUE,
    numeroCedula NUMBER(10) NOT NULL UNIQUE,
    correoElectronico VARCHAR2(100) NOT NULL UNIQUE
);

CREATE TABLE TarjetaCredito (
    idTarjeta NUMBER PRIMARY KEY UNIQUE,
    titular VARCHAR2(100) NOT NULL,
    numeroTarjeta NUMBER(16) NOT NULL UNIQUE,
    fechaExpiracion DATE NOT NULL,
    codigoSeguridad NUMBER(3) NOT NULL
);

CREATE TABLE UsuarioServicio(
    idUsuario NUMBER PRIMARY KEY UNIQUE,
    FOREIGN KEY (idTarjeta) REFERENCES TarjetaCredito(idTarjeta)
);

CREATE TABLE UsuarioConductor(
    idUsuario NUMBER PRIMARY KEY UNIQUE,
    disponibilidad CHAR(1) CONSTRAINT CHK_USUARIO_CONDUCTOR_DISPONIBILIDAD CHECK (disponibilidad IN ('D', 'O')) NOT NULL
);

CREATE TABLE Review(
    idReview NUMBER PRIMARY KEY UNIQUE,
    FOREIGN KEY (idUsuario) REFERENCES DatosUsuario(idUsuario),
    FOREIGN KEY (usuarioReceptor) REFERENCES DatosUsuario(idUsuario),
    calificacion NUMBER(1) CONSTRAINT CHK_REVIEW_CALIFICACION CHECK (calificacion BETWEEN 1 AND 5) NOT NULL,
    comentario VARCHAR2(255) CONSTRAINT CHK_REVIEW_COMENTARIO CHECK (LENGTH(comentario) BETWEEN 10 AND 255) NOT NULL
);

CREATE TABLE Vehiculo(
    idVehiculo NUMBER PRIMARY KEY UNIQUE,
    tipo VARCHAR2(50) NOT NULL CONSTRAINT CHK_VEHICULO_TIPO CHECK (tipo IN ('Carro', 'Motocicleta', 'Camioneta')),
    marca VARCHAR2(50) NOT NULL,
    modelo VARCHAR2(50) NOT NULL,
    color VARCHAR2(30) NOT NULL,
    placa VARCHAR2(10) NOT NULL UNIQUE,
    ciudadExpedicion VARCHAR2(50) NOT NULL,
    capacidadPasajeros NUMBER(2) CONSTRAINT CHK_VEHICULO_CAPACIDAD CHECK (capacidadPasajeros BETWEEN 1 AND 7),
    nivelVehiculo VARCHAR2(20) CONSTRAINT CHK_VEHICULO_NIVEL CHECK (nivelVehiculo IN ('Estandar', 'Confort', 'Large'))
);

CREATE TABLE ConductorVehiculo(
    FOREIGN KEY (idVehiculo) REFERENCES Vehiculo(idVehiculo),
    FOREIGN KEY (idUsuario) REFERENCES UsuarioConductor(idUsuario)
);

CREATE TABLE FranjaHorariaActividad (
  idFranja    VARCHAR2(10)  PRIMARY KEY,
  dia           VARCHAR2(10)  NOT NULL  CONSTRAINT CHK_FHA_DIA CHECK (DIA IN ('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo')),
  horaInicio   INTERVAL DAY TO SECOND NOT NULL,
  horaFin      INTERVAL DAY TO SECOND NOT NULL,
  CONSTRAINT CHK_FHA_RANGO_HORA CHECK ( horaInicio >= INTERVAL '0' SECOND AND horaFin    >  horaInicio AND horaFin    <= INTERVAL '1' DAY )
);

CREATE TABLE HorarioVehiculo (
    idVehiculo NUMBER PRIMARY KEY,
    FOREIGN KEY (idVehiculo) REFERENCES Vehiculo(idVehiculo),
    FOREIGN KEY (idFranja) REFERENCES FranjaHorariaActividad(idFranja),
    CONSTRAINT FK_HORARIO_VEHICULO FOREIGN KEY (idVehiculo) REFERENCES Vehiculo(idVehiculo)
);

CREATE TABLE Ciudad(
    idCiudad NUMBER PRIMARY KEY UNIQUE,
    nombre VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE PuntoUbicacion (
    idPunto NUMBER PRIMARY KEY UNIQUE,
    latitud NUMBER(10, 6) NOT NULL CONSTRAINT CHK_PUNTO_UBICACION_LATITUD CHECK (latitud BETWEEN -90 AND 90),
    longitud NUMBER(10, 6) NOT NULL CONSTRAINT CHK_PUNTO_UBICACION_LONGITUD CHECK (longitud BETWEEN -180 AND 180),
    direccionAproximada VARCHAR2(255) NOT NULL,
    FOREIGN KEY (idCiudad) REFERENCES Ciudad(idCiudad)
);

CREATE TABLE Servicio(
    idServicio NUMBER PRIMARY KEY UNIQUE,
    FOREIGN KEY (idUsuario) REFERENCES DatosUsuario(idUsuario),
    FOREIGN KEY (idConductor) REFERENCES UsuarioConductor(idUsuario),
    FOREIGN KEY (puntoPartida) REFERENCES PuntoUbicacion(idPunto),
    FOREIGN KEY (Tarifa) REFERENCES Tarifa(idTarifa)
);

CREATE TABLE DetallesServicio (
  idServicio VARCHAR2(10) PRIMARY KEY,
  fecha DATE NOT NULL,
  horaInicio DATE NOT NULL,
  horaFin DATE NOT NULL,
  longitudTrayecto NUMBER(6,2) NOT NULL,
  costoTotal NUMBER(10,2) NOT NULL,
  comision NUMBER(10,2) NOT NULL,
  estado VARCHAR2(15) NOT NULL CHECK (estado IN ('Solicitado','En curso','Finalizado','Cancelado')),
  tipoServicio VARCHAR2(20) NOT NULL CHECK (tipoServicio IN ('Transporte Pasajeros','Domicilio Comida','Transporte Paquete')),
  nivel VARCHAR2(10) NOT NULL CHECK (nivel IN ('Estandar','Confort','Large')),
  orden NUMBER NULL,
  restaurante VARCHAR2(80) NULL,

  CONSTRAINT FK_DETALLES_SERVICIO FOREIGN KEY (idServicio) REFERENCES servicios(idServicio)
);

CREATE TABLE Tarifa (
  idTarifa NUMBER PRIMARY KEY,
  tipoServicio VARCHAR2(30) NOT NULL CONSTRAINT CHK_TARIFA_TIPO CHECK (tipoServicio IN ('Transporte Pasajeros','Domicilio Comida', 'Transporte Paquete')),
  nivel VARCHAR2(10) NOT NULL CONSTRAINT CHK_TARIFA_NIVEL CHECK (nivel IN ('Estandar','Confort','Large')),
  precioPorKm NUMBER(10,2) NOT NULL CONSTRAINT CHK_TARIFA_PRECIO_POS CHECK (precioPorKm > 0),
  vigenciaDesde DATE NOT NULL,
  vigenciaHasta DATE NOT NULL,
  CONSTRAINT CHK_TARIFA_RANGO_FECHAS
    CHECK (vigenciaDesde < vigenciaHasta),

  CONSTRAINT CK_TARIFA_UNICA
    UNIQUE (tipoServicio, nivel, vigenciaDesde, vigenciaHasta)
);