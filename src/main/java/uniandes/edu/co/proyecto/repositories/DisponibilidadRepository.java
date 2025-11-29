package uniandes.edu.co.proyecto.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;

public interface DisponibilidadRepository extends MongoRepository<DisponibilidadEntity, Long> {

    // create
    @Query("{ $insert: { _id: ?0, idConductor: ?1, idVehiculo: ?2, franjasHorarias: ?3 } }")
    void insertarDisponibilidad(Long id, Long idConductor, Long idVehiculo, List<FranjaHorariaEntity> franjasHorarias);

    // read
    @Query(value = "{}")
    List<DisponibilidadEntity> buscarTodasLasDisponibilidades();

    // read
    @Query("{ _id: ?0 }")
    DisponibilidadEntity buscarPorId(Long id);

    // read
    @Query("{ idVehiculo: ?0 }")
    List<DisponibilidadEntity> buscarPorIdVehiculo(Long idVehiculo);

    // read
    @Query("{ idConductor: ?0 }")
    List<DisponibilidadEntity> buscarPorIdConductor(Long idConductor);

    // update
    @Query("{ _id: ?0 }")
    @Update("{ $set: { idConductor: ?1, idVehiculo: ?2, franjasHorarias: ?3 } }")
    void actualizarDisponibilidad(Long id, Long idConductor, Long idVehiculo, List<FranjaHorariaEntity> franjasHorarias);

    // delete
    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarDisponibilidadPorId(Long id);
}
