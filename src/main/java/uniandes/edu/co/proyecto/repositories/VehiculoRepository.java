package uniandes.edu.co.proyecto.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;

public interface VehiculoRepository extends MongoRepository<VehiculoEntity, Long> {

    // create
    @Query("{ $insert: { _id: ?0, idConductor: ?1, tipo: ?2, marca: ?3, modelo: ?4, color: ?5, placa: ?6, ciudadExpedicion: ?7, capacidadPasajeros: ?8, nivel: ?9 } }")
    void insertarVehiculo(Long id, Long idConductor, String tipo, String marca, String modelo, String color, String placa, String ciudadExpedicion, Integer capacidadPasajeros, String nivel);

    // read
    @Query(value = "{}")
    List<VehiculoEntity> buscarTodosLosVehiculos();

    // read
    @Query("{ _id: ?0 }")
    VehiculoEntity buscarPorId(Long id);

    // read
    @Query("{ idConductor: ?0 }")
    List<VehiculoEntity> buscarPorIdConductor(Long idConductor);

    // update
    @Query("{ _id: ?0 }")
    @Update("{ $set: { idConductor: ?1, tipo: ?2, marca: ?3, modelo: ?4, color: ?5, placa: ?6, ciudadExpedicion: ?7, capacidadPasajeros: ?8, nivel: ?9 } }")
    void actualizarVehiculo(Long id, Long idConductor, String tipo, String marca, String modelo, String color, String placa, String ciudadExpedicion, Integer capacidadPasajeros, String nivel);

    // delete
    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarVehiculo(Long id);
}
