package uniandes.edu.co.proyecto;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;
import uniandes.edu.co.proyecto.entities.ConductorVehiculoEntity;

import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;
import uniandes.edu.co.proyecto.repositories.ConductorVehiculoRepository;

@SpringBootApplication
public class ProyectoApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ConductorVehiculoRepository conductorVehiculoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        // Mostrar todos los usuarios
        Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuarios();
        for (UsuarioEntity usuario : usuarios) {
            System.out.println(usuario);
        }

        // Mostrar todos los vehículos
        Collection<VehiculoEntity> vehiculos = vehiculoRepository.darVehiculos();
        for (VehiculoEntity vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }

        // Mostrar todas las relaciones conductor-vehículo
        Collection<ConductorVehiculoEntity> conductorVehiculos = conductorVehiculoRepository.darConductorVehiculos();
        for (ConductorVehiculoEntity conductorVehiculo : conductorVehiculos) {
            System.out.println(conductorVehiculo);
        }
    }
}
