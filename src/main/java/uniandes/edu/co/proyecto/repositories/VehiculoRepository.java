package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {

    @Query(value = "SELECT * FROM vehiculos", nativeQuery = true)
    Collection<VehiculoEntity> darVehiculos();

    @Query(value = "SELECT * FROM vehiculos WHERE idVehiculo = :id", nativeQuery = true)
    VehiculoEntity darVehiculo(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vehiculos WHERE idVehiculo = :id", nativeQuery = true)
    void eliminarVehiculo(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vehiculos SET tipo = :tipo, marca = :marca, modelo = :modelo, color = :color, placa = :placa, ciudadExpedicion = :ciudad, capacidadPasajeros = :capacidad WHERE idVehiculo = :id", nativeQuery = true)
    void actualizarVehiculo(@Param("id") Long id, @Param("tipo") String tipo, @Param("marca") String marca, @Param("modelo") String modelo, @Param("color") String color, @Param("placa") String placa, @Param("ciudad") String ciudad, @Param("capacidad") Long capacidad);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO vehiculos (tipo, marca, modelo, color, placa, ciudadExpedicion, capacidadPasajeros) VALUES (:tipo, :marca, :modelo, :color, :placa, :ciudad, :capacidad)", nativeQuery = true)
    void insertarVehiculo(@Param("tipo") String tipo, @Param("marca") String marca, @Param("modelo") String modelo, @Param("color") String color, @Param("placa") String placa, @Param("ciudad") String ciudad, @Param("capacidad") Long capacidad);
}
