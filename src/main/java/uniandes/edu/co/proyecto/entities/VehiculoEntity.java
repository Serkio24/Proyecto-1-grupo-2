package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="vehiculos")
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idVehiculo;

    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private String ciudadExpedicion;
    private int capacidadPasajeros;
    private String nivel;

    // Constructor con parámetros
    public VehiculoEntity(String tipo, String marca, String modelo, String color, 
                          String placa, String ciudadExpedicion, int capacidadPasajeros, String nivel) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.ciudadExpedicion = ciudadExpedicion;
        this.capacidadPasajeros = capacidadPasajeros;
        this.nivel = nivel;
    }

    // Constructor vacío
    public VehiculoEntity() {}

    // Getters
    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCiudadExpedicion() {
        return ciudadExpedicion;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public String getNivel() {
        return nivel;
    }

    // Setters
    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setCiudadExpedicion(String ciudadExpedicion) {
        this.ciudadExpedicion = ciudadExpedicion;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
