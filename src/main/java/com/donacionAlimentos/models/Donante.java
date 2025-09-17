
package com.donacionAlimentos.models;

/**
 * clase que modela un donante de la tabla donantes
 * @author erwbyel
 */
public class Donante {
    private int idPersona;
    private String tipo;

    public Donante() {
    }
    
    public Donante(int idPersona, String tipo) {
        this.idPersona = idPersona;
        this.tipo = tipo;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Donantes{" + "idPersona=" + idPersona + ", tipo=" + tipo + '}';
    }
}
