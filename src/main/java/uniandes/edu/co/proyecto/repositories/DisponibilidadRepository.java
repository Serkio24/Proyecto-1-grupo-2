package uniandes.edu.co.proyecto.repositories;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadPK;

public interface DisponibilidadRepository extends JpaRepository<DisponibilidadEntity, DisponibilidadPK> {

    // Create
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO disponibilidades (idVehiculo, idFranja) VALUES (:idVehiculo, :idFranja)", nativeQuery = true)
    void insertarDisponibilidad(@Param("idVehiculo") Long idVehiculo, @Param("idFranja") Long idFranja);

    // Read: Get all
    @Query(value = "SELECT * FROM disponibilidades", nativeQuery = true)
    Collection<DisponibilidadEntity> darDisponibilidades();

    // Read: Get one
    @Query(value = "SELECT * FROM disponibilidades WHERE idVehiculo = :idVehiculo AND idFranja = :idFranja", nativeQuery = true)
    DisponibilidadEntity darDisponibilidad(@Param("idVehiculo") Long idVehiculo, @Param("idFranja") Long idFranja);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE disponibilidades SET idVehiculo = :idVehiculo, idFranja = :idFranja WHERE idVehiculo = :idVehiculoAnt AND idFranja = :idFranjaAnt", nativeQuery = true)
    void actualizarDisponibilidad(@Param("idVehiculoAnt") Long idVehiculoAnt, @Param("idFranjaAnt") Long idFranjaAnt, @Param("idVehiculo") Long idVehiculo, @Param("idFranja") Long idFranja);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM disponibilidades WHERE idVehiculo = :idVehiculo AND idFranja = :idFranja", nativeQuery = true)
    void eliminarDisponibilidad(@Param("idVehiculo") Long idVehiculo, @Param("idFranja") Long idFranja);

    @Query("""
       SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
       FROM DisponibilidadEntity d
       WHERE d.pk.vehiculo IN (
            SELECT cv.pk.idVehiculo
            FROM ConductorVehiculoEntity cv
            WHERE cv.pk.idConductor.id = :idConductor
       )
       AND d.pk.franja.diaSemana = :dia
       AND (
            (:horaInicio BETWEEN d.pk.franja.horaInicio AND d.pk.franja.horaFin)
         OR (:horaFin BETWEEN d.pk.franja.horaInicio AND d.pk.franja.horaFin)
         OR (d.pk.franja.horaInicio BETWEEN :horaInicio AND :horaFin)
       )
       """)
     boolean existsTraslape(@Param("idConductor") Long idConductor,
                         @Param("dia") String dia,
                         @Param("horaInicio") LocalTime horaInicio,
                         @Param("horaFin") LocalTime horaFin);

}
