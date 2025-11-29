package uniandes.edu.co.proyecto.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "VEHICULOS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehiculoEntity {

    @Id
    private Long idVehiculo;          // mapea al _id de Mongo
    // id del conductor dueño/asignado a este vehículo
    private Long idConductor;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private String ciudadExpedicion;
    private Integer capacidadPasajeros;
    private String nivel;

    public VehiculoEntity() {
    }

    public VehiculoEntity(Long idConductor, String tipo, String marca, String modelo, String color, String placa, String ciudadExpedicion, Integer capacidadPasajeros, String nivel) {
        this.idConductor = idConductor;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.ciudadExpedicion = ciudadExpedicion;
        this.capacidadPasajeros = capacidadPasajeros;
        this.nivel = nivel;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCiudadExpedicion() {
        return ciudadExpedicion;
    }

    public void setCiudadExpedicion(String ciudadExpedicion) {
        this.ciudadExpedicion = ciudadExpedicion;
    }

    public Integer getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(Integer capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
