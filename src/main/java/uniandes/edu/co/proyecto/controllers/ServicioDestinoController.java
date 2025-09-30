package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ServicioDestinoEntity;
import uniandes.edu.co.proyecto.repositories.ServicioDestinoRepository;

@RestController
@RequestMapping("/serviciosDestinos")
public class ServicioDestinoController {

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;

    // ✅ Listar todos los destinos de servicios
    @GetMapping
    public ResponseEntity<Collection<ServicioDestinoEntity>> listarServicioDestinos() {
        try {
            Collection<ServicioDestinoEntity> destinos = servicioDestinoRepository.darServicioDestinos();
            return ResponseEntity.ok(destinos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Obtener un destino de servicio por ID de servicio
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDestinoEntity> obtenerServicioDestino(@PathVariable Long id) {
        try {
            ServicioDestinoEntity destino = servicioDestinoRepository.darServicioDestino(id);
            if (destino != null) {
                return ResponseEntity.ok(destino);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Crear un nuevo destino de servicio
    @PostMapping("/new/save")
    public ResponseEntity<String> crearServicioDestino(@RequestBody ServicioDestinoEntity servicioDestino) {
        try {
            servicioDestinoRepository.insertarServicioDestino(
                servicioDestino.getIdServicio().getIdServicio(),
                servicioDestino.getIdPuntoLlegada().getIdPunto()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Destino de servicio creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear el destino de servicio");
        }
    }

    // ✅ Actualizar el destino de un servicio (por idServicio)
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarServicioDestino(@PathVariable Long id,
                                                            @RequestBody ServicioDestinoEntity servicioDestino) {
        try {
            servicioDestinoRepository.actualizarServicioDestino(
                id,
                servicioDestino.getIdPuntoLlegada().getIdPunto()
            );
            return ResponseEntity.ok("Destino de servicio actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar el destino de servicio");
        }
    }

    // ✅ Eliminar el destino de un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarServicioDestino(@PathVariable Long id) {
        try {
            servicioDestinoRepository.eliminarServicioDestino(id);
            return ResponseEntity.ok("Destino de servicio eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar el destino de servicio");
        }
    }
}
