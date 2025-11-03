package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.ServicioDestinoEntity;

public interface ServicioDestinoRepository extends JpaRepository<ServicioDestinoEntity, Long> {

    @Query(value = "SELECT * FROM servicios_destinos", nativeQuery = true)
    Collection<ServicioDestinoEntity> darServicioDestinos();

    @Query(value = "SELECT * FROM servicios_destinos WHERE idServicio = :id", nativeQuery = true)
    ServicioDestinoEntity darServicioDestino(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO servicios_destinos(idServicio, idPuntoLlegada) VALUES (:idServicio, :idPuntoLlegada)", nativeQuery = true)
    void insertarServicioDestino(@Param("idServicio") Long idServicio, @Param("idPuntoLlegada") Long idPuntoLlegada);

    // Obtener todos los destinos de un servicio específico
    @Query(value = "SELECT * FROM servicios_destinos WHERE idServicio = :idServicio", nativeQuery = true)
    List<ServicioDestinoEntity> darDestinosServicio(@Param("idServicio") Long idServicio);

    @Modifying
    @Transactional
    @Query(value = "UPDATE servicios_destinos SET idPuntoLlegada = :idPuntoLlegada WHERE idServicio = :id", nativeQuery = true)
    void actualizarServicioDestino(@Param("id") Long id, @Param("idPuntoLlegada") Long idPuntoLlegada);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM servicios_destinos WHERE idServicio = :id", nativeQuery = true)
    void eliminarServicioDestino(@Param("id") Long id);
}
