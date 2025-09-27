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
     * metodo que permite agregar a una nueva persona
     *
     * @param persona
     * @return
     */
    public boolean agregarPersona(Persona persona) {
        if (persona == null || persona.getNombre() == null || persona.getNombre().trim().isEmpty()
                || persona.getCorreo() == null || persona.getCorreo().trim().isEmpty()) {
            System.out.println("Error: Persona o datos obligatorios no válidos");
            return false;
        }
        return personaDAO.create(persona);
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
     * metodo que permite actualizar a una persona
     *
     * @param persona
     * @return
     */
    public boolean actualizarPersona(Persona persona) {
        if (persona == null || persona.getIdPersona() <= 0
                || persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            System.out.println("Error: Persona no válida para actualizar");
            return false;
        }
        return personaDAO.update(persona);
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
    public DefaultTableModel obtenerTablaClientes() {
        String[] columnas = {"ID", "NOMBRE", "DIRECCIÓN", "TELEFÓNO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Persona> lista = personaDAO.readAll();
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getDireccion(), p.getTelefono()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro
     *
     * @param filtro
     * @return formato de tabla
     */
    public DefaultTableModel obtenerTablaClientesPorFiltro(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "DIRECCIÓN", "TELEFÓNO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Persona> lista = personaDAO.readByFilter(filtro);
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getDireccion(), p.getTelefono()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro modal
     * @param filtro
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaClientesPorFiltroModal(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "TELEFÓNO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Persona> lista = personaDAO.readByFilterModal(filtro);
        for (Persona p : lista) {
            modelo.addRow(new Object[]{p.getIdPersona(), p.getNombre(), p.getDireccion(), p.getTelefono()});
        }
        return modelo;
    }
}
