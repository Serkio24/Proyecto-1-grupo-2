package uniandes.edu.co.proyecto.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.services.MongoUsuarioService;

@RestController
@RequestMapping("/mongo/usuarios")
public class MongoUsuarioController {

    @Autowired
    private MongoUsuarioService usuarioService;

    // DTO para crear cliente con tarjeta
    public static class ClienteDTO {
        private UsuarioEntity usuario;
        private UsuarioEntity.TarjetaCredito tarjeta;

        public UsuarioEntity getUsuario() { return usuario; }
        public void setUsuario(UsuarioEntity usuario) { this.usuario = usuario; }

        public UsuarioEntity.TarjetaCredito getTarjeta() { return tarjeta; }
        public void setTarjeta(UsuarioEntity.TarjetaCredito tarjeta) { this.tarjeta = tarjeta; }
    }

    // Clase de respuesta
    public static class UsuarioResponse {
        private String mensaje;
        private UsuarioEntity usuario;

        public UsuarioResponse(String mensaje, UsuarioEntity usuario) {
            this.mensaje = mensaje;
            this.usuario = usuario;
        }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        public UsuarioEntity getUsuario() { return usuario; }
        public void setUsuario(UsuarioEntity usuario) { this.usuario = usuario; }
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> listarUsuarios() {
        try {
            List<UsuarioEntity> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> obtenerUsuario(@PathVariable String id) {
        try {
            Optional<UsuarioEntity> usuario = usuarioService.obtenerUsuarioPorId(id);
            return usuario.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar usuarios por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioEntity>> buscarUsuariosPorNombre(@RequestParam("nombre") String nombre) {
        try {
            List<UsuarioEntity> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear usuario simple
    @PostMapping
    public ResponseEntity<UsuarioEntity> crearUsuario(@RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity usuarioCreado = usuarioService.insertarUsuario(
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
            UsuarioEntity usuarioCreado = usuarioService.crearCliente(dto.getUsuario(), dto.getTarjeta());
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
    public ResponseEntity<UsuarioResponse> registrarConductor(@RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity usuarioCreado = usuarioService.registrarConductor(usuario);
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
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable String id, @RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            UsuarioResponse respuesta = new UsuarioResponse("UsuarioEntity actualizado exitosamente", usuarioActualizado);
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
            return ResponseEntity.ok("UsuarioEntity eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Agregar tarjeta de crédito a usuario existente
    @PostMapping("/{id}/tarjetas")
    public ResponseEntity<UsuarioResponse> agregarTarjeta(@PathVariable String id,
                                                          @RequestBody UsuarioEntity.TarjetaCredito tarjeta) {
        try {
            UsuarioEntity usuario = usuarioService.agregarTarjetaCredito(id, tarjeta);
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
                                                           @RequestBody UsuarioEntity.Servicio servicio) {
        try {
            UsuarioEntity usuario = usuarioService.agregarServicio(id, servicio);
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
                                                        @RequestBody UsuarioEntity.Viaje viaje) {
        try {
            UsuarioEntity usuario = usuarioService.agregarViaje(id, viaje);
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
                                                         @RequestBody UsuarioEntity.Review review) {
        try {
            UsuarioEntity usuario = usuarioService.agregarReview(id, review);
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
