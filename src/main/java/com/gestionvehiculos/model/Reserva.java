package com.gestionvehiculos.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modelo de dominio: representa una reserva de vehículo eléctrico.
 * Contiene los datos esenciales y calcula automáticamente el precio total según tipo de carga y horas de uso.
 * Inmutable tras creación (los setters están disponibles, pero no se usan en la app actual).
 */
public class Reserva {

    private String cliente;
    private String vehiculo;
    private LocalDate fecha;
    private int horasUso;
    private String tipoCarga;
    private double precioTotal;

    /**
     * Constructor principal.
     * Calcula el precio total al crear la reserva (no se recalcula después).
     *
     * @param cliente Nombre del cliente.
     * @param vehiculo Modelo del vehículo.
     * @param fecha Fecha de la reserva.
     * @param horasUso Duración en horas (1–24).
     * @param tipoCarga "Carga lenta" o "Carga rápida".
     */
    public Reserva(String cliente, String vehiculo, LocalDate fecha, int horasUso, String tipoCarga) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.horasUso = horasUso;
        this.tipoCarga = tipoCarga;
        this.precioTotal = calcularPrecio(horasUso, tipoCarga);
    }

    // Getters y setters estándar (generados)
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVehiculo() {
        return vehiculo;
    }
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHorasUso() {
        return horasUso;
    }
    public void setHorasUso(int horasUso) {
        this.horasUso = horasUso;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }
    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }
    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    /**
     * Calcula el precio según la política de la aplicación:
     * - Carga lenta: 10€/hora
     * - Carga rápida: 15€/hora
     *
     * @param horasUso Número de horas.
     * @param tipoCarga Tipo de carga ("Carga lenta" o "Carga rápida").
     * @return Precio total en euros.
     */
    private double calcularPrecio(int horasUso, String tipoCarga){
        if (Objects.equals(tipoCarga, "Lenta")){
            return horasUso * 10.0;
        } else {
            return horasUso * 15.0;
        }
    }

    // Métodos hashCode y equals generados (útiles si se usan colecciones como HashSet)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return horasUso == reserva.horasUso &&
                Double.compare(reserva.precioTotal, precioTotal) == 0 &&
                Objects.equals(cliente, reserva.cliente) &&
                Objects.equals(vehiculo, reserva.vehiculo) &&
                Objects.equals(fecha, reserva.fecha) &&
                Objects.equals(tipoCarga, reserva.tipoCarga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fecha, horasUso, tipoCarga, precioTotal);
    }
}
