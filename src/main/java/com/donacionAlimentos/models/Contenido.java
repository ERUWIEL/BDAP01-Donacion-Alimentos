package com.donacionAlimentos.models;

/**
 * clase que modela la tabla contenidos de la relacion Entregas - Alimentos
 *
 * @author erwbyel
 */
public class Contenido {
    private int idContenido;
    private int idAlimento;
    private int idEntrega;
    private int idAportacion;

    public Contenido() {
    }

    public Contenido(int idContenido, int idAlimento, int idEntrega, int idAportacion) {
        this.idContenido = idContenido;
        this.idAlimento = idAlimento;
        this.idEntrega = idEntrega;
        this.idAportacion = idAportacion;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdAportacion() {
        return idAportacion;
    }

    public void setIdAportacion(int idAportacion) {
        this.idAportacion = idAportacion;
    }

    @Override
    public String toString() {
        return "Contenido{" + "idContenido=" + idContenido + ", idAlimento=" + idAlimento + ", idEntrega=" + idEntrega + ", idAportacion=" + idAportacion + '}';
    }
}
