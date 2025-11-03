package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ViajeEntity;

public interface ViajeRepository extends JpaRepository<ViajeEntity, Long> {

    @Query(value = "SELECT * FROM viajes", nativeQuery = true)
    Collection<ViajeEntity> darViajes();
    
    @Query(value = "SELECT * FROM viajes WHERE idViaje = :id", nativeQuery = true)
    ViajeEntity darViaje(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO viajes(idViaje, fechaHoraInicio, fechaHoraFin, longitudTrayecto, idServicio, idConductor, idVehiculo) VALUES (viajes_SEQ.NEXTVAL, :fechaHoraInicio, :fechaHoraFin, :longitudTrayecto, :idServicio, :idConductor, :idVehiculo)", nativeQuery = true)
    void insertarViaje(@Param("fechaHoraInicio") LocalDateTime fechaHoraInicio, @Param("fechaHoraFin") LocalDateTime fechaHoraFin, @Param("longitudTrayecto") Double longitudTrayecto, @Param("idServicio") Long idServicio, @Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE viajes SET fechaHoraInicio = :fechaHoraInicio, fechaHoraFin = :fechaHoraFin, longitudTrayecto = :longitudTrayecto, idServicio = :idServicio, idConductor = :idConductor, idVehiculo = :idVehiculo WHERE idViaje = :id", nativeQuery = true)
    void actualizarViaje(@Param("id") Long id, @Param("fechaHoraInicio") LocalDateTime fechaHoraInicio, @Param("fechaHoraFin") LocalDateTime fechaHoraFin, @Param("longitudTrayecto") Double longitudTrayecto, @Param("idServicio") Long idServicio, @Param("idConductor") Long idConductor, @Param("idVehiculo") Long idVehiculo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM viajes WHERE idViaje = :id", nativeQuery = true)
    void eliminarViaje(@Param("id") Long id);

    // Obtener el último viaje insertado
    @Query(value = "SELECT * FROM viajes WHERE idViaje = (SELECT MAX(idViaje) FROM viajes)", nativeQuery = true)
    ViajeEntity darUltimoViaje();

}
