package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.dto.ClienteDTO;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;
import uniandes.edu.co.proyecto.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<Collection<UsuarioEntity>> listarUsuarios() {
        try {
            Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> obtenerUsuario(@PathVariable Long id) {
        try {
            UsuarioEntity usuario = usuarioRepository.darUsuario(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar usuarios por nombre (búsqueda parcial)
    @GetMapping("/buscar")
    public ResponseEntity<Collection<UsuarioEntity>> buscarUsuariosPorNombre(@RequestParam("nombre") String nombre) {
        try {
            Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuariosPorNombre(nombre);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar un usuario
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        try {
            usuarioRepository.actualizarUsuario(
                id,
                usuario.getNombre(),
                usuario.getNumeroCelular(),
                usuario.getNumeroCedula(),
                usuario.getCorreoElectronico(),
                usuario.getTipo()
            );
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario");
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario");
        }
    }

    // Registrar usuario de servicios (RF2)
    @PostMapping("/new/cliente")
    public ResponseEntity<UsuarioEntity> crearCliente(@RequestBody ClienteDTO dto) {
        try {
            UsuarioEntity usuarioCreado = usuarioService.crearCliente(dto.getUsuario(), dto.getTarjeta());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (IllegalArgumentException e) {
            // Tarjeta incompleta
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Registrar usuario conductor (RF3)
    @PostMapping("/new/conductor")
    public ResponseEntity<UsuarioResponse> registrarConductor(@RequestBody UsuarioEntity usuario) {
        try {
            // Insertar usuario con rol "Conductor"
            usuarioRepository.insertarUsuario(
                usuario.getNombre(),
                usuario.getNumeroCelular(),
                usuario.getNumeroCedula(),
                usuario.getCorreoElectronico(),
                "Conductor");

            // Obtener el último usuario insertado
            UsuarioEntity usuarioCreado = usuarioRepository.darUltimoUsuario();

            // Construir respuesta
            UsuarioResponse respuesta = new UsuarioResponse("Conductor creado exitosamente", usuarioCreado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            UsuarioResponse error = new UsuarioResponse("Error al crear el conductor: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public class UsuarioResponse {
        private String mensaje;
        private UsuarioEntity usuario;

        // Constructor completo
        public UsuarioResponse(String mensaje, UsuarioEntity usuario) {
            this.mensaje = mensaje;
            this.usuario = usuario;
        }

        // Getters y setters
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public UsuarioEntity getUsuario() {
            return usuario;
        }

        public void setUsuario(UsuarioEntity usuario) {
            this.usuario = usuario;
        }
    }
}
