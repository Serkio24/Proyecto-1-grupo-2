package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;

@RestController
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    // create
    @PostMapping("/tarifas/new/save")
    public ResponseEntity<TarifaResponse> crearTarifa(@RequestBody TarifaEntity tarifa) {
        try {
            TarifaEntity ultima = tarifaRepository.findTopByOrderByIdTarifaDesc();

            Long nuevoId;
            if (ultima == null || ultima.getIdTarifa() == null) {
                nuevoId = 1L;
            } else {
                nuevoId = ultima.getIdTarifa() + 1;
            }

            tarifa.setIdTarifa(nuevoId);

            TarifaEntity guardada = tarifaRepository.save(tarifa);
            TarifaResponse respuesta = new TarifaResponse("Tarifa creada exitosamente", guardada);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            TarifaResponse error = new TarifaResponse("Error al crear la tarifa: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // read
    @GetMapping("/tarifas")
    public ResponseEntity<Collection<TarifaEntity>> listarTarifas() {
        try {
            List<TarifaEntity> tarifas = tarifaRepository.buscarTodasLasTarifas();
            return ResponseEntity.ok(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // read
    @GetMapping("/tarifas/{id}")
    public ResponseEntity<TarifaEntity> obtenerTarifa(@PathVariable("id") Long id) {
        try {
            TarifaEntity tarifa = tarifaRepository.buscarPorId(id);
            if (tarifa != null) {
                return ResponseEntity.ok(tarifa);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // update
    @PutMapping("/tarifas/{id}")
    public ResponseEntity<TarifaResponse> actualizarTarifa(@PathVariable("id") Long id, @RequestBody TarifaEntity tarifa) {
        try {
            tarifa.setIdTarifa(id);
            TarifaEntity actualizada = tarifaRepository.save(tarifa);
            TarifaResponse respuesta = new TarifaResponse("Tarifa actualizada exitosamente", actualizada);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            TarifaResponse error = new TarifaResponse("Error al actualizar la tarifa: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete
    @DeleteMapping("/tarifas/{id}/delete")
    public ResponseEntity<TarifaResponse> eliminarTarifa(@PathVariable("id") Long id) {
        try {
            tarifaRepository.eliminarTarifa(id);
            TarifaResponse respuesta = new TarifaResponse("Tarifa eliminada exitosamente", null);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            TarifaResponse error = new TarifaResponse("Error al eliminar la tarifa: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class TarifaResponse {
        private String mensaje;
        private TarifaEntity tarifa;

        public TarifaResponse(String mensaje, TarifaEntity tarifa) {
            this.mensaje = mensaje;
            this.tarifa = tarifa;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public TarifaEntity getTarifa() { return tarifa; }
        public void setTarifa(TarifaEntity tarifa) { this.tarifa = tarifa; }
    }
}
