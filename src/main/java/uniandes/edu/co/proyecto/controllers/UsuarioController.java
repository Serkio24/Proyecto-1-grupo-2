package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // create
    @PostMapping("/usuarios/new/save")
    public ResponseEntity<UsuarioResponse> guardarUsuario(@RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity ultimo = usuarioRepository.findTopByOrderByIdUsuarioDesc();
            Long nuevoId;
            if (ultimo == null || ultimo.getIdUsuario() == null) {
                nuevoId = 1L;
            } else {
                nuevoId = ultimo.getIdUsuario() + 1;
            }
            usuario.setIdUsuario(nuevoId);
            UsuarioEntity guardado = usuarioRepository.save(usuario);
            UsuarioResponse respuesta = new UsuarioResponse("Usuario creado exitosamente", guardado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al crear el usuario: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read
    @GetMapping("/usuarios")
    public ResponseEntity<Collection<UsuarioEntity>> obtenerTodosLosUsuarios() {
        try {
            List<UsuarioEntity> usuarios = usuarioRepository.buscarTodosLosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // read
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioEntity> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        try {
            UsuarioEntity usuario = usuarioRepository.buscarPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // update
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioEntity usuario) {
        try {
            usuario.setIdUsuario(id);
            UsuarioEntity actualizado = usuarioRepository.save(usuario);
            UsuarioResponse respuesta = new UsuarioResponse("Usuario actualizado exitosamente", actualizado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al actualizar el usuario: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete
    @DeleteMapping("/usuarios/{id}/delete")
    public ResponseEntity<UsuarioResponse> eliminarUsuario(@PathVariable("id") Long id) {
        try {
            usuarioRepository.eliminarUsuarioPorId(id);
            UsuarioResponse respuesta = new UsuarioResponse("Usuario eliminado exitosamente", null);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            UsuarioResponse error = new UsuarioResponse("Error al eliminar el usuario: " + e.getMessage(), null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class UsuarioResponse {
        private String mensaje;
        private UsuarioEntity usuario;

        public UsuarioResponse(String mensaje, UsuarioEntity usuario) {
            this.mensaje = mensaje;
            this.usuario = usuario;
        }

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
