package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.DonanteDAO;
import com.donacionAlimentos.interfaces.IDonanteDAO;
import com.donacionAlimentos.models.Donante;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class DonanteController {

    private final IDonanteDAO donanteDAO;

    public DonanteController() {
        this.donanteDAO = new DonanteDAO();
    }

    public boolean agregarDonante(Donante donante) {
        if (donante == null || donante.getIdPersona() <= 0) {
            System.out.println("Error: Donante no válido");
            return false;
        }

        return donanteDAO.create(donante);
    }

    public Donante buscarDonantePorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }

        return donanteDAO.read(id);
    }

    public List<Donante> buscarDonantes() {
        return donanteDAO.readAll();
    }

    public boolean actualizarDonante(Donante donante) {
        if (donante == null || donante.getIdPersona() <= 0) {
            System.out.println("Error: Donante no válido para actualizar");
            return false;
        }

        return donanteDAO.update(donante);
    }

    public boolean eliminarDonante(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }

        return donanteDAO.delete(id);
    }

}
