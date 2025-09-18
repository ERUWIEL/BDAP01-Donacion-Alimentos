package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.PersonaDAO;
import com.donacionAlimentos.interfaces.IPersonaDAO;
import com.donacionAlimentos.models.Persona;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class PersonaController {

    private final IPersonaDAO personaDAO;

    public PersonaController() {
        this.personaDAO = new PersonaDAO();
    }

    public boolean agregarPersona(Persona persona) {
        if (persona == null || persona.getNombre() == null || persona.getNombre().trim().isEmpty()
                || persona.getCorreo() == null || persona.getCorreo().trim().isEmpty()) {
            System.out.println("Error: Persona o datos obligatorios no válidos");
            return false;
        }
        return personaDAO.create(persona);
    }

    public Persona buscarPersonaPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return personaDAO.read(id);
    }

    public List<Persona> buscarPersonas() {
        return personaDAO.readAll();
    }

    public boolean actualizarPersona(Persona persona) {
        if (persona == null || persona.getIdPersona() <= 0
                || persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            System.out.println("Error: Persona no válida para actualizar");
            return false;
        }
        return personaDAO.update(persona);
    }

    public boolean eliminarPersona(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return personaDAO.delete(id);
    }
}
