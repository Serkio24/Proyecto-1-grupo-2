package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.ReviewDTO;
import uniandes.edu.co.proyecto.entities.ReviewEntity;
import uniandes.edu.co.proyecto.repositories.ReviewRepository;
import uniandes.edu.co.proyecto.services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

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

    //Crear un review
    @PostMapping("/new/save")
    public ResponseEntity<ReviewResponse> crearReview(@RequestBody ReviewEntity review) {
        try {
            if (review.getUsuarioCalificador().getTipo().equals(review.getUsuarioCalificado().getTipo())) {
                ReviewResponse error = new ReviewResponse("El calificador y calificado deben ser de tipos diferentes", null);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }

            // Insertar review usando repository
            reviewRepository.insertarReview(
                review.getUsuarioCalificador().getIdUsuario(),
                review.getUsuarioCalificado().getIdUsuario(),
                review.getViaje().getIdViaje(),
                review.getPuntuacion(),
                review.getComentario(),
                review.getFecha()
            );

            // Obtener la última review insertada
            ReviewEntity reviewGuardada = reviewRepository.darUltimaReview();
            ReviewResponse exito = new ReviewResponse("Review creada exitosamente", reviewGuardada);
            return new ResponseEntity<>(exito, HttpStatus.CREATED);

        } catch (Exception e) {
            ReviewResponse error = new ReviewResponse("Error al crear la review", null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
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
                    review.getFecha()
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

    // Requerimientos RF10 y RF11
    @PostMapping("/registrar")
    public ResponseEntity<ReviewResponse> registrarReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewEntity reviewGuardada = reviewService.registrarReview(reviewDTO);
            ReviewResponse exito = new ReviewResponse("Review creada exitosamente", reviewGuardada);
            return new ResponseEntity<>(exito, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ReviewResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ReviewResponse("Error al crear la review", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public class ReviewResponse {
        private String mensaje;
        private ReviewEntity review;

        public ReviewResponse(String mensaje, ReviewEntity review) {
            this.mensaje = mensaje;
            this.review = review;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public ReviewEntity getReview() { return review; }
        public void setReview(ReviewEntity review) { this.review = review; }
    }

}

