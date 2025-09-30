package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private CiudadRepository ciudadRepository;

    // Listar todas las ciudades
    @GetMapping
    public ResponseEntity<Collection<CiudadEntity>> listarCiudades() {
        try {
            Collection<CiudadEntity> ciudades = ciudadRepository.darCiudades();
            return ResponseEntity.ok(ciudades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una ciudad por ID
    @GetMapping("/{id}")
    public ResponseEntity<CiudadEntity> obtenerCiudad(@PathVariable Long id) {
        try {
            CiudadEntity ciudad = ciudadRepository.darCiudad(id);
            if (ciudad != null) {
                return ResponseEntity.ok(ciudad);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // RF1: Registrar una ciudad
    @PostMapping("/registrar")
    public ResponseEntity<CiudadEntity> registrarCiudad(@RequestBody CiudadEntity ciudad) {
        try {
            // Guardar usando JPA: Hibernate asigna ID automáticamente
            CiudadEntity ciudadGuardada = ciudadRepository.save(ciudad);
            // Devuelve la entidad completa como JSON
            return ResponseEntity.status(HttpStatus.CREATED).body(ciudadGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar ciudad
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarCiudad(@PathVariable Long id, @RequestBody CiudadEntity ciudad) {
        try {
            ciudadRepository.actualizarCiudad(id, ciudad.getNombre());
            return ResponseEntity.ok("Ciudad actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la ciudad");
        }
    }

    // Eliminar ciudad
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCiudad(@PathVariable Long id) {
        try {
            ciudadRepository.eliminarCiudad(id);
            return ResponseEntity.ok("Ciudad eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ciudad");
        }
    }

    @GetMapping("/next-id")
    public ResponseEntity<Long> obtenerNextId() {
        try {
            Long siguienteId = ciudadRepository.obtenerNextval();
            return ResponseEntity.ok(siguienteId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

