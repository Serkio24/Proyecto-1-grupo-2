package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConductorVehiculoRepository conductorVehiculoRepository;

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
    public ResponseEntity<VehiculoResponse> registrarVehiculo(@RequestBody VehiculoEntity vehiculo, @RequestParam Long conductorId) {
        try {
            // Validar que el conductor exista y sea del tipo correcto
            UsuarioEntity conductor = usuarioRepository.darUsuario(conductorId);
            if (conductor == null || !"Conductor".equals(conductor.getTipo())) {
                VehiculoResponse error = new VehiculoResponse("El usuario no es un conductor", null);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            // Insertar vehículo usando el método nativo del repository
            vehiculoRepository.insertarVehiculo(
                    vehiculo.getTipo(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo(),
                    vehiculo.getColor(),
                    vehiculo.getPlaca(),
                    vehiculo.getCiudadExpedicion(),
                    vehiculo.getCapacidadPasajeros()
            );

            // Obtener el último vehículo insertado
            VehiculoEntity vehiculoCreado = vehiculoRepository.darUltimoVehiculo();

            // Registrar la relación conductor-vehículo
            conductorVehiculoRepository.insertarConductorVehiculo(conductor.getIdUsuario(), vehiculoCreado.getIdVehiculo());

            VehiculoResponse respuesta = new VehiculoResponse("Vehículo registrado exitosamente", vehiculoCreado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (Exception e) {
            VehiculoResponse error = new VehiculoResponse("Error al registrar el vehículo", null);
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

