package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;

@RestController
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // create
    @PostMapping("/vehiculos/new/save")
    public ResponseEntity<VehiculoResponse> crearVehiculo(@RequestBody VehiculoEntity vehiculo) {
        try {
            VehiculoEntity guardado = vehiculoRepository.save(vehiculo);
            VehiculoResponse respuesta = new VehiculoResponse("Vehículo creado exitosamente", guardado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            VehiculoResponse error = new VehiculoResponse("Error al crear el vehículo: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read
    @GetMapping("/vehiculos")
    public ResponseEntity<Collection<VehiculoEntity>> listarVehiculos() {
        try {
            List<VehiculoEntity> vehiculos = vehiculoRepository.buscarTodosLosVehiculos();
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // read
    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<VehiculoEntity> obtenerVehiculo(@PathVariable("id") Long id) {
        try {
            VehiculoEntity vehiculo = vehiculoRepository.buscarPorId(id);
            if (vehiculo != null) {
                return ResponseEntity.ok(vehiculo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // update
    @PutMapping("/vehiculos/{id}")
    public ResponseEntity<VehiculoResponse> actualizarVehiculo(@PathVariable("id") Long id, @RequestBody VehiculoEntity vehiculo) {
        try {
            vehiculo.setIdVehiculo(id);
            VehiculoEntity actualizado = vehiculoRepository.save(vehiculo);
            VehiculoResponse respuesta = new VehiculoResponse("Vehículo actualizado exitosamente", actualizado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            VehiculoResponse error = new VehiculoResponse("Error al actualizar el vehículo: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete
    @DeleteMapping("/vehiculos/{id}/delete")
    public ResponseEntity<VehiculoResponse> eliminarVehiculo(@PathVariable("id") Long id) {
        try {
            vehiculoRepository.eliminarVehiculo(id);
            VehiculoResponse respuesta = new VehiculoResponse("Vehículo eliminado exitosamente", null);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            VehiculoResponse error = new VehiculoResponse("Error al eliminar el vehículo: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class VehiculoResponse {
        private String mensaje;
        private VehiculoEntity vehiculo;

        public VehiculoResponse(String mensaje, VehiculoEntity vehiculo) {
            this.mensaje = mensaje;
            this.vehiculo = vehiculo;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public VehiculoEntity getVehiculo() { return vehiculo; }
        public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
    }
}
