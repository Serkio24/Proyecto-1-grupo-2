package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.ServicioEntity;

public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

    @Query(value = "SELECT * FROM servicios", nativeQuery = true)
    Collection<ServicioEntity> darServicios();
    
    @Query(value = "SELECT * FROM servicios WHERE idServicio = :id", nativeQuery = true)
    ServicioEntity darServicio(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO servicios(idCliente, fechaHora, tipoServicio, nivelRequerido, estado, orden, restaurante, idPuntoPartida) VALUES (:idCliente, :fechaHora, :tipoServicio, :nivelRequerido, :estado, :orden, :restaurante, :idPuntoPartida)", nativeQuery = true)
    void insertarServicio(@Param("idCliente") Long idCliente, @Param("fechaHora") LocalDateTime fechaHora, @Param("tipoServicio") String tipoServicio, @Param("nivelRequerido") String nivelRequerido, @Param("estado") String estado, @Param("orden") String orden, @Param("restaurante") String restaurante, @Param("idPuntoPartida") Long idPuntoPartida);

    @Modifying
    @Transactional
    @Query(value = "UPDATE servicios SET idCliente = :idCliente, fechaHora = :fechaHora, tipoServicio = :tipoServicio, nivelRequerido = :nivelRequerido, estado = :estado, orden = :orden, restaurante = :restaurante, idPuntoPartida = :idPuntoPartida WHERE idServicio = :id", nativeQuery = true)
    void actualizarServicio(@Param("id") Long id, @Param("idCliente") Long idCliente, @Param("fechaHora") LocalDateTime fechaHora, @Param("tipoServicio") String tipoServicio, @Param("nivelRequerido") String nivelRequerido, @Param("estado") String estado, @Param("orden") String orden, @Param("restaurante") String restaurante, @Param("idPuntoPartida") Long idPuntoPartida);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM servicios WHERE idServicio = :id", nativeQuery = true)
    void eliminarServicio(@Param("id") Long id);
}