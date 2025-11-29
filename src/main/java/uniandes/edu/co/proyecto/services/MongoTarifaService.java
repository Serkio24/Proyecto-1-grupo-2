package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.Tarifa;
import uniandes.edu.co.proyecto.repositories.MongoTarifaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MongoTarifaService {

    @Autowired
    private MongoTarifaRepository tarifaRepository;

    // Crear tarifa
    @Transactional
    public Tarifa crearTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    // Obtener todas las tarifas
    public List<Tarifa> obtenerTodasLasTarifas() {
        return tarifaRepository.findAll();
    }

    // Obtener tarifa por ID
    public Optional<Tarifa> obtenerTarifaPorId(String id) {
        return tarifaRepository.findById(id);
    }

    // Buscar tarifas por tipo de servicio
    public List<Tarifa> buscarTarifasPorTipoServicio(String tipoServicio) {
        return tarifaRepository.findByTipoServicio(tipoServicio);
    }

    // Buscar tarifas por nivel
    public List<Tarifa> buscarTarifasPorNivel(String nivel) {
        return tarifaRepository.findByNivel(nivel);
    }

    // Buscar tarifa específica por tipo y nivel
    public Optional<Tarifa> buscarTarifaPorTipoYNivel(String tipoServicio, String nivel) {
        return tarifaRepository.findByTipoServicioAndNivel(tipoServicio, nivel);
    }

    // Actualizar tarifa
    @Transactional
    public Tarifa actualizarTarifa(String id, Tarifa tarifaActualizada) {
        Optional<Tarifa> tarifaExistente = tarifaRepository.findById(id);
        if (tarifaExistente.isPresent()) {
            Tarifa tarifa = tarifaExistente.get();
            tarifa.setTipoServicio(tarifaActualizada.getTipoServicio());
            tarifa.setNivel(tarifaActualizada.getNivel());
            tarifa.setPrecioPorKM(tarifaActualizada.getPrecioPorKM());
            return tarifaRepository.save(tarifa);
        }
        throw new RuntimeException("Tarifa no encontrada con ID: " + id);
    }

    // Eliminar tarifa
    @Transactional
    public void eliminarTarifa(String id) {
        tarifaRepository.deleteById(id);
    }
}
