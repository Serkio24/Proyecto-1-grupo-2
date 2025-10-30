package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.ReviewEntity;
import uniandes.edu.co.proyecto.repositories.ReviewRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // Listar todas las reviews
    @GetMapping
    public ResponseEntity<Collection<ReviewEntity>> listarReviews() {
        try {
            Collection<ReviewEntity> reviews = reviewRepository.darReviews();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una review por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewEntity> obtenerReview(@PathVariable Long id) {
        try {
            ReviewEntity review = reviewRepository.darReview(id);
            if (review != null) {
                return ResponseEntity.ok(review);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nueva review
    @PostMapping("/new/save")
    public ResponseEntity<String> crearReview(@RequestBody ReviewEntity review) {
        try {
            reviewRepository.insertarReview(
                    review.getUsuarioCalificador().getIdUsuario(),
                    review.getUsuarioCalificado().getIdUsuario(),
                    review.getViaje().getIdViaje(),
                    review.getPuntuacion(),
                    review.getComentario(),
                    review.getFechaRevision()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Review creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la review");
        }
    }

    // Actualizar review
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarReview(@PathVariable Long id, @RequestBody ReviewEntity review) {
        try {
            reviewRepository.actualizarReview(
                    id,
                    review.getUsuarioCalificador().getIdUsuario(),
                    review.getUsuarioCalificado().getIdUsuario(),
                    review.getViaje().getIdViaje(),
                    review.getPuntuacion(),
                    review.getComentario(),
                    review.getFechaRevision()
            );
            return ResponseEntity.ok("Review actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la review");
        }
    }

    // Eliminar review
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarReview(@PathVariable Long id) {
        try {
            reviewRepository.eliminarReview(id);
            return ResponseEntity.ok("Review eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la review");
        }
    }

    
    // RF10: Pasajero califica conductor
    @PostMapping("/pasajero")
    public ResponseEntity<String> crearReviewRF10(@RequestBody ReviewEntity review) {
        try {
            review.setFechaRevision(java.time.LocalDateTime.now());
            reviewRepository.insertarReview(
                    review.getUsuarioCalificador().getIdUsuario(), // pasajero
                    review.getUsuarioCalificado().getIdUsuario(),  // conductor
                    review.getViaje().getIdViaje(),
                    review.getPuntuacion(),
                    review.getComentario(),
                    review.getFechaRevision()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Review RF10 creada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear review RF10");
        }
    }

    // RF11: Conductor califica pasajero
    @PostMapping("/conductor")
    public ResponseEntity<String> crearReviewRF11(@RequestBody ReviewEntity review) {
        try {
            review.setFechaRevision(java.time.LocalDateTime.now());
            reviewRepository.insertarReview(
                    review.getUsuarioCalificador().getIdUsuario(), // conductor
                    review.getUsuarioCalificado().getIdUsuario(),  // pasajero
                    review.getViaje().getIdViaje(),
                    review.getPuntuacion(),
                    review.getComentario(),
                    review.getFechaRevision()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Review RF11 creada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear review RF11");
        }
    }
}

