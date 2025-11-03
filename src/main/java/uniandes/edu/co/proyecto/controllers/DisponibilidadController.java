package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.ActualizarDisponibilidadDTO;
import uniandes.edu.co.proyecto.dto.RegistroDisponibilidadDTO;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadPK;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.services.DisponibilidadService;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private DisponibilidadService disponibilidadService;

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
    @PostMapping("/new")
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
    public boolean validarTraslape(
            @RequestParam Long idConductor,
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin) {
        return disponibilidadRepository.validarTraslape(idConductor, diaSemana, horaInicio, horaFin)>0;
    }

    

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDisponibilidad(@RequestBody RegistroDisponibilidadDTO request) {
        disponibilidadService.registrarDisponibilidad(
            request.getIdConductor(),
            request.getDiaSemana(),
            request.getHoraInicio(),
            request.getHoraFin(),
            request.getTipoServicio()
        );
        return ResponseEntity.ok("Disponibilidad registrada correctamente");
    }

    @PostMapping("/modificar")
    public ResponseEntity<?> modificarDisponibilidad(@RequestBody ActualizarDisponibilidadDTO request) {
        try {
            disponibilidadService.modificarDisponibilidad(
                request.getIdConductor(),
                request.getIdVehiculoAnt(),
                request.getIdFranjaAnt(),
                request.getDiaSemana(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getTipoServicio(),
                request.getIdVehiculoNuevo()
            );
            return ResponseEntity.ok("Disponibilidad modificada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}


