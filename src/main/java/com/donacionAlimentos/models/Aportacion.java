
package com.donacionAlimentos.models;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * clase que modela la tabla aportaciones de la relacion Donante - Alimento
 * @author erwbyel
 */
public class Aportacion {
    private int idAportacion;
    private Date fechaCaducidad;
    private BigDecimal cantidad;
    private int idDonante;
    private int idAlimento;

    public Aportacion() {
    }

    public Aportacion(int iAportacion, Date fechaCaducidad, BigDecimal cantidad, int idDonante, int idAlimento) {
        this.idAportacion = iAportacion;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidad = cantidad;
        this.idDonante = idDonante;
        this.idAlimento = idAlimento;
    }

    public int getIdAportacion() {
        return idAportacion;
    }

    public void setIdAportacion(int idAportacion) {
        this.idAportacion = idAportacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(int idDonante) {
        this.idDonante = idDonante;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    @Override
    public String toString() {
        return "Aportacion{" + "idAportacion=" + idAportacion + ", fechaCaducidad=" + fechaCaducidad + ", cantidad=" + cantidad + ", iDonante=" + idDonante + ", idAlimento=" + idAlimento + '}';
    }
}
