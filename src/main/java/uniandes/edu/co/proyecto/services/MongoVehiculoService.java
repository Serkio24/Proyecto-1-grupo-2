package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.repositories.MongoVehiculoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoVehiculoService {

    @Autowired
    private MongoVehiculoRepository vehiculoRepository;

    // Crear vehículo
    @Transactional
    public VehiculoEntity crearVehiculo(VehiculoEntity vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // Obtener todos los vehículos
    public List<VehiculoEntity> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    // Obtener vehículo por ID
    public Optional<VehiculoEntity> obtenerVehiculoPorId(String id) {
        return vehiculoRepository.findById(id);
    }

    // Buscar vehículos por conductor
    public List<VehiculoEntity> buscarVehiculosPorConductor(String idConductor) {
        return vehiculoRepository.findByIdConductor(idConductor);
    }

    // Buscar vehículos disponibles
    public List<VehiculoEntity> buscarVehiculosDisponibles() {
        return vehiculoRepository.findVehiculosDisponibles();
    }

    // Buscar vehículo por placa
    public Optional<VehiculoEntity> buscarVehiculoPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    // Actualizar vehículo
    @Transactional
    public VehiculoEntity actualizarVehiculo(String id, VehiculoEntity vehiculoActualizado) {
        Optional<VehiculoEntity> vehiculoExistente = vehiculoRepository.findById(id);
        if (vehiculoExistente.isPresent()) {
            VehiculoEntity vehiculo = vehiculoExistente.get();
            vehiculo.setIdConductor(vehiculoActualizado.getIdConductor());
            vehiculo.setNivelVehiculo(vehiculoActualizado.getNivelVehiculo());
            vehiculo.setTipo(vehiculoActualizado.getTipo());
            vehiculo.setMarca(vehiculoActualizado.getMarca());
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setColor(vehiculoActualizado.getColor());
            vehiculo.setPlaca(vehiculoActualizado.getPlaca());
            vehiculo.setCiudadExpedicion(vehiculoActualizado.getCiudadExpedicion());
            vehiculo.setCapacidadPasajeros(vehiculoActualizado.getCapacidadPasajeros());
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Vehículo no encontrado con ID: " + id);
    }

    // Actualizar disponibilidad
    @Transactional
    public VehiculoEntity actualizarDisponibilidad(String id, VehiculoEntity.Disponibilidad disponibilidad) {
        Optional<VehiculoEntity> vehiculoOpt = vehiculoRepository.findById(id);
        if (vehiculoOpt.isPresent()) {
            VehiculoEntity vehiculo = vehiculoOpt.get();
            vehiculo.setDisponibilidad(disponibilidad);
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Vehículo no encontrado con ID: " + id);
    }

    // Agregar franja horaria
    @Transactional
    public VehiculoEntity agregarFranjaHoraria(String vehiculoId, VehiculoEntity.FranjaHoraria franja) {
        Optional<VehiculoEntity> vehiculoOpt = vehiculoRepository.findById(vehiculoId);
        if (vehiculoOpt.isPresent()) {
            VehiculoEntity vehiculo = vehiculoOpt.get();
            if (vehiculo.getFranjasHorarias() == null) {
                vehiculo.setFranjasHorarias(new ArrayList<>());
            }
            vehiculo.getFranjasHorarias().add(franja);
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Vehículo no encontrado con ID: " + vehiculoId);
    }

    // Eliminar vehículo
    @Transactional
    public void eliminarVehiculo(String id) {
        vehiculoRepository.deleteById(id);
    }
}
