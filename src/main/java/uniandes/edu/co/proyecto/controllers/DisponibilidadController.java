package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadPK;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    // Listar todas las disponibilidades
    @GetMapping
    public ResponseEntity<Collection<DisponibilidadEntity>> listarDisponibilidades() {
        try {
            Collection<DisponibilidadEntity> registros = disponibilidadRepository.darDisponibilidades();
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una disponibilidad por IDs
    @GetMapping("/{idVehiculo}/{idFranja}")
    public ResponseEntity<DisponibilidadEntity> obtenerDisponibilidad(@PathVariable Long idVehiculo, @PathVariable Long idFranja) {
        try {
            DisponibilidadEntity registro = disponibilidadRepository.darDisponibilidad(idVehiculo, idFranja);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nueva disponibilidad
    @PostMapping("/new/save")
    public ResponseEntity<String> crearDisponibilidad(@RequestBody DisponibilidadPK pk) {
        try {
            disponibilidadRepository.insertarDisponibilidad(pk.getVehiculo().getIdVehiculo(), pk.getFranja().getIdFranja());
            return ResponseEntity.status(HttpStatus.CREATED).body("Disponibilidad creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la disponibilidad");
        }
    }

    // Actualizar disponibilidad
    @PostMapping("/{idVehiculo}/{idFranja}/edit/save")
    public ResponseEntity<String> actualizarDisponibilidad(@PathVariable Long idVehiculo, @PathVariable Long idFranja, @RequestBody DisponibilidadPK pkNuevo) {
        try {
            disponibilidadRepository.actualizarDisponibilidad(
                    idVehiculo, idFranja,
                    pkNuevo.getVehiculo().getIdVehiculo(), pkNuevo.getFranja().getIdFranja()
            );
            return ResponseEntity.ok("Disponibilidad actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la disponibilidad");
        }
    }

    // Eliminar disponibilidad
    @DeleteMapping("/{idVehiculo}/{idFranja}")
    public ResponseEntity<String> eliminarDisponibilidad(@PathVariable Long idVehiculo, @PathVariable Long idFranja) {
        try {
            disponibilidadRepository.eliminarDisponibilidad(idVehiculo, idFranja);
            return ResponseEntity.ok("Disponibilidad eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la disponibilidad");
        }
    }

        // Validar traslape de disponibilidad
    @GetMapping("/validar-traslape")
    public ResponseEntity<?> validarTraslape(
            @RequestParam Long idConductor,
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin) {
        try {
            // Llamar al método del repository
            var resultados = disponibilidadRepository.validarTraslape(
                    idConductor, diaSemana, horaInicio, horaFin);

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al validar traslape: " + e.getMessage());
        }
    }

    // @PostMapping("/registrar")
    // public ResponseEntity<String> registrarDisponibilidad(
    //         @RequestParam Long idConductor,
    //         @RequestParam Long idVehiculo,
    //         @RequestParam Long idFranja,
    //         @RequestParam String diaSemana,
    //         @RequestParam String horaInicio,
    //         @RequestParam String horaFin) {

    //     try {
    //         // 1. Validar traslape
    //         var traslapes = disponibilidadRepository.validarTraslape(
    //                 idConductor, diaSemana, horaInicio, horaFin);

    //         if (!traslapes.isEmpty()) {
    //             return ResponseEntity
    //                     .status(HttpStatus.CONFLICT)
    //                     .body("Ya existe una disponibilidad que se superpone para este conductor en ese horario.");
    //         }

    //         // 2. Insertar disponibilidad
    //         disponibilidadRepository.insertarDisponibilidad(idVehiculo, idFranja);

    //         return ResponseEntity
    //                 .status(HttpStatus.CREATED)
    //                 .body("Disponibilidad registrada exitosamente");

    //     } catch (Exception e) {
    //         return ResponseEntity
    //                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Error al registrar la disponibilidad: " + e.getMessage());
    //     }
    // }

}
