package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;

@RestController
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    // create
    @PostMapping("/viajes/new/save")
    public ResponseEntity<ViajeResponse> crearViaje(@RequestBody ViajeEntity viaje) {
        try {
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