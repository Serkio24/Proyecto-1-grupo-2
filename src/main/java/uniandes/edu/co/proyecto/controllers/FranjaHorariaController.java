package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;
import uniandes.edu.co.proyecto.repositories.FranjaHorariaRepository;

@RestController
@RequestMapping("/franjas-horarias")
public class FranjaHorariaController {

    @Autowired
    private FranjaHorariaRepository franjaHorariaRepository;

    // Listar todas las franjas horarias
    @GetMapping
    public ResponseEntity<Collection<FranjaHorariaEntity>> listarFranjasHorarias() {
        try {
            Collection<FranjaHorariaEntity> franjas = franjaHorariaRepository.darFranjasHorarias();
            return ResponseEntity.ok(franjas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una franja horaria por ID
    @GetMapping("/{id}")
    public ResponseEntity<FranjaHorariaEntity> obtenerFranjaHoraria(@PathVariable Long id) {
        try {
            FranjaHorariaEntity franja = franjaHorariaRepository.darFranjaHoraria(id);
            if (franja != null) {
                return ResponseEntity.ok(franja);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nueva franja horaria
    @PostMapping("/new/save")
    public ResponseEntity<String> crearFranjaHoraria(@RequestBody FranjaHorariaEntity franja) {
        try {
            franjaHorariaRepository.insertarFranjaHoraria(
                    franja.getDiaSemana(),
                    franja.getHoraInicio(),
                    franja.getHoraFin(),
                    franja.getTipoServicio()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Franja horaria creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la franja horaria");
        }
    }

    // Actualizar franja horaria
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarFranjaHoraria(@PathVariable Long id, @RequestBody FranjaHorariaEntity franja) {
        try {
            franjaHorariaRepository.actualizarFranjaHoraria(
                    id,
                    franja.getDiaSemana(),
                    franja.getHoraInicio(),
                    franja.getHoraFin(),
                    franja.getTipoServicio()
            );
            return ResponseEntity.ok("Franja horaria actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la franja horaria");
        }
    }

    // Eliminar franja horaria
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFranjaHoraria(@PathVariable Long id) {
        try {
            franjaHorariaRepository.eliminarFranjaHoraria(id);
            return ResponseEntity.ok("Franja horaria eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la franja horaria");
        }
    }
}
