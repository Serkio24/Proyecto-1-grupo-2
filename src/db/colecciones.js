use("ISIS2304A01202520");

// Usuarios de la aplicación (clientes y conductores)
db.USUARIOS.drop();

db.createCollection("USUARIOS", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "nombre",
        "numeroCelular",
        "numeroCedula",
        "correoElectronico",
        "tipoUsuario"
        // tarjetasCredito NO es obligatoria
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },
        nombre: {
          bsonType: "string"
        },
        numeroCelular: {
          bsonType: "string"
        },
        numeroCedula: {
          bsonType: "string"
        },
        correoElectronico: {
          bsonType: "string"
        },
        tipoUsuario: {
          bsonType: "string", // "Cliente" o "Conductor"
        },
        // arreglo opcional, puede no existir o ser []
        tarjetasCredito: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: [
              "idTarjetaCredito",
              "titularDeLaTarjeta",
              "numeroTarjeta",
              "fechaExpiracion",
              "codigoSeguridad"
            ],
            properties: {
              idTarjetaCredito: {
                bsonType: ["int", "long"]
              },
              titularDeLaTarjeta: {
                bsonType: "string"
              },
              numeroTarjeta: {
                bsonType: "string"
              },
              fechaExpiracion: {
                bsonType: "string" // "AAAA-MM"
              },
              codigoSeguridad: {
                bsonType: "int"
              }
            }
          }
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});


// Vehículos registrados por los conductores
db.VEHICULOS.drop();

db.createCollection("VEHICULOS", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "idConductor",
        "tipo",
        "marca",
        "modelo",
        "color",
        "placa",
        "ciudadExpedicion",
        "capacidadPasajeros",
        "nivel"
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },
        idConductor: {
          bsonType: ["int", "long"] // referencia a USUARIOS
        },
        tipo: {
          bsonType: "string"
        },
        marca: {
          bsonType: "string"
        },
        modelo: {
          bsonType: "string"
        },
        color: {
          bsonType: "string"
        },
        placa: {
          bsonType: "string"
        },
        ciudadExpedicion: {
          bsonType: "string"
        },
        capacidadPasajeros: {
          bsonType: "int"
        },
        nivel: {
          bsonType: "string" // Estandar, Confort, Large...
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});


// Disponibilidades de conductor + vehículo
db.DISPONIBILIDADES.drop();

db.createCollection("DISPONIBILIDADES", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "idConductor",
        "idVehiculo",
        "ciudad",
        "tiposServicio",
        "franjasHorarias"
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },
        idConductor: {
          bsonType: ["int", "long"] // referencia a USUARIOS
        },
        idVehiculo: {
          bsonType: ["int", "long"] // referencia a VEHICULOS
        },
        ciudad: {
          bsonType: "string"
        },
        tiposServicio: {
          bsonType: "array",
          items: {
            bsonType: "string"
          }
        },
        franjasHorarias: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: [
              "idFranja",
              "diaSemana",
              "horaInicio",
              "horaFin",
              "tipoServicio",
              "disponible"
            ],
            properties: {
              idFranja: {
                bsonType: ["int", "long"]
              },
              diaSemana: {
                bsonType: "string"
              },
              horaInicio: {
                bsonType: "string" // "HH:mm"
              },
              horaFin: {
                bsonType: "string" // "HH:mm"
              },
              tipoServicio: {
                bsonType: "string"
              },
              disponible: {
                bsonType: "bool"
              }
            }
          }
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});


// Catálogo de tarifas por tipo de servicio y nivel
db.TARIFAS.drop();

db.createCollection("TARIFAS", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "tipoServicio",
        "nivel",
        "precioPorKm",
        "vigenciaDesde"
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },
        tipoServicio: {
          bsonType: "string"
        },
        nivel: {
          bsonType: "string"
        },
        precioPorKm: {
          bsonType: ["double", "decimal", "int"]
        },
        vigenciaDesde: {
          bsonType: "date"
        },
        vigenciaHasta: {
          bsonType: ["date", "null"]
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});


// Viajes (servicio solicitado + viaje ejecutado)
db.VIAJES.drop();

db.createCollection("VIAJES", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "idServicio",
        "idCliente",
        "idConductor",
        "idVehiculo",
        "idTarifa",
        "fechaHora",
        "tipoServicio",
        "nivelRequerido",
        "estado",
        "puntoOrigen",
        "destinos",
        "fechaHoraInicio",
        "fechaHoraFin",
        "longitudTrayecto",
        "costoTotal"
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },

        // referencias principales
        idServicio: {
          bsonType: ["int", "long"]
        },
        idCliente: {
          bsonType: ["int", "long"] // USUARIOS
        },
        idConductor: {
          bsonType: ["int", "long"] // USUARIOS
        },
        idVehiculo: {
          bsonType: ["int", "long"] // VEHICULOS
        },
        idTarifa: {
          bsonType: ["int", "long"] // TARIFAS
        },

        // parte de la solicitud (Servicio)
        fechaHora: {
          bsonType: "date"
        },
        tipoServicio: {
          bsonType: "string"
        },
        nivelRequerido: {
          bsonType: "string"
        },
        estado: {
          bsonType: "string" // Pendiente, Asignado, Cancelado, Finalizado...
        },
        orden: {
          bsonType: ["int", "null"]
        },
        restaurante: {
          bsonType: ["string", "null"]
        },

        // punto de origen embebido
        puntoOrigen: {
          bsonType: "object",
          required: [
            "idPuntoPartida",
            "nombre",
            "latitud",
            "longitud",
            "direccionAproximada",
            "ciudad"
          ],
          properties: {
            idPuntoPartida: {
              bsonType: ["int", "long"]
            },
            nombre: {
              bsonType: "string"
            },
            latitud: {
              bsonType: ["double", "decimal"]
            },
            longitud: {
              bsonType: ["double", "decimal"]
            },
            direccionAproximada: {
              bsonType: "string"
            },
            ciudad: {
              bsonType: "string"
            }
          }
        },

        // destinos embebidos (ServicioDestino + PuntoGeografico)
        destinos: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: [
              "idDestino",
              "idPuntoLlegada",
              "puntoGeografico"
            ],
            properties: {
              idDestino: {
                bsonType: ["int", "long"]
              },
              idPuntoLlegada: {
                bsonType: ["int", "long"]
              },
              puntoGeografico: {
                bsonType: "object",
                required: [
                  "nombre",
                  "latitud",
                  "longitud",
                  "direccionAproximada",
                  "ciudad"
                ],
                properties: {
                  nombre: {
                    bsonType: "string"
                  },
                  latitud: {
                    bsonType: ["double", "decimal"]
                  },
                  longitud: {
                    bsonType: ["double", "decimal"]
                  },
                  direccionAproximada: {
                    bsonType: "string"
                  },
                  ciudad: {
                    bsonType: "string"
                  }
                }
              }
            }
          }
        },

        // parte del viaje ejecutado
        fechaHoraInicio: {
          bsonType: "date"
        },
        fechaHoraFin: {
          bsonType: "date"
        },
        longitudTrayecto: {
          bsonType: ["double", "decimal"]
        },
        costoTotal: {
          bsonType: ["double", "decimal", "int"]
        },

        // reviews embebidas (opcional)
        reviews: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: [
              "idRevision",
              "idUsuarioCalificador",
              "idUsuarioCalificado",
              "puntuacion",
              "comentario",
              "fecha"
            ],
            properties: {
              idRevision: {
                bsonType: ["int", "long"]
              },
              idUsuarioCalificador: {
                bsonType: ["int", "long"]
              },
              idUsuarioCalificado: {
                bsonType: ["int", "long"]
              },
              puntuacion: {
                bsonType: ["int", "double"]
              },
              comentario: {
                bsonType: "string"
              },
              fecha: {
                bsonType: "date"
              }
            }
          }
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});
