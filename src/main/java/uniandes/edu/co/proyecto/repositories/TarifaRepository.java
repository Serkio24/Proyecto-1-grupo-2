package uniandes.edu.co.proyecto.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.proyecto.entities.TarifaEntity;

public interface TarifaRepository extends MongoRepository<TarifaEntity, Long> {

    // create
    @Query("{ $insert: { _id: ?0, tipoServicio: ?1, nivel: ?2, precioPorKm: ?3, vigenciaDesde: ?4, vigenciaHasta: ?5 } }")
    void insertarTarifa(Long id, String tipoServicio, String nivel, Double precioPorKm, Date vigenciaDesde, Date vigenciaHasta);

    // read
    @Query(value = "{}")
    List<TarifaEntity> buscarTodasLasTarifas();

    // read
    @Query("{ _id: ?0 }")
    TarifaEntity buscarPorId(Long id);

    // read
    // tarifa vigente para un tipo, nivel y fecha dada
    @Query("{ 'tipoServicio': ?0, 'nivel': ?1, 'vigenciaDesde': { $lte: ?2 }, $or: [ { 'vigenciaHasta': { $gte: ?2 } }, { 'vigenciaHasta': null } ] }")
    TarifaEntity encontrarTarifa(String tipoServicio, String nivel, Date fechaSolicitud);

    // update
    @Query("{ _id: ?0 }")
    @Update("{ $set: { tipoServicio: ?1, nivel: ?2, precioPorKm: ?3, vigenciaDesde: ?4, vigenciaHasta: ?5 } }")
    void actualizarTarifa(Long id, String tipoServicio, String nivel, Double precioPorKm, Date vigenciaDesde, Date vigenciaHasta);

    // delete
    @Query(value = "{ _id: ?0 }", delete = true)
    void eliminarTarifa(Long id);
}
