package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Embeddable
public class UsuarioServiciosPK implements Serializable {

    @OneToOne
    @JoinColumn(name="idUsuario", referencedColumnName="idUsuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name="idTarjetaCredito", referencedColumnName="idTarjetaCredito")
    private TarjetaCreditoEntity tarjetaCredito;

    public UsuarioServiciosPK() {}

    public UsuarioServiciosPK(UsuarioEntity usuario, TarjetaCreditoEntity tarjetaCredito) {
        super();
        this.usuario = usuario;
        this.tarjetaCredito = tarjetaCredito;
    }

    // Getters y Setters
    public UsuarioEntity getUsuario() { return usuario; }
    public void setUsuario(UsuarioEntity usuario) { this.usuario = usuario; }

    public TarjetaCreditoEntity getTarjetaCredito() { return tarjetaCredito; }
    public void setTarjetaCredito(TarjetaCreditoEntity tarjetaCredito) { this.tarjetaCredito = tarjetaCredito; }
}
