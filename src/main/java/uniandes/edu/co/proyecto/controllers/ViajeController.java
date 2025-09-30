package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    // Listar todos los viajes
    @GetMapping
    public ResponseEntity<Collection<ViajeEntity>> listarViajes() {
        try {
            Collection<ViajeEntity> viajes = viajeRepository.darViajes();
            System.out.println(viajes);
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un viaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<ViajeEntity> obtenerViaje(@PathVariable Long id) {
        try {
            ViajeEntity viaje = viajeRepository.darViaje(id);
            if (viaje != null) {
                return ResponseEntity.ok(viaje);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo viaje
    @PostMapping("/new/save")
    public ResponseEntity<String> crearViaje(@RequestBody ViajeEntity viaje) {
        try {
            viajeRepository.insertarViaje(
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getLongitudTrayecto(),
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Viaje creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el viaje");
        }
    }

    // Actualizar viaje
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarViaje(@PathVariable Long id, @RequestBody ViajeEntity viaje) {
        try {
            viajeRepository.actualizarViaje(
                id,
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getLongitudTrayecto(),
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo()
            );
            return ResponseEntity.ok("Viaje actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el viaje");
        }
    }

    // Eliminar viaje
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarViaje(@PathVariable Long id) {
        try {
            viajeRepository.eliminarViaje(id);
            return ResponseEntity.ok("Viaje eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el viaje");
        }
    }
}
