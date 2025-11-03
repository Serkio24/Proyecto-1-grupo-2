package uniandes.edu.co.proyecto.repositories;

import java.time.LocalDate;
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

    // Create: usar secuencia para generar ID
    @Modifying @Transactional
    @Query(value = "INSERT INTO tarifas(id_tarifa, tipo_servicio, nivel, precio_por_km, vigencia_desde, vigencia_hasta) VALUES (tarifas_SEQ.nextval, :tipoServicio, :nivel, :precioPorKm, :vigenciaDesde, :vigenciaHasta)", nativeQuery = true)
    void insertarTarifa(@Param("tipoServicio") String tipoServicio, @Param("nivel") String nivel, @Param("precioPorKm") Double precioPorKm, @Param("vigenciaDesde") LocalDate vigenciaDesde, @Param("vigenciaHasta") LocalDate vigenciaHasta);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tarifas SET tipoServicio = :tipoServicio, nivel = :nivel, precioPorKm = :precioPorKm, vigenciaDesde = :vigenciaDesde, vigenciaHasta = :vigenciaHasta WHERE idTarifa = :id", nativeQuery = true)
    void actualizarTarifa(@Param("id") Long id, @Param("tipoServicio") String tipoServicio, @Param("nivel") String nivel, @Param("precioPorKm") Double precioPorKm, @Param("vigenciaDesde") String vigenciaDesde, @Param("vigenciaHasta") String vigenciaHasta);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tarifas WHERE idTarifa = :id", nativeQuery = true)
    void eliminarTarifa(@Param("id") Long id);

    // Encontrar tarifa por tipo de servicio, nivel y fecha de solicitud
    @Query(value = """
        SELECT * 
        FROM tarifas 
        WHERE tipo_servicio = :tipoServicio
            AND nivel = :nivel
            AND :fechaSolicitud BETWEEN vigencia_desde AND vigencia_hasta
        """, nativeQuery = true)
    TarifaEntity encontrarTarifa(@Param("tipoServicio") String tipoServicio, @Param("nivel") String nivel, @Param("fechaSolicitud") LocalDate fechaSolicitud
    );

    @Query(value = "SELECT * FROM tarifas WHERE id_tarifa = (SELECT MAX(id_tarifa) FROM tarifas)", nativeQuery = true)
    TarifaEntity darUltimaTarifa();

}
