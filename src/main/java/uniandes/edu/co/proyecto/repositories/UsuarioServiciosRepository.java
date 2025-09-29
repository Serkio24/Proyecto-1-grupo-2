package uniandes.edu.co.proyecto.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.UsuarioServiciosEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServiciosPK;

public interface UsuarioServiciosRepository extends JpaRepository<UsuarioServiciosEntity, UsuarioServiciosPK> {

    //Create, remarcar cada parametro con anotacion param
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios_servicio (idUsuario, idTarjetaCredito) VALUES (:idUsuario, :idTarjetaCredito)", nativeQuery = true)
    void insertarUsuarioServicio(@Param("idUsuario") Long idUsuario, @Param("idTarjetaCredito") Long idTarjetaCredito);

    //Read: Get all
    //indicar que consulta es y que es nativa
    @Query(value = "SELECT * FROM usuarios_servicio", nativeQuery = true)
    Collection<UsuarioServiciosEntity> darUsuariosServicios();

    //Read: Get one, le pasamos idUsuario e idTarjetaCredito por parametro
    @Query(value = "SELECT * FROM usuarios_servicio WHERE idUsuario = :idUsuario AND idTarjetaCredito = :idTarjetaCredito", nativeQuery = true)
    UsuarioServiciosEntity darUsuarioServicio(@Param("idUsuario") Long idUsuario, @Param("idTarjetaCredito") Long idTarjetaCredito);

    //Update
    @Modifying
    @Transactional
    @Query(value= "UPDATE usuarios_servicio SET idUsuario = :idUsuario, idTarjetaCredito = :idTarjetaCredito WHERE idUsuario = :idUsuarioAnt AND idTarjetaCredito = :idTarjetaCreditoAnt", nativeQuery = true)
    void actualizarUsuarioServicio(@Param("idUsuarioAnt") Long idUsuarioAnt, @Param("idTarjetaCreditoAnt") Long idTarjetaCreditoAnt, @Param("idUsuario") Long idUsuario, @Param("idTarjetaCredito") Long idTarjetaCredito);

    //Delete
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usuarios_servicio WHERE idUsuario = :idUsuario AND idTarjetaCredito = :idTarjetaCredito", nativeQuery = true)
    void eliminarUsuarioServicio(@Param("idUsuario") Long idUsuario, @Param("idTarjetaCredito") Long idTarjetaCredito);
}
