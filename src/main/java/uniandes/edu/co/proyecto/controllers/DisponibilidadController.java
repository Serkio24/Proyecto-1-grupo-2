package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;

@RestController
public class DisponibilidadController {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @PostMapping("/disponibilidades/new/save")
    public ResponseEntity<DisponibilidadResponse> crearDisponibilidad(
            @RequestBody DisponibilidadEntity disponibilidad) {

        try {
            
            Long idConductor = disponibilidad.getIdConductor();

            for (FranjaHorariaEntity franja : disponibilidad.getFranjasHorarias()) {

                List<DisponibilidadEntity> conflictos =
                        disponibilidadRepository.validarTraslapeConductor(
                                idConductor,
                                franja.getDiaSemana(),
                                franja.getHoraInicio(),
                                franja.getHoraFin()
                        );

                if (!conflictos.isEmpty()) {
                    DisponibilidadResponse error = new DisponibilidadResponse(
                            "Traslape detectado para el día " + franja.getDiaSemana()
                            + " entre " + franja.getHoraInicio()
                            + " y " + franja.getHoraFin(),
                            null
                    );
                    return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409
                }
            }

            
            DisponibilidadEntity ultima = disponibilidadRepository.findTopByOrderByIdDesc();
            Long nuevoId = (ultima == null || ultima.getId() == null) ? 1L : ultima.getId() + 1;
            disponibilidad.setId(nuevoId);

            DisponibilidadEntity guardada = disponibilidadRepository.save(disponibilidad);

            DisponibilidadResponse respuesta =
                    new DisponibilidadResponse("Disponibilidad creada exitosamente", guardada);

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (Exception e) {

            DisponibilidadResponse error =
                    new DisponibilidadResponse("Error al crear la disponibilidad: " + e.getMessage(), null);

            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}


    // read
    @GetMapping("/disponibilidades")
    public ResponseEntity<Collection<DisponibilidadEntity>> obtenerTodasLasDisponibilidades() {
        try {
            List<DisponibilidadEntity> registros = disponibilidadRepository.buscarTodasLasDisponibilidades();
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // read
    @GetMapping("/disponibilidades/{id}")
    public ResponseEntity<DisponibilidadEntity> obtenerDisponibilidadPorId(@PathVariable("id") Long id) {
        try {
            DisponibilidadEntity registro = disponibilidadRepository.buscarPorId(id);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // update
    @PutMapping("/disponibilidades/{id}")
    public ResponseEntity<DisponibilidadResponse> actualizarDisponibilidad(@PathVariable("id") Long id, @RequestBody DisponibilidadEntity disponibilidad) {
        try {
            // asegúrate de que DisponibilidadEntity tenga este setter con el nombre correcto
            disponibilidad.setId(id);
            DisponibilidadEntity actualizada = disponibilidadRepository.save(disponibilidad);
            DisponibilidadResponse respuesta = new DisponibilidadResponse("Disponibilidad actualizada exitosamente", actualizada);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            DisponibilidadResponse error = new DisponibilidadResponse("Error al actualizar la disponibilidad: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete
    @DeleteMapping("/disponibilidades/{id}/delete")
    public ResponseEntity<DisponibilidadResponse> eliminarDisponibilidad(@PathVariable("id") Long id) {
        try {
            disponibilidadRepository.eliminarDisponibilidadPorId(id);
            DisponibilidadResponse respuesta = new DisponibilidadResponse("Disponibilidad eliminada exitosamente", null);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            DisponibilidadResponse error = new DisponibilidadResponse("Error al eliminar la disponibilidad: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class DisponibilidadResponse {
        private String mensaje;
        private DisponibilidadEntity disponibilidad;

        public DisponibilidadResponse(String mensaje, DisponibilidadEntity disponibilidad) {
            this.mensaje = mensaje;
            this.disponibilidad = disponibilidad;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public DisponibilidadEntity getDisponibilidad() { return disponibilidad; }
        public void setDisponibilidad(DisponibilidadEntity disponibilidad) { this.disponibilidad = disponibilidad; }
    }
}
