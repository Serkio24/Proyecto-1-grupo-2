package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.CiudadEntity;

public interface CiudadRepository extends JpaRepository<CiudadEntity, Long> {

    // Create: usar secuencia para generar ID
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ciudades (idCiudad, nombre) VALUES (ciudades_SEQ.nextval, :nombre)", nativeQuery = true)
    void insertarCiudad(@Param("nombre") String nombre);

    // Read: Get all
    @Query(value = "SELECT * FROM ciudades", nativeQuery = true)
    Collection<CiudadEntity> darCiudades();

    // Read: Get one
    @Query(value = "SELECT * FROM ciudades WHERE idCiudad = :idCiudad", nativeQuery = true)
    CiudadEntity darCiudad(@Param("idCiudad") Long idCiudad);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE ciudades SET nombre = :nombre WHERE idCiudad = :idCiudad", nativeQuery = true)
    void actualizarCiudad(@Param("idCiudad") Long idCiudad, @Param("nombre") String nombre);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ciudades WHERE idCiudad = :idCiudad", nativeQuery = true)
    void eliminarCiudad(@Param("idCiudad") Long idCiudad);

    // Obtener el siguiente valor de la secuencia
    @Query(value = "SELECT ciudades_SEQ.nextval FROM dual", nativeQuery = true)
    Long obtenerNextval();

}
