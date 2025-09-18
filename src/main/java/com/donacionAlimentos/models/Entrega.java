package com.donacionAlimentos.models;

import java.sql.Date;

/**
 * clase que modela la tabla entregas
 *
 * @author erwbyel
 */
public class Entrega {

    public static enum Estado {
        PENDIENTE, EN_TRANSITO, ENTREGADA, CANCELADA
    }
    
    private int idEntrega;
    private Date fechaEntrega;
    private Estado estadoEntrega;
    private int idOrganizacion;

    public Entrega() {
    }
    
    public Entrega(int idEntrega, Date fechaEntrega, Estado estadoEntrega, int idOrganizacion) {
        this.idEntrega = idEntrega;
        this.fechaEntrega = fechaEntrega;
        this.estadoEntrega = estadoEntrega;
        this.idOrganizacion = idOrganizacion;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Estado getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(Estado estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    @Override
    public String toString() {
        return "Entrega{" + "idEntrega=" + idEntrega + ", fechaEntrega=" + fechaEntrega + ", estadoEntrega=" + estadoEntrega + ", idOrganizacion=" + idOrganizacion + '}';
    }
}
