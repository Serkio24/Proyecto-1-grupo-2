package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.ComisionEntity;
import uniandes.edu.co.proyecto.entities.CostoEntity;

public interface ComisionRepository extends JpaRepository<ComisionEntity, CostoEntity> {

    @Query(value = "SELECT * FROM comisiones", nativeQuery = true)
    Collection<ComisionEntity> darComisiones();
    
    @Query(value = "SELECT * FROM comisiones WHERE idComision = :id", nativeQuery = true)
    ComisionEntity darComision(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO comisiones(idViaje, porcentajeComision, valorComision) VALUES (:idViaje, :porcentajeComision, :valorComision)", nativeQuery = true)
    void insertarComision(@Param("idViaje") Long idViaje, @Param("porcentajeComision") Double porcentajeComision, @Param("valorComision") Double valorComision);

    @Modifying
    @Transactional
    @Query(value = "UPDATE comisiones SET idViaje = :idViaje, porcentajeComision = :porcentajeComision, valorComision = :valorComision WHERE idComision = :id", nativeQuery = true)
    void actualizarComision(@Param("id") Long id, @Param("idViaje") Long idViaje, @Param("porcentajeComision") Double porcentajeComision, @Param("valorComision") Double valorComision);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comisiones WHERE idComision = :id", nativeQuery = true)
    void eliminarComision(@Param("id") Long id);
}