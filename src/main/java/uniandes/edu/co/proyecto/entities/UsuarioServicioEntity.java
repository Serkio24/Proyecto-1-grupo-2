package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_servicio")
public class UsuarioServicioEntity {

    @EmbeddedId
    private UsuarioServicioPK pk;

    public UsuarioServicioEntity() {}

    public UsuarioServicioEntity(UsuarioEntity usuario, TarjetaCreditoEntity tarjetaCredito) {
        this.pk = new UsuarioServicioPK(usuario, tarjetaCredito);
    }

    // Getters y Setters
    public UsuarioServicioPK getPk() { return pk; }
    public void setPk(UsuarioServicioPK pk) { this.pk = pk; }
}

