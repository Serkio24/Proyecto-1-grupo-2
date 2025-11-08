package uniandes.edu.co.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniandes.edu.co.proyecto.dto.ReviewDTO;
import uniandes.edu.co.proyecto.entities.ReviewEntity;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.entities.ViajeEntity;
import uniandes.edu.co.proyecto.repositories.ReviewRepository;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.repositories.ViajeRepository;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //RF10 y RF11 - Registrar una review entre cliente y conductor
    @Transactional
    public ReviewEntity registrarReview(ReviewDTO reviewDTO) {
        ViajeEntity viaje = viajeRepository.darViaje(reviewDTO.getIdViaje());
        UsuarioEntity calificador = usuarioRepository.darUsuario(reviewDTO.getIdUsuario());
        UsuarioEntity calificado;

        if (calificador == null || viaje == null) {
            throw new IllegalArgumentException("El viaje o el usuario calificador no existen");
        }

        // Determinar quién es el calificado según el tipo de usuario
        if (calificador.getTipo().equalsIgnoreCase("Conductor")) {
            calificado = viaje.getIdServicio().getIdCliente();
        } else if (calificador.getTipo().equalsIgnoreCase("Cliente")) {
            calificado = viaje.getIdConductor();
        } else {
            throw new IllegalArgumentException("El usuario calificador no tiene un tipo válido");
        }

        // Validar que el calificador participe en el viaje
        boolean esConductor = calificador.getIdUsuario().equals(viaje.getIdConductor().getIdUsuario());
        boolean esCliente = calificador.getIdUsuario().equals(viaje.getIdServicio().getIdCliente().getIdUsuario());

        if (!esConductor && !esCliente) {
            throw new IllegalArgumentException("El usuario calificador no está asociado a este viaje");
        }

        // Insertar la review
        reviewRepository.insertarReview(
            calificador.getIdUsuario(),
            calificado.getIdUsuario(),
            viaje.getIdViaje(),
            reviewDTO.getPuntuacion(),
            reviewDTO.getComentario(),
            LocalDateTime.now()
        );

        return reviewRepository.darUltimaReview();
    }
}
