package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.services.VehiculoService;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<Collection<VehiculoEntity>> listarVehiculos() {
        try {
            Collection<VehiculoEntity> vehiculos = vehiculoRepository.darVehiculos();
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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

    // Registrar un vehículo para un usuario conductor (RF4)
    @PostMapping("/new")
    public ResponseEntity<VehiculoResponse> registrarVehiculo(
            @RequestBody VehiculoEntity vehiculo,
            @RequestParam Long conductorId) {

        try {
            VehiculoEntity vehiculoCreado = vehiculoService.registrarVehiculoParaConductor(vehiculo, conductorId);
            VehiculoResponse respuesta = new VehiculoResponse("Vehículo registrado exitosamente", vehiculoCreado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            VehiculoResponse error = new VehiculoResponse(e.getReason(), null);
            return new ResponseEntity<>(error, e.getStatusCode());
        } catch (Exception e) {
            VehiculoResponse error = new VehiculoResponse("Error inesperado al registrar el vehículo", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable Long id) {
        try {
            vehiculoRepository.eliminarVehiculo(id);
            return ResponseEntity.ok("Vehículo eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el vehículo");
        }
    }

    public class VehiculoResponse {
        private String mensaje;
        private VehiculoEntity vehiculo;

        public VehiculoResponse(String mensaje, VehiculoEntity vehiculo) {
            this.mensaje = mensaje;
            this.vehiculo = vehiculo;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public VehiculoEntity getVehiculo() {
            return vehiculo;
        }

        public void setVehiculo(VehiculoEntity vehiculo) {
            this.vehiculo = vehiculo;
        }
    }

}

