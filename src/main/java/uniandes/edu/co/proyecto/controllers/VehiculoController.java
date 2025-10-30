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

    @PostMapping("/new")
    public ResponseEntity<String> registrarVehiculo(@RequestBody VehiculoEntity vehiculo, @RequestParam Long conductorId) {
        try {
            UsuarioEntity conductor = usuarioRepository.darUsuario(conductorId);

            if (conductor == null || !"Conductor".equals(conductor.getTipo())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no es un conductor válido");
            }

            // Ignorar cualquier ID enviado
            vehiculo.setIdVehiculo(null);
            VehiculoEntity vehiculoCreado = vehiculoRepository.save(vehiculo);

            conductorVehiculoRepository.insertarConductorVehiculo(conductor.getIdUsuario(), vehiculoCreado.getIdVehiculo());

            return ResponseEntity.status(HttpStatus.CREATED).body("Vehículo registrado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el vehículo");
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
