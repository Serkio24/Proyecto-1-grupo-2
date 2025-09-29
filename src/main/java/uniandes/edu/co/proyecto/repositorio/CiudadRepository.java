package uniandes.edu.co.proyecto.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.CiudadEntity;

public interface CiudadRepository extends JpaRepository<CiudadEntity, Integer>{

    @Query(value= "SELECT * FROM ciudades", nativeQuery=true)
    Collection<CiudadEntity> darCiudades();
    
    @Query(value= "SELECT * FROM ciudades WHERE id= :id", nativeQuery =true)
    CiudadEntity darCiudadEntity(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO ciudades(id, nombre) VALUES (:id, :nombre)", nativeQuery=true)
    void insertarCiudad(@Param("nombre") String nombre, @Param("id") int id);

    @Modifying
    @Transactional
    @Query( value= "UPDATE ciudades SET nombre= :nombre WHERE id= :id", nativeQuery = true)
    void actualizarCiudad(@Param("id") int id, String nombre);

    @Modifying
    @Transactional
    @Query( value="DELETE FROM ciudades WHERE id= :id", nativeQuery=true)
    void eliminarCiudad(@Param("id") int id);
}
