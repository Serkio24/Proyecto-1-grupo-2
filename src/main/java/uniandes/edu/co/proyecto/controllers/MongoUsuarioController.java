package uniandes.edu.co.proyecto.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.Usuario;
import uniandes.edu.co.proyecto.services.MongoUsuarioService;

@RestController
@RequestMapping("/mongo/usuarios")
public class MongoUsuarioController {

    @Autowired
    private MongoUsuarioService usuarioService;

    // DTO para crear cliente con tarjeta
    public static class ClienteDTO {
        private Usuario usuario;
        private Usuario.TarjetaCredito tarjeta;

        public Usuario getUsuario() { return usuario; }
        public void setUsuario(Usuario usuario) { this.usuario = usuario; }

        public Usuario.TarjetaCredito getTarjeta() { return tarjeta; }
        public void setTarjeta(Usuario.TarjetaCredito tarjeta) { this.tarjeta = tarjeta; }
    }

    // Clase de respuesta
    public static class UsuarioResponse {
        private String mensaje;
        private Usuario usuario;

        public UsuarioResponse(String mensaje, Usuario usuario) {
            this.mensaje = mensaje;
            this.usuario = usuario;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public Usuario getUsuario() { return usuario; }
        public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String id) {
        try {
            Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
            return usuario.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar usuarios por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@RequestParam("nombre") String nombre) {
        try {
            List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear usuario simple
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.insertarUsuario(
                usuario.getNombre(),
                usuario.getNumeroCelular(),
                usuario.getNumeroCedula(),
                usuario.getCorreoElectronico(),
                usuario.getTipoUsuario()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // RF2 - Crear cliente con tarjeta de crédito
    @PostMapping("/new/cliente")
    public ResponseEntity<UsuarioResponse> crearCliente(@RequestBody ClienteDTO dto) {
        try {
            Usuario usuarioCreado = usuarioService.crearCliente(dto.getUsuario(), dto.getTarjeta());
            UsuarioResponse respuesta = new UsuarioResponse("Cliente creado exitosamente", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (IllegalArgumentException e) {
            UsuarioResponse error = new UsuarioResponse("Tarjeta incompleta: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            UsuarioResponse error = new UsuarioResponse("Error al crear cliente: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // RF3 - Registrar conductor
    @PostMapping("/new/conductor")
    public ResponseEntity<UsuarioResponse> registrarConductor(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCreado = usuarioService.registrarConductor(usuario);
            UsuarioResponse respuesta = new UsuarioResponse("Conductor creado exitosamente", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            UsuarioResponse error = new UsuarioResponse("Error al crear conductor: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            UsuarioResponse respuesta = new UsuarioResponse("Usuario actualizado exitosamente", usuarioActualizado);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            UsuarioResponse error = new UsuarioResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al actualizar usuario", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Agregar tarjeta de crédito a usuario existente
    @PostMapping("/{id}/tarjetas")
    public ResponseEntity<UsuarioResponse> agregarTarjeta(@PathVariable String id,
                                                          @RequestBody Usuario.TarjetaCredito tarjeta) {
        try {
            Usuario usuario = usuarioService.agregarTarjetaCredito(id, tarjeta);
            UsuarioResponse respuesta = new UsuarioResponse("Tarjeta agregada exitosamente", usuario);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            UsuarioResponse error = new UsuarioResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al agregar tarjeta", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Agregar servicio a usuario
    @PostMapping("/{id}/servicios")
    public ResponseEntity<UsuarioResponse> agregarServicio(@PathVariable String id,
                                                           @RequestBody Usuario.Servicio servicio) {
        try {
            Usuario usuario = usuarioService.agregarServicio(id, servicio);
            UsuarioResponse respuesta = new UsuarioResponse("Servicio agregado exitosamente", usuario);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            UsuarioResponse error = new UsuarioResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al agregar servicio", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Agregar viaje a usuario
    @PostMapping("/{id}/viajes")
    public ResponseEntity<UsuarioResponse> agregarViaje(@PathVariable String id,
                                                        @RequestBody Usuario.Viaje viaje) {
        try {
            Usuario usuario = usuarioService.agregarViaje(id, viaje);
            UsuarioResponse respuesta = new UsuarioResponse("Viaje agregado exitosamente", usuario);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            UsuarioResponse error = new UsuarioResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al agregar viaje", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Agregar review a usuario
    @PostMapping("/{id}/reviews")
    public ResponseEntity<UsuarioResponse> agregarReview(@PathVariable String id,
                                                         @RequestBody Usuario.Review review) {
        try {
            Usuario usuario = usuarioService.agregarReview(id, review);
            UsuarioResponse respuesta = new UsuarioResponse("Review agregado exitosamente", usuario);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            UsuarioResponse error = new UsuarioResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al agregar review", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
