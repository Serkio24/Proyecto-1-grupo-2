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
import uniandes.edu.co.proyecto.entities.ServicioDestinoEntity;

import uniandes.edu.co.proyecto.entities.CiudadEntity;
import uniandes.edu.co.proyecto.entities.ComisionEntity;
import uniandes.edu.co.proyecto.entities.DisponibilidadEntity;
import uniandes.edu.co.proyecto.entities.FranjaHorariaEntity;
import uniandes.edu.co.proyecto.entities.NivelVehiculoEntity;
import uniandes.edu.co.proyecto.entities.PuntoGeograficoEntity;
import uniandes.edu.co.proyecto.entities.ReviewEntity;
import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.entities.UsuarioConductorEntity;
import uniandes.edu.co.proyecto.entities.UsuarioServiciosEntity;
import uniandes.edu.co.proyecto.entities.VehiculoEntity;

import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.ServicioRepository;
import uniandes.edu.co.proyecto.repositories.TarifaRepository;
import uniandes.edu.co.proyecto.repositories.CostoRepository;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;
import uniandes.edu.co.proyecto.repositories.ServicioDestinoRepository;

import uniandes.edu.co.proyecto.repositories.CiudadRepository;
import uniandes.edu.co.proyecto.repositories.ComisionRepository;
import uniandes.edu.co.proyecto.repositories.DisponibilidadRepository;
import uniandes.edu.co.proyecto.repositories.FranjaHorariaRepository;
import uniandes.edu.co.proyecto.repositories.NivelVehiculoRepository;
import uniandes.edu.co.proyecto.repositories.PuntoGeograficoRepository;
import uniandes.edu.co.proyecto.repositories.ReviewRepository;
import uniandes.edu.co.proyecto.repositories.TarjetaCreditoRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioConductorRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioServiciosRepository;
import uniandes.edu.co.proyecto.repositories.VehiculoRepository;

@SpringBootApplication
public class ProyectoApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private CostoRepository costoRepository;

    @Autowired
    private ComisionRepository comisionRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ServicioDestinoRepository servicioDestinoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Autowired
    private FranjaHorariaRepository franjaHorariaRepository;

    @Autowired
    private NivelVehiculoRepository nivelVehiculoRepository;

    @Autowired
    private PuntoGeograficoRepository puntoGeograficoRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @Autowired
    private UsuarioConductorRepository usuarioConductorRepository;

    @Autowired
    private UsuarioServiciosRepository usuarioServiciosRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuarios();
        for (UsuarioEntity usuario : usuarios) {
            System.out.println(usuario);
        }

        Collection<ServicioEntity> servicios = servicioRepository.darServicios();
        for (ServicioEntity servicio : servicios) {
            System.out.println(servicio);
        }

        Collection<TarifaEntity> tarifas = tarifaRepository.darTarifas();
        for (TarifaEntity tarifa : tarifas) {
            System.out.println(tarifa);
        }

        Collection<CostoEntity> costos = costoRepository.darCostos();
        for (CostoEntity costo : costos) {
            System.out.println(costo);
        }

        Collection<ComisionEntity> comisiones = comisionRepository.darComisiones();
        for (ComisionEntity comision : comisiones) {
            System.out.println(comision);
        }

        Collection<ViajeEntity> viajes = viajeRepository.darViajes();
        for (ViajeEntity viaje : viajes) {
            System.out.println(viaje);
        }

        Collection<ServicioDestinoEntity> servicioDestinos = servicioDestinoRepository.darServicioDestinos();
        for (ServicioDestinoEntity servicioDestino : servicioDestinos) {
            System.out.println(servicioDestino);
        }

        Collection<CiudadEntity> ciudades = ciudadRepository.darCiudades();
        for (CiudadEntity ciudad : ciudades) {
            System.out.println(ciudad);
        }

        Collection<DisponibilidadEntity> disponibilidades = disponibilidadRepository.darDisponibilidades();
        for (DisponibilidadEntity disponibilidad : disponibilidades) {
            System.out.println(disponibilidad);
        }

        Collection<FranjaHorariaEntity> franjas = franjaHorariaRepository.darFranjasHorarias();
        for (FranjaHorariaEntity franja : franjas) {
            System.out.println(franja);
        }

        Collection<NivelVehiculoEntity> niveles = nivelVehiculoRepository.darNivelesVehiculo();
        for (NivelVehiculoEntity nivel : niveles) {
            System.out.println(nivel);
        }

        Collection<PuntoGeograficoEntity> puntos = puntoGeograficoRepository.darPuntosGeograficos();
        for (PuntoGeograficoEntity punto : puntos) {
            System.out.println(punto);
        }

        Collection<ReviewEntity> reviews = reviewRepository.darReviews();
        for (ReviewEntity review : reviews) {
            System.out.println(review);
        }

        Collection<TarjetaCreditoEntity> tarjetas = tarjetaCreditoRepository.darTarjetas();
        for (TarjetaCreditoEntity tarjeta : tarjetas) {
            System.out.println(tarjeta);
        }

        Collection<UsuarioConductorEntity> usuarioConductores = usuarioConductorRepository.darUsuariosConductores();
        for (UsuarioConductorEntity usuarioConductor : usuarioConductores) {
            System.out.println(usuarioConductor);
        }

        Collection<UsuarioServiciosEntity> usuarioServicios = usuarioServiciosRepository.darUsuariosServicios();
        for (UsuarioServiciosEntity usuarioServicio : usuarioServicios) {
            System.out.println(usuarioServicio);
        }

        Collection<VehiculoEntity> vehiculos = vehiculoRepository.darVehiculos();
        for (VehiculoEntity vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }
}
