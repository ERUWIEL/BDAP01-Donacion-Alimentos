package com.donacionAlimentos.models;

/**
 * clase que modela una organizacion de la tabla organizaciones
 * @author erwbyel
 */
public class Organizacion {
    private int idPersona;
    private String nombreOrganizacion;

    public Organizacion() {
    }
    
    public Organizacion(int idPersona, String nombreOrganizacion) {
        this.idPersona = idPersona;
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    @Override
    public String toString() {
        return "Organizaciones{" + "idPersona=" + idPersona + ", nombreOrganizacion=" + nombreOrganizacion + '}';
    }
}
