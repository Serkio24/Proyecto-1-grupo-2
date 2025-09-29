package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCreditoEntity, Long> {

    // Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tarjetas_credito (titularDeLaTarjeta, numeroTarjeta, fechaExpiracion, codigoSeguridad) VALUES (:titular, :numero, :fecha, :codigo)", nativeQuery = true)
    void insertarTarjeta(@Param("titular") String titularDeLaTarjeta, @Param("numero") String numeroTarjeta, @Param("fecha") String fechaExpiracion, @Param("codigo") String codigoSeguridad);

    // Read: Get all, indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM tarjetas_credito", nativeQuery = true)
    Collection<TarjetaCreditoEntity> darTarjetas();

    // Read: Get one, le pasamos id por parametro
    @Query(value = "SELECT * FROM tarjetas_credito WHERE idTarjetaCredito=:id", nativeQuery = true)
    TarjetaCreditoEntity darTarjeta(@Param("id") Long idTarjetaCredito);

    // Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE tarjetas_credito SET titularDeLaTarjeta=:titular, numeroTarjeta=:numero, fechaExpiracion=:fecha, codigoSeguridad=:codigo WHERE idTarjetaCredito=:id", nativeQuery = true)
    void actualizarTarjeta(@Param("id") Long idTarjetaCredito, @Param("titular") String titularDeLaTarjeta, @Param("numero") String numeroTarjeta, @Param("fecha") String fechaExpiracion, @Param("codigo") String codigoSeguridad);

    // Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tarjetas_credito WHERE idTarjetaCredito=:id", nativeQuery = true)
    void eliminarTarjeta(@Param("id") Long idTarjetaCredito);
}
