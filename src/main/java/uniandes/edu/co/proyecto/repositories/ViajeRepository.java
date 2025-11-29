package uniandes.edu.co.proyecto.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;

public interface ViajeRepository extends MongoRepository<ViajeEntity, Long> {

    // create
    @Query("{ $insert: { _id: ?0, idServicio: ?1, idCliente: ?2, idConductor: ?3, idVehiculo: ?4, idTarifa: ?5, fechaHora: ?6, tipoServicio: ?7, nivelRequerido: ?8, estado: ?9, orden: ?10, restaurante: ?11, puntoOrigen: ?12, destinos: ?13, fechaHoraInicio: ?14, fechaHoraFin: ?15, longitudTrayecto: ?16, costoTotal: ?17 } }")
    void insertarViaje(Long id, Long idServicio, Long idCliente, Long idConductor, Long idVehiculo, Long idTarifa, Date fechaHora, String tipoServicio, String nivelRequerido, String estado, Integer orden, String restaurante, PuntoGeograficoEntity puntoOrigen, List<PuntoGeograficoEntity> destinos, Date fechaHoraInicio, Date fechaHoraFin, Double longitudTrayecto, Double costoTotal);

    // read
    @Query(value = "{}")
    List<ViajeEntity> buscarTodosLosViajes();

    // read
    @Query("{ _id: ?0 }")
    ViajeEntity buscarPorId(Long id);

    // read
    @Query("{ idCliente: ?0 }")
    List<ViajeEntity> buscarPorIdCliente(Long idCliente);

    // update
    @Query("{ _id: ?0 }")
    @Update("{ $set: { idServicio: ?1, idCliente: ?2, idConductor: ?3, idVehiculo: ?4, idTarifa: ?5, fechaHora: ?6, tipoServicio: ?7, nivelRequerido: ?8, estado: ?9, orden: ?10, restaurante: ?11, puntoOrigen: ?12, destinos: ?13, fechaHoraInicio: ?14, fechaHoraFin: ?15, longitudTrayecto: ?16, costoTotal: ?17 } }")
    void actualizarViaje(Long id, Long idServicio, Long idCliente, Long idConductor, Long idVehiculo, Long idTarifa, Date fechaHora, String tipoServicio, String nivelRequerido, String estado, Integer orden, String restaurante, PuntoGeograficoEntity puntoOrigen, List<PuntoGeograficoEntity> destinos, Date fechaHoraInicio, Date fechaHoraFin, Double longitudTrayecto, Double costoTotal);

    // delete
    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarViaje(Long id);
}