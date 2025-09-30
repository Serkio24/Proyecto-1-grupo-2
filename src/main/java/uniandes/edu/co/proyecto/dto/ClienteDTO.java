package uniandes.edu.co.proyecto.dto;

import uniandes.edu.co.proyecto.entities.TarjetaCreditoEntity;
import uniandes.edu.co.proyecto.entities.UsuarioEntity;

public class ClienteDTO {
    private UsuarioEntity usuario;
    private TarjetaCreditoEntity tarjeta;

    public UsuarioEntity getUsuario() { return usuario; }
    public void setUsuario(UsuarioEntity usuario) { this.usuario = usuario; }

    public TarjetaCreditoEntity getTarjeta() { return tarjeta; }
    public void setTarjeta(TarjetaCreditoEntity tarjeta) { this.tarjeta = tarjeta; }
}
