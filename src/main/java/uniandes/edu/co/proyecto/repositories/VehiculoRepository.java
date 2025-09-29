package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {

    //Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO vehiculos (tipo, marca, modelo, color, placa, ciudadExpedicion, capacidadPasajeros) VALUES (:tipo, :marca, :modelo, :color, :placa, :ciudadExpedicion, :capacidadPasajeros)", nativeQuery = true)
    void insertarVehiculo(@Param("tipo") String tipo, @Param("marca") String marca, @Param("modelo") String modelo, @Param("color") String color, @Param("placa") String placa, @Param("ciudadExpedicion") String ciudadExpedicion, @Param("capacidadPasajeros") Integer capacidadPasajeros);

    //Read: Get all
    //indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM vehiculos", nativeQuery = true)
    Collection<VehiculoEntity> darVehiculos();

    //Read: Get one, le pasamos id por parametro
    @Query(value = "SELECT * FROM vehiculos WHERE idVehiculo = :idVehiculo", nativeQuery = true)
    VehiculoEntity darVehiculo(@Param("idVehiculo") Long idVehiculo);

    //Update
    @Modifying
    @Transactional
    @Query(value = "UPDATE vehiculos SET tipo = :tipo, marca = :marca, modelo = :modelo, color = :color, placa = :placa, ciudadExpedicion = :ciudadExpedicion, capacidadPasajeros = :capacidadPasajeros WHERE idVehiculo = :idVehiculo", nativeQuery = true)
    void actualizarVehiculo(@Param("idVehiculo") Long idVehiculo, @Param("tipo") String tipo, @Param("marca") String marca, @Param("modelo") String modelo, @Param("color") String color, @Param("placa") String placa, @Param("ciudadExpedicion") String ciudadExpedicion, @Param("capacidadPasajeros") Integer capacidadPasajeros);

    //Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vehiculos WHERE idVehiculo = :idVehiculo", nativeQuery = true)
    void eliminarVehiculo(@Param("idVehiculo") Long idVehiculo);
}
