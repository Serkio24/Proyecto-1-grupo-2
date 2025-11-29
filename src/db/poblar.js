use("ISIS2304A01202520");

// ---------- Helpers básicos ----------

function randInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function choice(arr) {
  return arr[randInt(0, arr.length - 1)];
}

function randomPhone() {
  return (
    "3" +
    randInt(0, 9) +
    randInt(100000000, 999999999).toString().slice(1)
  );
}

function randomCedula() {
  return randInt(10000000, 99999999).toString();
}

function randomEmail(prefix, id) {
  return prefix.toLowerCase() + id + "@correo.com";
}

var ciudades = ["Bogotá", "Medellín", "Cali", "Barranquilla"];
var tiposServicio = [
  "Transporte Pasajeros",
  "Domicilio Comida",
  "Transporte Mercancías",
];
var niveles = ["Estandar", "Confort", "Large"];

// ---------- 1. Poblar USUARIOS (clientes + conductores) ----------

var N_CLIENTES = 200;
var N_CONDUCTORES = 50;

var usuarios = [];
var clientesIds = [];
var conductoresIds = [];

var nextUserId = 1;

// Clientes
for (var i = 0; i < N_CLIENTES; i++) {
  var id = nextUserId++;
  clientesIds.push(id);

  var usuario = {
    _id: id,
    nombre: "Cliente " + id,
    numeroCelular: randomPhone(),
    numeroCedula: randomCedula(),
    correoElectronico: randomEmail("cliente", id),
    tipoUsuario: "Cliente",
  };

  // ~70% de clientes con al menos una tarjeta
  if (Math.random() < 0.7) {
    usuario.tarjetasCredito = [
      {
        idTarjetaCredito: id * 10,
        titularDeLaTarjeta: "Cliente " + id,
        numeroTarjeta: "**** **** **** " + randInt(1000, 9999),
        fechaExpiracion: "2027-0" + randInt(1, 9),
        codigoSeguridad: randInt(100, 999),
      },
    ];
  }

  usuarios.push(usuario);
}

// Conductores
for (var j = 0; j < N_CONDUCTORES; j++) {
  var idc = nextUserId++;
  conductoresIds.push(idc);

  var conductor = {
    _id: idc,
    nombre: "Conductor " + idc,
    numeroCelular: randomPhone(),
    numeroCedula: randomCedula(),
    correoElectronico: randomEmail("conductor", idc),
    tipoUsuario: "Conductor",
    tarjetasCredito: [],
  };

  usuarios.push(conductor);
}

db.USUARIOS.insertMany(usuarios);

// ---------- 2. Poblar TARIFAS ----------

var tarifas = [];
var nextTarifaId = 1;

for (var t = 0; t < tiposServicio.length; t++) {
  for (var n = 0; n < niveles.length; n++) {
    tarifas.push({
      _id: nextTarifaId,
      tipoServicio: tiposServicio[t],
      nivel: niveles[n],
      precioPorKm: 1500 + 500 * n + 200 * t, // algo creciente
      vigenciaDesde: new Date("2025-01-01T00:00:00Z"),
      vigenciaHasta: null,
    });
    nextTarifaId++;
  }
}

db.TARIFAS.insertMany(tarifas);

// para lookup rápido por (tipoServicio, nivel)
var tarifasIndex = {};
tarifas.forEach(function (tar) {
  tarifasIndex[tar.tipoServicio + "|" + tar.nivel] = tar._id;
});

// ---------- 3. Poblar VEHICULOS ----------

var vehiculos = [];
var mapaVehiculosPorConductor = {}; // conductorId -> [vehiculos]

var nextVehiculoId = 1;

// promedio 1.1 coches por conductor: unos 55–60 para 50 conductores
conductoresIds.forEach(function (idCond) {
  var numVeh = Math.random() < 0.1 ? 2 : 1; // ~10% tienen 2 vehículos
  mapaVehiculosPorConductor[idCond] = [];

  for (var k = 0; k < numVeh; k++) {
    var vId = nextVehiculoId++;
    var nivel = choice(niveles);
    var ciudad = choice(ciudades);

    var veh = {
      _id: vId,
      idConductor: idCond,
      tipo: "Carro",
      marca: "Marca" + randInt(1, 5),
      modelo: "Modelo" + randInt(1, 5),
      color: "Color" + randInt(1, 5),
      placa: "ABC" + ("00" + vId).slice(-3),
      ciudadExpedicion: ciudad,
      capacidadPasajeros: choice([4, 4, 4, 6]),
      nivel: nivel,
    };

    vehiculos.push(veh);
    mapaVehiculosPorConductor[idCond].push(veh);
  }
});

db.VEHICULOS.insertMany(vehiculos);

// ---------- 4. Poblar DISPONIBILIDADES ----------

var disponibilidades = [];
var nextDispId = 1;
var dias = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];

vehiculos.forEach(function (veh) {
  var disp = {
    _id: nextDispId++,
    idConductor: veh.idConductor,
    idVehiculo: veh._id,
    franjasHorarias: [],
  };

  // 2 o 3 franjas semanales
  var numFranjas = randInt(2, 3);
  for (var f = 0; f < numFranjas; f++) {
    var dia = choice(dias);
    var horaInicio = randInt(6, 20); // entre 6:00 y 20:00
    var duracion = choice([2, 3, 4]); // horas

    disp.franjasHorarias.push({
      idFranja: f + 1,
      diaSemana: dia,
      horaInicio:
        (horaInicio < 10 ? "0" : "") + horaInicio + ":00",
      horaFin:
        (horaInicio + duracion < 10 ? "0" : "") +
        (horaInicio + duracion) +
        ":00",
      tipoServicio: choice(tiposServicio),
      disponible: true,
    });
  }

  disponibilidades.push(disp);
});

db.DISPONIBILIDADES.insertMany(disponibilidades);

// ---------- 5. Poblar VIAJES ----------

var viajes = [];
var nextViajeId = 1;
var nextServicioId = 10000;
var nextPuntoId = 8000;
var nextReviewId = 10000;

var baseFecha = new Date("2025-09-20T06:00:00Z");

// generamos unos 400 viajes de ejemplo
var N_VIAJES = 400;

for (var v = 0; v < N_VIAJES; v++) {
  var idViaje = nextViajeId++;
  var idServicio = nextServicioId++;

  var idCliente = choice(clientesIds);
  var idCond = choice(conductoresIds);
  var vehsCond = mapaVehiculosPorConductor[idCond];
  if (!vehsCond || vehsCond.length === 0) {
    continue;
  }
  var vehiculo = choice(vehsCond);

  var tipoServ = choice(tiposServicio);
  var nivelVeh = vehiculo.nivel;
  var idTarifa = tarifasIndex[tipoServ + "|" + nivelVeh];

  // fecha de solicitud en los últimos 5 días
  var fechaSolicitud =
    new Date(
      baseFecha.getTime() +
        randInt(0, 5) * 24 * 3600 * 1000 +
        randInt(0, 23) * 3600 * 1000 +
        randInt(0, 59) * 60 * 1000
    );

  var duracionMin = randInt(10, 45);
  var fechaInicio = new Date(
    fechaSolicitud.getTime() + randInt(1, 10) * 60 * 1000
  );
  var fechaFin = new Date(
    fechaInicio.getTime() + duracionMin * 60 * 1000
  );

  var longitudKm = randInt(3, 18) + Math.random();
  var precioKm = tarifas.find((t) => t._id === idTarifa).precioPorKm;
  var costo = Math.round(longitudKm * precioKm);

  // punto de origen (PUNTO GEOGRAFICO con idPunto)
  var idPunto = nextPuntoId++;
  var ciudad = vehiculo.ciudadExpedicion;
  var puntoOrigen = {
    idPunto: idPunto,
    nombre: "Origen " + idPunto,
    latitud: 4.5 + Math.random() * 0.5,
    longitud: -74.2 + Math.random() * 0.4,
    direccionAproximada: "Dir " + idPunto,
    ciudad: ciudad,
  };

  // destinos: array de PUNTO GEOGRAFICO
  var numDestinos = Math.random() < 0.3 ? 2 : 1;
  var destinos = [];
  for (var d = 0; d < numDestinos; d++) {
    var idPuntoDest = nextPuntoId++;
    destinos.push({
      idPunto: idPuntoDest,
      nombre: "Destino " + idPuntoDest,
      latitud: 4.5 + Math.random() * 0.5,
      longitud: -74.2 + Math.random() * 0.4,
      direccionAproximada: "Dir " + idPuntoDest,
      ciudad: ciudad,
    });
  }

  // estado: mayoría finalizados, algunos cancelados
  var estado = Math.random() < 0.1 ? "Cancelado" : "Finalizado";

  var viaje = {
    _id: idViaje,
    idServicio: idServicio,
    idCliente: idCliente,
    idConductor: idCond,
    idVehiculo: vehiculo._id,
    idTarifa: idTarifa,

    fechaHora: fechaSolicitud,
    tipoServicio: tipoServ,
    nivelRequerido: nivelVeh,
    estado: estado,
    orden: null,
    restaurante: null,

    puntoOrigen: puntoOrigen,
    destinos: destinos,

    fechaHoraInicio: fechaInicio,
    fechaHoraFin: fechaFin,
    longitudTrayecto: longitudKm,
    costoTotal: costo,
  };

  // ~40% de los viajes con al menos una review
  if (Math.random() < 0.4 && estado === "Finalizado") {
    viaje.reviews = [];

    // cliente califica al conductor
    if (Math.random() < 0.9) {
      viaje.reviews.push({
        idRevision: nextReviewId++,
        idUsuarioCalificador: idCliente,
        idUsuarioCalificado: idCond,
        puntuacion: randInt(3, 5),
        comentario:
          "Comentario cliente→conductor " + idViaje,
        fecha: new Date(
          fechaFin.getTime() + 5 * 60 * 1000
        ),
      });
    }

    // conductor califica al cliente
    if (Math.random() < 0.5) {
      viaje.reviews.push({
        idRevision: nextReviewId++,
        idUsuarioCalificador: idCond,
        idUsuarioCalificado: idCliente,
        puntuacion: randInt(3, 5),
        comentario:
          "Comentario conductor→cliente " + idViaje,
        fecha: new Date(
          fechaFin.getTime() + 10 * 60 * 1000
        ),
      });
    }
  }

  viajes.push(viaje);
}

db.VIAJES.insertMany(viajes);
