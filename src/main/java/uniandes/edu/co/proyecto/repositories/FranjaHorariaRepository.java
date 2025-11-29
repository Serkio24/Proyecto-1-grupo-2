package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;

public interface FranjaHorariaRepository extends JpaRepository<FranjaHorariaEntity, Long> {

    // Create
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO franjas_horarias (idFranja, diaSemana, horaInicio, horaFin, tipoServicio)
        VALUES (franjas_horarias_SEQ.NEXTVAL, :diaSemana, :horaInicio, :horaFin, :tipoServicio)
    """, nativeQuery = true)
    void insertarFranjaHoraria(
        @Param("diaSemana") String diaSemana,
        @Param("horaInicio") String horaInicio,
        @Param("horaFin") String horaFin,
        @Param("tipoServicio") String tipoServicio
    );

    // Read: Get all
    @Query(value = "SELECT * FROM franjas_horarias", nativeQuery = true)
    Collection<FranjaHorariaEntity> darFranjasHorarias();

    // Read: Get one
    @Query(value = "SELECT * FROM franjas_horarias WHERE idFranja = :id", nativeQuery = true)
    FranjaHorariaEntity darFranjaHoraria(@Param("id") Long idFranja);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE franjas_horarias SET diaSemana = :diaSemana, horaInicio = :horaInicio, horaFin = :horaFin, tipoServicio = :tipoServicio WHERE idFranja = :id", nativeQuery = true)
    void actualizarFranjaHoraria(@Param("id") Long idFranja, @Param("diaSemana") String diaSemana, @Param("horaInicio") String horaInicio, @Param("horaFin") String horaFin, @Param("tipoServicio") String tipoServicio);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM franjas_horarias WHERE idFranja = :id", nativeQuery = true)
    void eliminarFranjaHoraria(@Param("id") Long idFranja);

    // Verifica si existe una franja con mismo día, horaInicio y horaFin
    @Query(value = """
        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
        FROM franjas_horarias
        WHERE diaSemana = :diaSemana
        AND horaInicio = :horaInicio
        AND horaFin = :horaFin
    """, nativeQuery = true)
    int existeFranja(
        @Param("diaSemana") String diaSemana,
        @Param("horaInicio") String horaInicio,
        @Param("horaFin") String horaFin
    );

    // Retorna la franja si existe (útil para obtener su ID)
    @Query(value = """
        SELECT * FROM franjas_horarias
        WHERE diaSemana = :diaSemana
        AND horaInicio = :horaInicio
        AND horaFin = :horaFin
    """, nativeQuery = true)
    FranjaHorariaEntity buscarFranjaExistente(
        @Param("diaSemana") String diaSemana,
        @Param("horaInicio") String horaInicio,
        @Param("horaFin") String horaFin
    );

}
