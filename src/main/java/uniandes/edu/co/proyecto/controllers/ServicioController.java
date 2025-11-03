package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.SolicitudServicioDTO;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.services.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioService servicioService;

    // ✅ Listar todos los servicios
    @GetMapping
    public ResponseEntity<Collection<ServicioEntity>> listarServicios() {
        try {
            Collection<ServicioEntity> servicios = servicioRepository.darServicios();
            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Solicitar un servicio (usa el Service)
    @PostMapping("/acciones/solicitar")
    public ResponseEntity<String> solicitarServicio(@RequestBody SolicitudServicioDTO solicitud) {
        try {
            servicioService.solicitarServicio(
                solicitud.getIdUsuario(),
                solicitud.getTipoServicio(),
                solicitud.getNivelRequerido(),
                solicitud.getIdPuntoPartida(),
                solicitud.getDestinos(),
                solicitud.getOrden(),
                solicitud.getRestaurante()
            );
            return ResponseEntity.ok("✅ Servicio solicitado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("❌ Error al solicitar el servicio: " + e.getMessage());
        }
    }

    // ✅ Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicioEntity> obtenerServicio(@PathVariable Long id) {
        try {
            ServicioEntity servicio = servicioRepository.darServicio(id);
            if (servicio != null) {
                return ResponseEntity.ok(servicio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear servicio
    @PostMapping("/new/save")
    public ResponseEntity<ServicioResponse> crearServicio(@RequestBody ServicioEntity servicio) {
        try {
            servicioRepository.insertarServicio(
                servicio.getIdCliente().getIdUsuario(),
                servicio.getFechaHora(),  // ← si es String, se mantiene así
                servicio.getTipoServicio(),
                servicio.getNivelRequerido(),
                servicio.getEstado(),
                servicio.getOrden(),
                servicio.getRestaurante(),
                servicio.getIdPuntoPartida().getIdPunto()
            );

            // Recuperar el servicio recién creado
            ServicioEntity servicioGuardado = servicioRepository.darUltimoServicio();
            ServicioResponse respuesta = new ServicioResponse("Servicio creado exitosamente", servicioGuardado);

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (Exception e) {
            ServicioResponse error = new ServicioResponse("Error al crear el servicio", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarServicio(@PathVariable Long id,
                                                     @RequestBody ServicioEntity servicio) {
        try {
            servicioRepository.actualizarServicio(
                id,
                servicio.getIdCliente().getIdUsuario(),
                servicio.getFechaHora(),
                servicio.getTipoServicio(),
                servicio.getNivelRequerido(),
                servicio.getEstado(),
                servicio.getOrden(),
                servicio.getRestaurante(),
                servicio.getIdPuntoPartida().getIdPunto()
            );
            return ResponseEntity.ok("Servicio actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar el servicio: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarServicio(@PathVariable Long id) {
        try {
            servicioRepository.eliminarServicio(id);
            return ResponseEntity.ok("Servicio eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar el servicio: " + e.getMessage());
        }
    }

    public class ServicioResponse {
        private String mensaje;
        private ServicioEntity servicio;

        public ServicioResponse(String mensaje, ServicioEntity servicio) {
            this.mensaje = mensaje;
            this.servicio = servicio;
        }

        // Getters y setters
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public ServicioEntity getServicio() { return servicio; }
        public void setServicio(ServicioEntity servicio) { this.servicio = servicio; }
    }
}
