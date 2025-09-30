package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // ✅ Listar todos los vehículos
    @GetMapping
    public ResponseEntity<Collection<VehiculoEntity>> listarVehiculos() {
        try {
            Collection<VehiculoEntity> vehiculos = vehiculoRepository.darVehiculos();
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Obtener un vehículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoEntity> obtenerVehiculo(@PathVariable Long id) {
        try {
            VehiculoEntity vehiculo = vehiculoRepository.darVehiculo(id);
            if (vehiculo != null) {
                return ResponseEntity.ok(vehiculo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Crear un nuevo vehículo
    @PostMapping("/new/save")
    public ResponseEntity<String> crearVehiculo(@RequestBody VehiculoEntity vehiculo) {
        try {
            vehiculoRepository.insertarVehiculo(
                vehiculo.getTipo(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getColor(),
                vehiculo.getPlaca(),
                vehiculo.getCiudadExpedicion(),
                vehiculo.getCapacidadPasajeros() != null ? vehiculo.getCapacidadPasajeros().longValue() : null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Vehículo creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el vehículo");
        }
    }

    // ✅ Actualizar un vehículo
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarVehiculo(@PathVariable Long id, @RequestBody VehiculoEntity vehiculo) {
        try {
            vehiculoRepository.actualizarVehiculo(
                id,
                vehiculo.getTipo(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getColor(),
                vehiculo.getPlaca(),
                vehiculo.getCiudadExpedicion(),
                vehiculo.getCapacidadPasajeros() != null ? vehiculo.getCapacidadPasajeros().longValue() : null
            );
            return ResponseEntity.ok("Vehículo actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el vehículo");
        }
    }

    // ✅ Eliminar un vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable Long id) {
        try {
            vehiculoRepository.eliminarVehiculo(id);
            return ResponseEntity.ok("Vehículo eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el vehículo");
        }
    }
}
