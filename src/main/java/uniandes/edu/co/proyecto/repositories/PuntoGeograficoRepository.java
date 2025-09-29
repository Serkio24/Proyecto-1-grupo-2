package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;

public interface PuntoGeograficoRepository extends JpaRepository<PuntoGeograficoEntity, Long> {

    // Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO puntos_geograficos (nombre, latitud, longitud, direccionAproximada, idCiudad) VALUES (:nombre, :latitud, :longitud, :direccionAproximada, :idCiudad)", nativeQuery = true)
    void insertarPuntoGeografico(@Param("nombre") String nombre, @Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("direccionAproximada") String direccionAproximada, @Param("idCiudad") Long idCiudad);

    // Read: Get all
    // indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM puntos_geograficos", nativeQuery = true)
    Collection<PuntoGeograficoEntity> darPuntosGeograficos();

    // Read: Get one, le pasamos id por parametro
    @Query(value = "SELECT * FROM puntos_geograficos WHERE idPunto = :id", nativeQuery = true)
    PuntoGeograficoEntity darPuntoGeografico(@Param("id") Long idPunto);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE puntos_geograficos SET nombre = :nombre, latitud = :latitud, longitud = :longitud, direccionAproximada = :direccionAproximada, idCiudad = :idCiudad WHERE idPunto = :id", nativeQuery = true)
    void actualizarPuntoGeografico(@Param("id") Long idPunto, @Param("nombre") String nombre, @Param("latitud") Double latitud, @Param("longitud") Double longitud, @Param("direccionAproximada") String direccionAproximada, @Param("idCiudad") Long idCiudad);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM puntos_geograficos WHERE idPunto = :id", nativeQuery = true)
    void eliminarPuntoGeografico(@Param("id") Long idPunto);
}
