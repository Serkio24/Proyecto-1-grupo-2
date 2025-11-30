package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;
import uniandes.edu.co.proyecto.repositories.UsoServiciosRepository;

@RestController
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsoServiciosRepository usoServiciosRepository;

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
    @PutMapping("/viajes/{id}")
    public ResponseEntity<ViajeResponse> actualizarViaje(@PathVariable("id") Long id, @RequestBody ViajeEntity viaje) {
        try {
            viaje.setIdViaje(id);
            ViajeEntity actualizado = viajeRepository.save(viaje);
            ViajeResponse respuesta = new ViajeResponse("Viaje actualizado exitosamente", actualizado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            ViajeResponse error = new ViajeResponse("Error al actualizar el viaje: " + e.getMessage(), null);
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
}