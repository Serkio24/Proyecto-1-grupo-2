package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.Usuario;
import uniandes.edu.co.proyecto.repositories.MongoUsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoUsuarioService {

    @Autowired
    private MongoUsuarioRepository usuarioRepository;

    // Crear un usuario simple
    @Transactional
    public Usuario insertarUsuario(String nombre, String numeroCelular, String numeroCedula,
                                   String correoElectronico, String tipoUsuario) {
        Usuario usuario = new Usuario(nombre, numeroCelular, numeroCedula, correoElectronico, tipoUsuario);
        return usuarioRepository.save(usuario);
    }

    // RF2 - Crear cliente con tarjeta de crédito
    @Transactional
    public Usuario crearCliente(Usuario usuario, Usuario.TarjetaCredito tarjeta) {
        // Establecer tipo de usuario como Cliente
        usuario.setTipoUsuario("Cliente");

        // Si se proporciona una tarjeta válida, agregarla
        if (tarjeta != null) {
            boolean titularVacio = (tarjeta.getTitularDeLaTarjeta() == null ||
                                   tarjeta.getTitularDeLaTarjeta().trim().isEmpty());
            boolean numeroVacio = (tarjeta.getNumeroTarjeta() == null ||
                                  tarjeta.getNumeroTarjeta().trim().isEmpty());
            boolean fechaVacia = (tarjeta.getFechaExpiracion() == null);
            boolean codigoVacio = (tarjeta.getCodigoSeguridad() == null);

            if (!titularVacio && !numeroVacio && !fechaVacia && !codigoVacio) {
                // Agregar la tarjeta al array de tarjetas del usuario
                List<Usuario.TarjetaCredito> tarjetas = new ArrayList<>();
                tarjetas.add(tarjeta);
                usuario.setTarjetasCredito(tarjetas);
            } else if (titularVacio || numeroVacio || fechaVacia || codigoVacio) {
                throw new IllegalArgumentException("Tarjeta incompleta: todos los campos son obligatorios o ninguno");
            }
        }

        // Guardar y retornar el usuario
        return usuarioRepository.save(usuario);
    }

    // RF3 - Registrar conductor
    @Transactional
    public Usuario registrarConductor(Usuario usuario) {
        usuario.setTipoUsuario("Conductor");
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuarios por nombre
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre);
    }

    // Actualizar usuario
    @Transactional
    public Usuario actualizarUsuario(String id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setNumeroCelular(usuarioActualizado.getNumeroCelular());
            usuario.setNumeroCedula(usuarioActualizado.getNumeroCedula());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }

    // Eliminar usuario
    @Transactional
    public void eliminarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    // Agregar tarjeta de crédito a un usuario existente
    @Transactional
    public Usuario agregarTarjetaCredito(String usuarioId, Usuario.TarjetaCredito tarjeta) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getTarjetasCredito() == null) {
                usuario.setTarjetasCredito(new ArrayList<>());
            }
            usuario.getTarjetasCredito().add(tarjeta);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
    }

    // Agregar servicio a un usuario
    @Transactional
    public Usuario agregarServicio(String usuarioId, Usuario.Servicio servicio) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getServicios() == null) {
                usuario.setServicios(new ArrayList<>());
            }
            usuario.getServicios().add(servicio);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
    }

    // Agregar viaje a un usuario
    @Transactional
    public Usuario agregarViaje(String usuarioId, Usuario.Viaje viaje) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getViajes() == null) {
                usuario.setViajes(new ArrayList<>());
            }
            usuario.getViajes().add(viaje);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
    }

    // Agregar review a un usuario
    @Transactional
    public Usuario agregarReview(String usuarioId, Usuario.Review review) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getReviews() == null) {
                usuario.setReviews(new ArrayList<>());
            }
            usuario.getReviews().add(review);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
    }
}
