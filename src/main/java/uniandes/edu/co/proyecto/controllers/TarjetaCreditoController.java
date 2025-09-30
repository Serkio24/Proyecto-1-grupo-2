package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.repositories.TarjetaCreditoRepository;

@RestController
@RequestMapping("/tarjetas-credito")
public class TarjetaCreditoController {

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    // Listar todas las tarjetas
    @GetMapping
    public ResponseEntity<Collection<TarjetaCreditoEntity>> listarTarjetas() {
        try {
            Collection<TarjetaCreditoEntity> tarjetas = tarjetaCreditoRepository.darTarjetas();
            return ResponseEntity.ok(tarjetas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una tarjeta por ID
    @GetMapping("/{id}")
    public ResponseEntity<TarjetaCreditoEntity> obtenerTarjeta(@PathVariable Long id) {
        try {
            TarjetaCreditoEntity tarjeta = tarjetaCreditoRepository.darTarjeta(id);
            if (tarjeta != null) {
                return ResponseEntity.ok(tarjeta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nueva tarjeta
    @PostMapping("/new/save")
    public ResponseEntity<String> crearTarjeta(@RequestBody TarjetaCreditoEntity tarjeta) {
        try {
            // ⚠️ Si el cliente es obligatorio, se debe pasar su ID aquí
            Long clienteId = (tarjeta.getCliente() != null) ? tarjeta.getCliente().getIdUsuario() : null;

            tarjetaCreditoRepository.insertarTarjeta(
                tarjeta.getTitularDeLaTarjeta(),
                tarjeta.getNumeroTarjeta(),
                tarjeta.getFechaExpiracion(),
                tarjeta.getCodigoSeguridad().toString()  // OJO: si en el repo cambias a Integer, quita el toString()
                // ,clienteId   // si agregas el FK en el query
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Tarjeta de crédito creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la tarjeta de crédito");
        }
    }

    // Actualizar tarjeta
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarTarjeta(@PathVariable Long id, @RequestBody TarjetaCreditoEntity tarjeta) {
        try {
            tarjetaCreditoRepository.actualizarTarjeta(
                id,
                tarjeta.getTitularDeLaTarjeta(),
                tarjeta.getNumeroTarjeta(),
                tarjeta.getFechaExpiracion(),
                tarjeta.getCodigoSeguridad().toString()
            );
            return ResponseEntity.ok("Tarjeta de crédito actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la tarjeta de crédito");
        }
    }

    // Eliminar tarjeta
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTarjeta(@PathVariable Long id) {
        try {
            tarjetaCreditoRepository.eliminarTarjeta(id);
            return ResponseEntity.ok("Tarjeta de crédito eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la tarjeta de crédito");
        }
    }
}
