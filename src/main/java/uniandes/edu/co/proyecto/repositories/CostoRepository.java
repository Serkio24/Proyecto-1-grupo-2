package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.CostoEntity;
import uniandes.edu.co.proyecto.entities.CostoPK;

public interface CostoRepository extends JpaRepository<CostoEntity, CostoPK> {

    @Query(value = "SELECT * FROM costos", nativeQuery = true)
    Collection<CostoEntity> darCostos();
    
    @Query(value = "SELECT * FROM costos WHERE idCosto = :id", nativeQuery = true)
    CostoEntity darCosto(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO costos(idViaje, costoTotal) VALUES (:idViaje, :costoTotal)", nativeQuery = true)
    void insertarCosto(@Param("idViaje") Long idViaje, @Param("costoTotal") Double costoTotal);

    @Modifying
    @Transactional
    @Query(value = "UPDATE costos SET idViaje = :idViaje, costoTotal = :costoTotal WHERE idCosto = :id", nativeQuery = true)
    void actualizarCosto(@Param("id") Long id, @Param("idViaje") Long idViaje, @Param("costoTotal") Double costoTotal);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM costos WHERE idCosto = :id", nativeQuery = true)
    void eliminarCosto(@Param("id") Long id);
}