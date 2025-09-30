package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class NivelVehiculoPK implements Serializable {

    private String modelo;
    private int capacidadPasajeros;

    public NivelVehiculoPK() {}

    public NivelVehiculoPK(String modelo, int capacidadPasajeros) {
        this.modelo = modelo;
        this.capacidadPasajeros = capacidadPasajeros;
    }

    // Getters y setters
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getCapacidadPasajeros() { return capacidadPasajeros; }
    public void setCapacidadPasajeros(int capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }
}
