package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;
import uniandes.edu.co.proyecto.repositories.UsoServiciosRepository;

@RestController
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsoServiciosRepository usoServiciosRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    // create
    @PostMapping("/viajes/new/save")
    public ResponseEntity<ViajeResponse> crearViaje(@RequestBody ViajeEntity viaje) {
        try {
            ViajeEntity ultimo = viajeRepository.findTopByOrderByIdViajeDesc();

            Long nuevoId;
            if (ultimo == null || ultimo.getIdViaje() == null) {
                nuevoId = 1L;
            } else {
                nuevoId = ultimo.getIdViaje() + 1;
            }

            viaje.setIdViaje(nuevoId);

            viaje.setFechaHora(new Date());

            TarifaEntity tarifa = tarifaRepository.encontrarTarifa(viaje.getTipoServicio(), viaje.getNivelRequerido(), viaje.getFechaHora());
            viaje.setIdTarifa(tarifa.getIdTarifa()); 

            DisponibilidadEntity disponibilidad = disponibilidadRepository.buscarConductorDisponible( diaDeHoy(), 
                                                                                             String.format("%02d:00", LocalTime.now().getHour()));

            if (disponibilidad == null) {
                ViajeResponse error = new ViajeResponse("No hay conductores disponibles en este momento", null);
                viaje.setEstado("Cancelado");
                viajeRepository.save(viaje);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            viaje.setIdConductor(disponibilidad.getIdConductor());

            viaje.setIdVehiculo(disponibilidad.getIdVehiculo());
            viaje.setEstado("Confirmado");

            for (FranjaHorariaEntity franja: disponibilidad.getFranjasHorarias()) {
                if (franja.getDiaSemana().equals(diaDeHoy())) {
                    franja.setDisponible(false);
                    break; // q pereza hacer otro booleano jsjsjs
                }
            }
            viaje.setFechaHoraInicio(new Date());
            ViajeEntity guardado = viajeRepository.save(viaje);

            ViajeResponse respuesta = new ViajeResponse("Viaje creado exitosamente", guardado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            ViajeResponse error = new ViajeResponse("Error al crear el viaje: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read
    @GetMapping("/viajes")
    public ResponseEntity<Collection<ViajeEntity>> listarViajes() {
        try {
            List<ViajeEntity> viajes = viajeRepository.buscarTodosLosViajes();
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // read
    @GetMapping("/viajes/{id}")
    public ResponseEntity<ViajeEntity> obtenerViaje(@PathVariable("id") Long id) {
        try {
            ViajeEntity viaje = viajeRepository.buscarPorId(id);
            if (viaje != null) {
                return ResponseEntity.ok(viaje);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // update
    // @PutMapping("/viajes/{id}")
    // public ResponseEntity<ViajeResponse> actualizarViaje(@PathVariable("id") Long id, @RequestBody ViajeEntity viaje) {
    //     try {
    //         viaje.setIdViaje(id);
    //         ViajeEntity actualizado = viajeRepository.save(viaje);
    //         ViajeResponse respuesta = new ViajeResponse("Viaje actualizado exitosamente", actualizado);
    //         return new ResponseEntity<>(respuesta, HttpStatus.OK);
    //     } catch (Exception e) {
    //         ViajeResponse error = new ViajeResponse("Error al actualizar el viaje: " + e.getMessage(), null);
    //         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @PutMapping("/viajes/{id}")
    public ResponseEntity<ViajeResponse> finalizarViaje(@PathVariable("id") Long id) {
        try {
            // 1. Buscar el viaje por ID
            ViajeEntity viaje = viajeRepository.findById(id).orElse(null);

            if (viaje == null) {
                ViajeResponse error = new ViajeResponse(
                        "No existe un viaje con el id " + id,
                        null
                );
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

             if ("Finalizado".equalsIgnoreCase(viaje.getEstado())) {
                return new ResponseEntity<>(
                        new ViajeResponse("El viaje ya fue finalizado", viaje),
                        HttpStatus.CONFLICT
                );
            }

            viaje.setEstado("Finalizado");   
            viaje.setFechaHoraFin(new Date());
            TarifaEntity tarifa = tarifaRepository.buscarPorId(viaje.getIdTarifa());
            Double longitud= calcularLongitudTotal(viaje);
            Double costo= calcularCosto(longitud, tarifa);
            viaje.setLongitudTrayecto(longitud);
            viaje.setCostoTotal(costo);
            ViajeEntity actualizado = viajeRepository.save(viaje);

            ViajeResponse respuesta = new ViajeResponse(
                    "Viaje finalizado exitosamente",
                    actualizado
            );

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {
            ViajeResponse error = new ViajeResponse(
                    "Error al finalizar el viaje: " + e.getMessage(),
                    null
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // delete
    @DeleteMapping("/viajes/{id}/delete")
    public ResponseEntity<ViajeResponse> eliminarViaje(@PathVariable("id") Long id) {
        try {
            viajeRepository.eliminarViaje(id);
            ViajeResponse respuesta = new ViajeResponse("Viaje eliminado exitosamente", null);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            ViajeResponse error = new ViajeResponse("Error al eliminar el viaje: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // RFC1, historico de viajes por usuario
    @GetMapping("/usuarios/{id}/viajes")
    public ResponseEntity<Collection<ViajeEntity>> obtenerViajesPorUsuario(@PathVariable("id") Long idUsuario) {
        try {
            List<ViajeEntity> viajes = viajeRepository.buscarPorIdCliente(idUsuario);
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // RFC3: uso de servicios por ciudad y rango de fechas (yyyy-MM-dd)
    @GetMapping("/servicios/uso")
    public ResponseEntity<List<Map<String,Object>>> obtenerUsoServicios(
            @RequestParam("ciudad") String ciudad,
            @RequestParam("desde") String desdeStr,
            @RequestParam("hasta") String hastaStr) {
        try {
            LocalDate desdeLD = LocalDate.parse(desdeStr);
            LocalDate hastaLD = LocalDate.parse(hastaStr);
            Date desde = Date.from(desdeLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date hasta = Date.from(hastaLD.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<org.bson.Document> docs = usoServiciosRepository.obtenerUsoPorCiudadYFechas(ciudad, desde, hasta);
            List<Map<String,Object>> resultado = docs.stream().map(d -> Map.of(
                    "tipoServicio", d.get("tipoServicio"),
                    "nivel", d.get("nivel"),
                    "cantidad", d.get("cantidad"),
                    "porcentaje", d.get("porcentaje")
            )).toList();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public static class ViajeResponse {

        private String mensaje;
        private ViajeEntity viaje;

        public ViajeResponse(String mensaje, ViajeEntity viaje) {
            this.mensaje = mensaje;
            this.viaje = viaje;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public ViajeEntity getViaje() { return viaje; }
        public void setViaje(ViajeEntity viaje) { this.viaje = viaje; }
    }

    // funciones auxiliaressss
    String diaDeHoy() {
        LocalDate hoy = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.forLanguageTag("es"));
        String nombreDiaMinusculas = hoy.format(formatter);
        if (nombreDiaMinusculas == null || nombreDiaMinusculas.isEmpty()) {
            return "";
        }
        return nombreDiaMinusculas.substring(0, 1).toUpperCase() + nombreDiaMinusculas.substring(1);
    }

    private double calcularDistanciaKm(PuntoGeograficoEntity p1, PuntoGeograficoEntity p2) {

        final int RADIO_TIERRA_KM = 6371;

        double lat1 = Math.toRadians(p1.getLatitud());
        double lon1 = Math.toRadians(p1.getLongitud());
        double lat2 = Math.toRadians(p2.getLatitud());
        double lon2 = Math.toRadians(p2.getLongitud());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    private double calcularLongitudTotal(ViajeEntity viaje) {

        double total = 0.0;

        PuntoGeograficoEntity puntoActual = viaje.getPuntoOrigen();

        for (PuntoGeograficoEntity destino : viaje.getDestinos()) {
            total += calcularDistanciaKm(puntoActual, destino);
            puntoActual = destino;
        }

        return total;
    }

    private double calcularCosto(double longitudKm, TarifaEntity tarifa) {

        return longitudKm * tarifa.getPrecioPorKm();
    }



}