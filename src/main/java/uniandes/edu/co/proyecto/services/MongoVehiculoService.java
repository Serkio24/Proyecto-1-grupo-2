package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.Vehiculo;
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
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // Obtener todos los vehículos
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    // Obtener vehículo por ID
    public Optional<Vehiculo> obtenerVehiculoPorId(String id) {
        return vehiculoRepository.findById(id);
    }

    // Buscar vehículos por conductor
    public List<Vehiculo> buscarVehiculosPorConductor(String idConductor) {
        return vehiculoRepository.findByIdConductor(idConductor);
    }

    // Buscar vehículos disponibles
    public List<Vehiculo> buscarVehiculosDisponibles() {
        return vehiculoRepository.findVehiculosDisponibles();
    }

    // Buscar vehículo por placa
    public Optional<Vehiculo> buscarVehiculoPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    // Actualizar vehículo
    @Transactional
    public Vehiculo actualizarVehiculo(String id, Vehiculo vehiculoActualizado) {
        Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(id);
        if (vehiculoExistente.isPresent()) {
            Vehiculo vehiculo = vehiculoExistente.get();
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
    public Vehiculo actualizarDisponibilidad(String id, Vehiculo.Disponibilidad disponibilidad) {
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);
        if (vehiculoOpt.isPresent()) {
            Vehiculo vehiculo = vehiculoOpt.get();
            vehiculo.setDisponibilidad(disponibilidad);
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Vehículo no encontrado con ID: " + id);
    }

    // Agregar franja horaria
    @Transactional
    public Vehiculo agregarFranjaHoraria(String vehiculoId, Vehiculo.FranjaHoraria franja) {
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(vehiculoId);
        if (vehiculoOpt.isPresent()) {
            Vehiculo vehiculo = vehiculoOpt.get();
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
