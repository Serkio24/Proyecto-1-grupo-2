package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_servicio")
public class UsuarioServiciosEntity {

    @EmbeddedId
    private UsuarioServiciosPK pk;

    public UsuarioServiciosEntity() {}

    public UsuarioServiciosEntity(UsuarioEntity usuario, TarjetaCreditoEntity tarjetaCredito) {
        this.pk = new UsuarioServiciosPK(usuario, tarjetaCredito);
    }

    // Getters y Setters
    public UsuarioServiciosPK getPk() { return pk; }
    public void setPk(UsuarioServiciosPK pk) { this.pk = pk; }
}

