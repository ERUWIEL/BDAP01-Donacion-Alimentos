package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.OrganizacionDAO;
import com.donacionAlimentos.interfaces.IOrganizacionDAO;
import com.donacionAlimentos.models.Organizacion;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class OrganizacionController {

    private final IOrganizacionDAO organizacionDAO;

    public OrganizacionController() {
        this.organizacionDAO = new OrganizacionDAO();
    }

    public boolean agregarOrganizacion(Organizacion organizacion) {
        if (organizacion == null || organizacion.getIdPersona() <= 0
                || organizacion.getNombreOrganizacion() == null || organizacion.getNombreOrganizacion().trim().isEmpty()) {
            System.out.println("Error: Organización o datos obligatorios no válidos");
            return false;
        }
        return organizacionDAO.create(organizacion);
    }

    public Organizacion buscarOrganizacionPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return organizacionDAO.read(id);
    }

    public List<Organizacion> buscarOrganizaciones() {
        return organizacionDAO.readAll();
    }

    public boolean actualizarOrganizacion(Organizacion organizacion) {
        if (organizacion == null || organizacion.getIdPersona() <= 0
                || organizacion.getNombreOrganizacion() == null || organizacion.getNombreOrganizacion().trim().isEmpty()) {
            System.out.println("Error: Organización no válida para actualizar");
            return false;
        }

        return organizacionDAO.update(organizacion);
    }

    public boolean eliminarOrganizacion(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return organizacionDAO.delete(id);
    }

}
