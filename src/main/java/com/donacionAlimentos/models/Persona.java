
package com.donacionAlimentos.models;

/**
 * clase que modela una persona de la tabla personas
 * @author erwbyel
 */
public class Persona {
    private int idPersona;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String correo;
    private String telefono;
    private String direccion;

    public Persona() {
    }
    
    public Persona(int idPersona, String nombre, String apPaterno, String apMaterno, String correo, String telefono, String direccion) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", nombre=" + nombre + ", apPaterno=" + apPaterno + ", apMaterno=" + apMaterno + ", correo=" + correo + ", telefono=" + telefono + ", direccion=" + direccion + '}';
    }
}
