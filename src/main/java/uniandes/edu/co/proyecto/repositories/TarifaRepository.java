package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.TarifaEntity;

public interface TarifaRepository extends JpaRepository<TarifaEntity, Long> {

    @Query(value = "SELECT * FROM tarifas", nativeQuery = true)
    Collection<TarifaEntity> darTarifas();
    
    @Query(value = "SELECT * FROM tarifas WHERE idTarifa = :id", nativeQuery = true)
    TarifaEntity darTarifa(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tarifas(tipoServicio, nivel, precioPorKm, vigenciaDesde, vigenciaHasta) VALUES (:tipoServicio, :nivel, :precioPorKm, :vigenciaDesde, :vigenciaHasta)", nativeQuery = true)
    void insertarTarifa(@Param("tipoServicio") String tipoServicio, @Param("nivel") String nivel, @Param("precioPorKm") Double precioPorKm, @Param("vigenciaDesde") String vigenciaDesde, @Param("vigenciaHasta") String vigenciaHasta);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tarifas SET tipoServicio = :tipoServicio, nivel = :nivel, precioPorKm = :precioPorKm, vigenciaDesde = :vigenciaDesde, vigenciaHasta = :vigenciaHasta WHERE idTarifa = :id", nativeQuery = true)
    void actualizarTarifa(@Param("id") Long id, @Param("tipoServicio") String tipoServicio, @Param("nivel") String nivel, @Param("precioPorKm") Double precioPorKm, @Param("vigenciaDesde") String vigenciaDesde, @Param("vigenciaHasta") String vigenciaHasta);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tarifas WHERE idTarifa = :id", nativeQuery = true)
    void eliminarTarifa(@Param("id") Long id);
}