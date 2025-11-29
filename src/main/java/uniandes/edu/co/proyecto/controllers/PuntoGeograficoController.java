package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;

@RestController
@RequestMapping("/puntos-geograficos")
public class PuntoGeograficoController {

    @Autowired
    private PuntoGeograficoRepository puntoGeograficoRepository;

    // Listar todos los puntos geográficos
    @GetMapping
    public ResponseEntity<Collection<PuntoGeograficoEntity>> listarPuntosGeograficos() {
        try {
            Collection<PuntoGeograficoEntity> puntos = puntoGeograficoRepository.darPuntosGeograficos();
            return ResponseEntity.ok(puntos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un punto geográfico por ID
    @GetMapping("/{id}")
    public ResponseEntity<PuntoGeograficoEntity> obtenerPuntoGeografico(@PathVariable Long id) {
        try {
            PuntoGeograficoEntity punto = puntoGeograficoRepository.darPuntoGeografico(id);
            if (punto != null) {
                return ResponseEntity.ok(punto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo punto geográfico
    @PostMapping("/new/save")
    public ResponseEntity<String> crearPuntoGeografico(@RequestBody PuntoGeograficoEntity punto) {
        try {
            puntoGeograficoRepository.insertarPuntoGeografico(
                    punto.getNombre(),
                    punto.getLatitud(),
                    punto.getLongitud(),
                    punto.getDireccionAproximada(),
                    punto.getCiudad().getIdCiudad()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Punto geográfico creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el punto geográfico");
        }
    }

    // Actualizar punto geográfico
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarPuntoGeografico(@PathVariable Long id, @RequestBody PuntoGeograficoEntity punto) {
        try {
            puntoGeograficoRepository.actualizarPuntoGeografico(
                    id,
                    punto.getNombre(),
                    punto.getLatitud(),
                    punto.getLongitud(),
                    punto.getDireccionAproximada(),
                    punto.getCiudad().getIdCiudad()
            );
            return ResponseEntity.ok("Punto geográfico actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el punto geográfico");
        }
    }

    // Eliminar punto geográfico
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPuntoGeografico(@PathVariable Long id) {
        try {
            puntoGeograficoRepository.eliminarPuntoGeografico(id);
            return ResponseEntity.ok("Punto geográfico eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el punto geográfico");
        }
    }
}
