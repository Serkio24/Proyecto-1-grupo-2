package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.repositories.MongoTarifaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MongoTarifaService {

    @Autowired
    private MongoTarifaRepository tarifaRepository;

    // Crear tarifa
    @Transactional
    public TarifaEntity crearTarifa(TarifaEntity tarifa) {
        return tarifaRepository.save(tarifa);
    }

    // Obtener todas las tarifas
    public List<TarifaEntity> obtenerTodasLasTarifas() {
        return tarifaRepository.findAll();
    }

    // Obtener tarifa por ID
    public Optional<TarifaEntity> obtenerTarifaPorId(String id) {
        return tarifaRepository.findById(id);
    }

    // Buscar tarifas por tipo de servicio
    public List<TarifaEntity> buscarTarifasPorTipoServicio(String tipoServicio) {
        return tarifaRepository.findByTipoServicio(tipoServicio);
    }

    // Buscar tarifas por nivel
    public List<TarifaEntity> buscarTarifasPorNivel(String nivel) {
        return tarifaRepository.findByNivel(nivel);
    }

    // Buscar tarifa específica por tipo y nivel
    public Optional<TarifaEntity> buscarTarifaPorTipoYNivel(String tipoServicio, String nivel) {
        return tarifaRepository.findByTipoServicioAndNivel(tipoServicio, nivel);
    }

    // Actualizar tarifa
    @Transactional
    public TarifaEntity actualizarTarifa(String id, TarifaEntity tarifaActualizada) {
        Optional<TarifaEntity> tarifaExistente = tarifaRepository.findById(id);
        if (tarifaExistente.isPresent()) {
            TarifaEntity tarifa = tarifaExistente.get();
            tarifa.setTipoServicio(tarifaActualizada.getTipoServicio());
            tarifa.setNivel(tarifaActualizada.getNivel());
            tarifa.setPrecioPorKM(tarifaActualizada.getPrecioPorKM());
            return tarifaRepository.save(tarifa);
        }
        throw new RuntimeException("TarifaEntity no encontrada con ID: " + id);
    }

    // Eliminar tarifa
    @Transactional
    public void eliminarTarifa(String id) {
        tarifaRepository.deleteById(id);
    }
}
