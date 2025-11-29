use("ISIS2304A01202520");

//usuarios
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
          bsonType: "string" // "Cliente" o "Conductor"
        },
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


//vehiculos
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
          bsonType: ["int", "long"]
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
          bsonType: "string"
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});


//disponibilidades
db.DISPONIBILIDADES.drop();

db.createCollection("DISPONIBILIDADES", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "idConductor",
        "idVehiculo",
        "franjasHorarias"
      ],
      properties: {
        _id: {
          bsonType: ["int", "long", "objectId"]
        },
        idConductor: {
          bsonType: ["int", "long"]
        },
        idVehiculo: {
          bsonType: ["int", "long"]
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


//tarifas
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


//viajes
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

        idServicio: {
          bsonType: ["int", "long"]
        },
        idCliente: {
          bsonType: ["int", "long"]
        },
        idConductor: {
          bsonType: ["int", "long"]
        },
        idVehiculo: {
          bsonType: ["int", "long"]
        },
        idTarifa: {
          bsonType: ["int", "long"]
        },

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
          bsonType: "string"
        },
        orden: {
          bsonType: ["int", "null"]
        },
        restaurante: {
          bsonType: ["string", "null"]
        },
        puntoOrigen: {
          bsonType: "object",
          required: [
            "idPunto",
            "nombre",
            "latitud",
            "longitud",
            "direccionAproximada",
            "ciudad"
          ],
          properties: {
            idPunto: {
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
        destinos: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: [
              "idPunto",
              "nombre",
              "latitud",
              "longitud",
              "direccionAproximada",
              "ciudad"
            ],
            properties: {
              idPunto: {
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
          }
        },

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
