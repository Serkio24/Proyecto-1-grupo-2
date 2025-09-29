package uniandes.edu.co.proyecto;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.entities.ServicioEntity;
import uniandes.edu.co.proyecto.entities.TarifaEntity;
import uniandes.edu.co.proyecto.entities.CostoEntity;
import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;
import uniandes.edu.co.proyecto.repositories.CostoRepository;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;

@SpringBootApplication
public class ProyectoApplication implements CommandLineRunner{

	// @Autowired
	// private UsuarioRepository usuarioRepository;

	// @Autowired
	// private ServicioRepository servicioRepository;

	// @Autowired
	// private TarifaRepository tarifaRepository;

	@Autowired
	private CostoRepository costoRepository;

	// @Autowired
	// private ViajeRepository viajeRepository;
	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Override
	public void run(String...arg){
		// Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuarios();
		// for (UsuarioEntity usuario:usuarios){
		// 	System.out.println(usuario);
		// }

		// Collection<ServicioEntity> servicios = servicioRepository.darServicios();
		// for (ServicioEntity servicio:servicios){
		// 	System.out.println(servicio);
		// }

		// Collection<TarifaEntity> tarifas = tarifaRepository.darTarifas();
		// for (TarifaEntity tarifa:tarifas){
		// 	System.out.println(tarifa);
		// }

		Collection<CostoEntity> costos = costoRepository.darCostos();
		for (CostoEntity costo:costos){
			System.out.println(costo);
		}

		// Collection<ViajeEntity> viajes = viajeRepository.darViajes();
		// for (ViajeEntity viaje:viajes){
		// 	System.out.println(viaje);
		// }
	}

}
