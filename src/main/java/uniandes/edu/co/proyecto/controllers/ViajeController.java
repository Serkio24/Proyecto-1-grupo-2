package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;
import uniandes.edu.co.proyecto.services.ViajeService;


@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    // Listar todos los viajes
    @GetMapping
    public ResponseEntity<Collection<ViajeEntity>> listarViajes() {
        try {
            Collection<ViajeEntity> viajes = viajeRepository.darViajes();
            System.out.println(viajes);
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un viaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<ViajeEntity> obtenerViaje(@PathVariable Long id) {
        try {
            ViajeEntity viaje = viajeRepository.darViaje(id);
            if (viaje != null) {
                return ResponseEntity.ok(viaje);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/new/save")
    public ResponseEntity<ViajeResponse> crearViaje(@RequestBody ViajeEntity viaje) {
        try {
            viajeRepository.insertarViaje(
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getLongitudTrayecto(),
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo(),
                viaje.getIdTarifa().getIdTarifa(),
                viaje.getCosto()
            );

            // Obtener el último viaje insertado
            ViajeEntity viajeGuardado = viajeRepository.darUltimoViaje();
            ViajeResponse respuesta = new ViajeResponse("Viaje creado exitosamente", viajeGuardado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            ViajeResponse error = new ViajeResponse("Error al crear el viaje", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar viaje
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarViaje(@PathVariable Long id, @RequestBody ViajeEntity viaje) {
        try {
            viajeRepository.actualizarViaje(
                id,
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getLongitudTrayecto(),
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo(),
                viaje.getIdTarifa().getIdTarifa(),
                viaje.getCosto()
            );
            return ResponseEntity.ok("Viaje actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el viaje");
        }
    }

    // Eliminar viaje
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarViaje(@PathVariable Long id) {
        try {
            viajeRepository.eliminarViaje(id);
            return ResponseEntity.ok("Viaje eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el viaje");
        }
    }

    // Terminar un viaje (RF9)
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ViajeResponse> terminarViaje(@PathVariable Long id) {
        try {
            ViajeEntity viajeFinalizado = viajeService.finalizarViaje(id);
            ViajeResponse exito = new ViajeResponse("Viaje finalizado correctamente", viajeFinalizado);
            return new ResponseEntity<>(exito, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ViajeResponse error = new ViajeResponse(e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            ViajeResponse error = new ViajeResponse(e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            ViajeResponse error = new ViajeResponse("Error al finalizar viaje", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public class ViajeResponse {

        private String mensaje;
        private ViajeEntity viaje;

        // Constructor
        public ViajeResponse(String mensaje, ViajeEntity viaje) {
            this.mensaje = mensaje;
            this.viaje = viaje;
        }

        // Getters y Setters
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public ViajeEntity getViaje() {
            return viaje;
        }

        public void setViaje(ViajeEntity viaje) {
            this.viaje = viaje;
        }
    }
}
