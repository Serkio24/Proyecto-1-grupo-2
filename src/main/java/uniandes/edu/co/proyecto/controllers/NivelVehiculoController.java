package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.NivelVehiculoEntity;
import uniandes.edu.co.proyecto.entities.NivelVehiculoPK;
import uniandes.edu.co.proyecto.repositories.NivelVehiculoRepository;

@RestController
@RequestMapping("/niveles-vehiculo")
public class NivelVehiculoController {

    @Autowired
    private NivelVehiculoRepository nivelVehiculoRepository;

    // Listar todos los niveles de vehículo
    @GetMapping
    public ResponseEntity<Collection<NivelVehiculoEntity>> listarNivelesVehiculo() {
        try {
            Collection<NivelVehiculoEntity> niveles = nivelVehiculoRepository.darNivelesVehiculo();
            return ResponseEntity.ok(niveles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un nivel de vehículo por PK
    @GetMapping("/{modelo}/{capacidadPasajeros}")
    public ResponseEntity<NivelVehiculoEntity> obtenerNivelVehiculo(@PathVariable String modelo, @PathVariable int capacidadPasajeros) {
        try {
            NivelVehiculoEntity nivel = nivelVehiculoRepository.darNivelVehiculo(modelo, capacidadPasajeros);
            if (nivel != null) {
                return ResponseEntity.ok(nivel);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo nivel de vehículo
    @PostMapping("/new/save")
    public ResponseEntity<String> crearNivelVehiculo(@RequestBody NivelVehiculoEntity nivel) {
        try {
            NivelVehiculoPK pk = nivel.getPk();
            nivelVehiculoRepository.insertarNivelVehiculo(
                    pk.getModelo(),
                    pk.getCapacidadPasajeros(),
                    nivel.getNivel()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Nivel de vehículo creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el nivel de vehículo");
        }
    }

    // Actualizar nivel de vehículo
    @PostMapping("/{modelo}/{capacidadPasajeros}/edit/save")
    public ResponseEntity<String> actualizarNivelVehiculo(@PathVariable String modelo, @PathVariable int capacidadPasajeros, @RequestBody NivelVehiculoEntity nivel) {
        try {
            nivelVehiculoRepository.actualizarNivelVehiculo(
                    modelo,
                    capacidadPasajeros,
                    nivel.getNivel()
            );
            return ResponseEntity.ok("Nivel de vehículo actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el nivel de vehículo");
        }
    }

    // Eliminar nivel de vehículo
    @DeleteMapping("/{modelo}/{capacidadPasajeros}")
    public ResponseEntity<String> eliminarNivelVehiculo(@PathVariable String modelo, @PathVariable int capacidadPasajeros) {
        try {
            nivelVehiculoRepository.eliminarNivelVehiculo(modelo, capacidadPasajeros);
            return ResponseEntity.ok("Nivel de vehículo eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el nivel de vehículo");
        }
    }
}

