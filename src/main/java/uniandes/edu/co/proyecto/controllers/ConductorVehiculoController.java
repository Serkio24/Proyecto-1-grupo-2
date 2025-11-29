package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;
import uniandes.edu.co.proyecto.entities.ConductorVehiculoPK;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;

@RestController
@RequestMapping("/conductor-vehiculos")
public class ConductorVehiculoController {

    @Autowired
    private ConductorVehiculoRepository conductorVehiculoRepository;

    // Listar todos los registros
    @GetMapping
    public ResponseEntity<Collection<ConductorVehiculoEntity>> listarConductorVehiculos() {
        try {
            Collection<ConductorVehiculoEntity> registros = conductorVehiculoRepository.darConductorVehiculos();
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un registro por IDs
    @GetMapping("/{idConductor}/{idVehiculo}")
    public ResponseEntity<ConductorVehiculoEntity> obtenerConductorVehiculo(@PathVariable Long idConductor, @PathVariable Long idVehiculo) {
        try {
            ConductorVehiculoEntity registro = conductorVehiculoRepository.darConductorVehiculoPorId(idConductor, idVehiculo);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo registro
    @PostMapping("/new/save")
    public ResponseEntity<String> crearConductorVehiculo(@RequestBody ConductorVehiculoPK pk) {
        try {
            conductorVehiculoRepository.insertarConductorVehiculo(pk.getIdConductor().getIdUsuario(), pk.getIdVehiculo().getIdVehiculo());
            return ResponseEntity.status(HttpStatus.CREATED).body("Conductor-Vehículo creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el registro");
        }
    }

    // Actualizar registro
    @PostMapping("/{idConductor}/{idVehiculo}/edit/save")
    public ResponseEntity<String> actualizarConductorVehiculo(@PathVariable Long idConductor, @PathVariable Long idVehiculo, @RequestBody ConductorVehiculoPK pkNuevo) {
        try {
            conductorVehiculoRepository.actualizarConductorVehiculo(idConductor, idVehiculo, pkNuevo.getIdConductor().getIdUsuario(), pkNuevo.getIdVehiculo().getIdVehiculo());
            return ResponseEntity.ok("Conductor-Vehículo actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el registro");
        }
    }

    // Eliminar registro
    @DeleteMapping("/{idConductor}/{idVehiculo}")
    public ResponseEntity<String> eliminarConductorVehiculo(@PathVariable Long idConductor, @PathVariable Long idVehiculo) {
        try {
            conductorVehiculoRepository.eliminarConductorVehiculo(idConductor, idVehiculo);
            return ResponseEntity.ok("Conductor-Vehículo eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el registro");
        }
    }
}
