package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.TarjetaCreditoRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void insertarUsuario(String nombre, String numeroTelefono, String cedula, String email, String tipo) {
        usuarioRepository.insertarUsuario(nombre, numeroTelefono, cedula, email, tipo);
    }

    //nuevo RF2
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public UsuarioEntity crearCliente(UsuarioEntity usuario, TarjetaCreditoEntity tarjeta) {
        usuarioRepository.insertarUsuario(
            usuario.getNombre(),
            usuario.getNumeroCelular(),
            usuario.getNumeroCedula(),
            usuario.getCorreoElectronico(),
            "Cliente"
        );

        UsuarioEntity usuarioCreado = usuarioRepository.darUltimoUsuario();

        if (tarjeta != null) {
            boolean titularVacio = (tarjeta.getTitularDeLaTarjeta() == null || tarjeta.getTitularDeLaTarjeta().trim().isEmpty());
            boolean numeroVacio = (tarjeta.getNumeroTarjeta() == null || tarjeta.getNumeroTarjeta().trim().isEmpty());
            boolean fechaVacia = (tarjeta.getFechaExpiracion() == null);
            boolean codigoVacio = (tarjeta.getCodigoSeguridad() == null);


            if (!titularVacio && !numeroVacio && !fechaVacia && !codigoVacio) {
                tarjeta.setCliente(usuarioCreado);
                tarjetaCreditoRepository.insertarTarjeta(
                    tarjeta.getTitularDeLaTarjeta(),
                    tarjeta.getNumeroTarjeta(),
                    tarjeta.getFechaExpiracion(),
                    tarjeta.getCodigoSeguridad(),
                    usuarioCreado.getIdUsuario()
                );
            } else if (titularVacio || numeroVacio || fechaVacia || codigoVacio) {
                throw new IllegalArgumentException("Tarjeta incompleta: todos los campos son obligatorios o ninguno");
            }
        }

        return usuarioCreado;
    }

    // RF3 - Registrar usuario conductor con transaccionalidad
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UsuarioEntity registrarConductor(UsuarioEntity usuario) {
        // Insertar usuario con rol "Conductor"
        usuarioRepository.insertarUsuario(
            usuario.getNombre(),
            usuario.getNumeroCelular(),
            usuario.getNumeroCedula(),
            usuario.getCorreoElectronico(),
            "Conductor"
        );

        // Obtener el último usuario insertado
        UsuarioEntity usuarioCreado = usuarioRepository.darUltimoUsuario();

        // Retornar el usuario creado (la transacción se confirma aquí si no hubo error)
        return usuarioCreado;
    }

}
