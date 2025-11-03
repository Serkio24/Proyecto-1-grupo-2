package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.repositories.CiudadRepository;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional
    public void insertarCiudad(String nombre) {
        ciudadRepository.insertarCiudad(nombre);
    }

    /**
     * RF1: Registrar una nueva ciudad para AlpesCab de forma transaccional.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public CiudadEntity registrarCiudad(String nombre) {
        // Insertar la ciudad usando la secuencia
        ciudadRepository.insertarCiudad(nombre);
        
        // Recuperar la ciudad recién creada
        CiudadEntity ciudadCreada = ciudadRepository.darUltimaCiudad();
        
        return ciudadCreada;
    }

}
