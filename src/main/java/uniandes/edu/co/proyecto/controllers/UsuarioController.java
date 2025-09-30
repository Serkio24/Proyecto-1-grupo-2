package uniandes.edu.co.proyecto.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.entities.UsuarioEntity;
import uniandes.edu.co.proyecto.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ Listar todos los usuarios
    @GetMapping
    public ResponseEntity<Collection<UsuarioEntity>> listarUsuarios() {
        try {
            Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Obtener un usuario por ID
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

    // ✅ Buscar usuarios por nombre (búsqueda parcial)
    @GetMapping("/buscar")
    public ResponseEntity<Collection<UsuarioEntity>> buscarUsuariosPorNombre(@RequestParam("nombre") String nombre) {
        try {
            Collection<UsuarioEntity> usuarios = usuarioRepository.darUsuariosPorNombre(nombre);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Crear un nuevo usuario
    @PostMapping("/new/save")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioEntity usuario) {
        try {
            usuarioRepository.insertarUsuario(
                usuario.getNombre(),
                usuario.getNumeroCelular(),
                usuario.getNumeroCedula(),
                usuario.getCorreoElectronico(),
                usuario.getTipo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }

    // ✅ Actualizar un usuario
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

    // ✅ Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario");
        }
    }
}
