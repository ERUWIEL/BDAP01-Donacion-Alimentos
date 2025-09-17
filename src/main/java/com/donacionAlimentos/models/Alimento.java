
package com.donacionAlimentos.models;

import java.math.BigDecimal;

/**
 * clase que modela un alimento de la tabla alimentos
 * @author erwbyel
 */
public class Alimento {
    private int idAlimento;
    private String nombre;
    private BigDecimal cantidad;
    private String tipo;

    public Alimento() {
    }
    
    public Alimento(int idAlimento, String nombre, BigDecimal cantidad, String tipo) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Alimento{" + "idAlimento=" + idAlimento + ", nombre=" + nombre + ", cantidad=" + cantidad + ", tipo=" + tipo + '}';
    }
}
