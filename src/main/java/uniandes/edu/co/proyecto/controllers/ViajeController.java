package uniandes.edu.co.proyecto.controllers;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;


@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    ViajeController(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
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

    // Crear nuevo viaje
    @PostMapping("/new/save")
    public ResponseEntity<String> crearViaje(@RequestBody ViajeEntity viaje) {
        try {
            viajeRepository.insertarViaje(
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getLongitudTrayecto(),
                viaje.getIdServicio().getIdServicio(),
                viaje.getIdConductor().getIdUsuario(),
                viaje.getIdVehiculo().getIdVehiculo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Viaje creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el viaje");
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
                viaje.getIdVehiculo().getIdVehiculo()
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
    public ResponseEntity<ViajeResponse> terminarViaje(@PathVariable Long id, @RequestParam Double longitud){
        try {
            ViajeEntity viaje = viajeRepository.darViaje(id);
            ServicioEntity servicio = viaje.getIdServicio();
            VehiculoEntity vehiculo = viaje.getIdVehiculo();
            if (!"Asignado".equals(servicio.getEstado())){
                ViajeResponse error = new ViajeResponse("El servicio no ha sido asignado, no puede finalizar", null);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            servicio.setEstado("Finalizado");
            servicioRepository.actualizarServicio(servicio.getIdServicio(), servicio.getIdCliente().getIdUsuario(), servicio.getFechaHora(), servicio.getTipoServicio(), servicio.getNivelRequerido(), "Finalizado", servicio.getOrden(), servicio.getRestaurante(), servicio.getIdPuntoPartida().getIdPunto());
            LocalDateTime fin = LocalDateTime.now();
            viaje.setFechaHoraFin(fin);
            viaje.setLongitudTrayecto(longitud);

            viajeRepository.actualizarViaje(viaje.getIdViaje(), viaje.getFechaHoraInicio(), fin, longitud, viaje.getIdServicio().getIdServicio(), viaje.getIdConductor().getIdUsuario(), viaje.getIdVehiculo().getIdVehiculo());

            for (DisponibilidadEntity disponibilidad : disponibilidadRepository.darDisponibilidadesVehiculo(vehiculo.getIdVehiculo())){
                if(!disponibilidad.isDisponible()){
                    disponibilidad.setDisponible(true);
                    Long idVehiculo = disponibilidad.getPk().getVehiculo().getIdVehiculo();
                    Long idFranja = disponibilidad.getPk().getFranja().getIdFranja();
                    disponibilidadRepository.actualizarDisponibilidadFranja(idVehiculo, idFranja, true);
                }
            }
            ViajeResponse exito = new ViajeResponse("Viaje finalizado correctamente", viaje);
            return new ResponseEntity<>(exito, HttpStatus.OK);
        } catch (Exception e){
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
