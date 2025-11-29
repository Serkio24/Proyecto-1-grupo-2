package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.PuntoGeografico;
import uniandes.edu.co.proyecto.repositories.MongoPuntoGeograficoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MongoPuntoGeograficoService {

    @Autowired
    private MongoPuntoGeograficoRepository puntoRepository;

    // Crear punto geográfico
    @Transactional
    public PuntoGeografico crearPuntoGeografico(PuntoGeografico punto) {
        return puntoRepository.save(punto);
    }

    // Obtener todos los puntos geográficos
    public List<PuntoGeografico> obtenerTodosLosPuntos() {
        return puntoRepository.findAll();
    }

    // Obtener punto geográfico por ID
    public Optional<PuntoGeografico> obtenerPuntoPorId(String id) {
        return puntoRepository.findById(id);
    }

    // Buscar puntos por ciudad
    public List<PuntoGeografico> buscarPuntosPorCiudad(String ciudad) {
        return puntoRepository.findByCiudad(ciudad);
    }

    // Buscar puntos por nombre
    public List<PuntoGeografico> buscarPuntosPorNombre(String nombre) {
        return puntoRepository.findByNombreContaining(nombre);
    }

    // Actualizar punto geográfico
    @Transactional
    public PuntoGeografico actualizarPunto(String id, PuntoGeografico puntoActualizado) {
        Optional<PuntoGeografico> puntoExistente = puntoRepository.findById(id);
        if (puntoExistente.isPresent()) {
            PuntoGeografico punto = puntoExistente.get();
            punto.setNombre(puntoActualizado.getNombre());
            punto.setLatitud(puntoActualizado.getLatitud());
            punto.setLongitud(puntoActualizado.getLongitud());
            punto.setDireccionAproximada(puntoActualizado.getDireccionAproximada());
            punto.setCiudad(puntoActualizado.getCiudad());
            return puntoRepository.save(punto);
        }
        throw new RuntimeException("Punto geográfico no encontrado con ID: " + id);
    }

    // Eliminar punto geográfico
    @Transactional
    public void eliminarPunto(String id) {
        puntoRepository.deleteById(id);
    }
}
