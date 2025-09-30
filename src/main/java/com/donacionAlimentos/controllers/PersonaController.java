package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.PersonaDAO;
import com.donacionAlimentos.interfaces.IPersonaDAO;
import com.donacionAlimentos.models.Persona;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * clase control de personas
 *
 * @author erwbyel
 */
public class PersonaController {

    private final IPersonaDAO personaDAO;

    /**
     * metodo constructor de la clase
     */
    public PersonaController() {
        this.personaDAO = new PersonaDAO();
    }

    /**
     * metodo que permite agregar una nueva persona
     *
     * @param nombre
     * @param apPaterno
     * @param apMaterno
     * @param telefono
     * @param correo
     * @param direccion
     * @return
     */
    public boolean agregarPersona(String nombre, String apPaterno, String apMaterno, String telefono, String correo, String direccion) {
        if (nombre.isEmpty() || apPaterno.isEmpty() || direccion.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            return false;
        } else {
            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApPaterno(apPaterno);
            persona.setApMaterno(apMaterno);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
            return personaDAO.create(persona);
        }
    }

    /**
     * metodo que permite buscar a una persona por id
     *
     * @param id
     * @return
     */
    public Persona buscarPersonaPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return personaDAO.read(id);
    }

    /**
     * metodo que permite leer todas las personas
     *
     * @return
     */
    public List<Persona> buscarPersonas() {
        return personaDAO.readAll();
    }

    /**
     * metodo que permite actualizar una persona
     * @param id
     * @param nombre
     * @param apPaterno
     * @param apMaterno
     * @param telefono
     * @param correo
     * @param direccion
     * @return 
     */
    public boolean actualizarPersona(Integer id, String nombre, String apPaterno, String apMaterno, String telefono, String correo, String direccion) {
        if (id == null || nombre.isEmpty() || apPaterno.isEmpty() || direccion.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            return false;
        } else {
            Persona persona = new Persona();
            persona.setIdPersona(id);
            persona.setNombre(nombre);
            persona.setApPaterno(apPaterno);
            persona.setApMaterno(apMaterno);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
            return personaDAO.update(persona);
        }
    }

    /**
     * metodo que permite eliminar una persona
     *
     * @param id
     * @return
     */
    public boolean eliminarPersona(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return personaDAO.delete(id);
    }

    // metodos de utileria
    /**
     * metodo que tabula las personas sin filtro
     *
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaPersonas() {
        String[] columnas = {"ID", "NOMBRE", "PATERNO", "MATERNO", "TELEFONO", "CORREO", "DIRECCION"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        
        List<Persona> lista = personaDAO.readAll();
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getApPaterno(), p.getApMaterno() , p.getTelefono(), p.getCorreo(),p.getDireccion()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro
     *
     * @param filtro
     * @return formato de tabla
     */
    public DefaultTableModel obtenerTablaPersonasPorFiltro(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "PATERNO", "MATERNO", "TELEFONO", "CORREO", "DIRECCION"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Persona> lista = personaDAO.readByFilter(filtro);
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getApPaterno(), p.getApMaterno() , p.getTelefono(), p.getCorreo(),p.getDireccion()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro modal
     *
     * @param filtro
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaPersonasPorFiltroModal(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "APELLIDOS(S)", "TELEFONO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Persona> lista = personaDAO.readByFilterModal(filtro);
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getApPaterno() + "   " + p.getApMaterno(), p.getTelefono()});
        }
        return modelo;
    }
}
