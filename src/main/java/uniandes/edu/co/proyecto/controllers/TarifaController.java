package uniandes.edu.co.proyecto.controllers;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;

@RestController
@RequestMapping("/tarifas")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Listar todas las tarifas
    @GetMapping
    public ResponseEntity<Collection<TarifaEntity>> listarTarifas() {
        try {
            Collection<TarifaEntity> tarifas = tarifaRepository.darTarifas();
            return ResponseEntity.ok(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una tarifa por ID
    @GetMapping("/{id}")
    public ResponseEntity<TarifaEntity> obtenerTarifa(@PathVariable Long id) {
        try {
            TarifaEntity tarifa = tarifaRepository.darTarifa(id);
            if (tarifa != null) {
                return ResponseEntity.ok(tarifa);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nueva tarifa
    @PostMapping("/new/save")
    public ResponseEntity<TarifaResponse> crearTarifa(@RequestBody TarifaEntity tarifa) {
        try {
            // Insertar la tarifa usando el repository
            tarifaRepository.insertarTarifa(
                tarifa.getTipoServicio(),
                tarifa.getNivel(),
                tarifa.getPrecioPorKm(),
                tarifa.getVigenciaDesde(),
                tarifa.getVigenciaHasta()
            );

            // Obtener la última tarifa insertada
            TarifaEntity tarifaGuardada = tarifaRepository.darUltimaTarifa(); // Método que retorna la última tarifa
            TarifaResponse respuesta = new TarifaResponse("Tarifa creada exitosamente", tarifaGuardada);

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            TarifaResponse error = new TarifaResponse("Error al crear la tarifa", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Actualizar una tarifa existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarTarifa(@PathVariable Long id, @RequestBody TarifaEntity tarifa) {
        try {
            tarifaRepository.actualizarTarifa(
                id,
                tarifa.getTipoServicio(),
                tarifa.getNivel(),
                tarifa.getPrecioPorKm(),
                tarifa.getVigenciaDesde() != null ? tarifa.getVigenciaDesde().format(FORMATTER) : null,
                tarifa.getVigenciaHasta() != null ? tarifa.getVigenciaHasta().format(FORMATTER) : null
            );
            return ResponseEntity.ok("Tarifa actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar la tarifa");
        }
    }

    // Eliminar una tarifa
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTarifa(@PathVariable Long id) {
        try {
            tarifaRepository.eliminarTarifa(id);
            return ResponseEntity.ok("Tarifa eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar la tarifa");
        }
    }

    public class TarifaResponse {
        private String mensaje;
        private TarifaEntity tarifa;

        public TarifaResponse(String mensaje, TarifaEntity tarifa) {
            this.mensaje = mensaje;
            this.tarifa = tarifa;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public TarifaEntity getTarifa() {
            return tarifa;
        }

        public void setTarifa(TarifaEntity tarifa) {
            this.tarifa = tarifa;
        }
    }
}
