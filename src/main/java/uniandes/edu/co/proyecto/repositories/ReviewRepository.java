package uniandes.edu.co.proyecto.repositories;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // Create
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO reviews (idUsuarioCalificador, idUsuarioCalificado, idViaje, puntuacion, comentario, fechaRevision) VALUES (:idUsuarioCalificador, :idUsuarioCalificado, :idViaje, :puntuacion, :comentario, :fechaRevision)", nativeQuery = true)
    void insertarReview(@Param("idUsuarioCalificador") Long idUsuarioCalificador, @Param("idUsuarioCalificado") Long idUsuarioCalificado, @Param("idViaje") Long idViaje, @Param("puntuacion") Double puntuacion, @Param("comentario") String comentario, @Param("fechaRevision") LocalDate fechaRevision);

    // Read: Get all
    @Query(value = "SELECT * FROM reviews", nativeQuery = true)
    Collection<ReviewEntity> darReviews();

    // Read: Get one
    @Query(value = "SELECT * FROM reviews WHERE idRevision = :id", nativeQuery = true)
    ReviewEntity darReview(@Param("id") Long idRevision);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE reviews SET idUsuarioCalificador = :idUsuarioCalificador, idUsuarioCalificado = :idUsuarioCalificado, idViaje = :idViaje, puntuacion = :puntuacion, comentario = :comentario, fechaRevision = :fechaRevision WHERE idRevision = :id", nativeQuery = true)
    void actualizarReview(@Param("id") Long idRevision, @Param("idUsuarioCalificador") Long idUsuarioCalificador, @Param("idUsuarioCalificado") Long idUsuarioCalificado, @Param("idViaje") Long idViaje, @Param("puntuacion") Double puntuacion, @Param("comentario") String comentario, @Param("fechaRevision") LocalDate fechaRevision);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reviews WHERE idRevision = :id", nativeQuery = true)
    void eliminarReview(@Param("id") Long idRevision);
}
