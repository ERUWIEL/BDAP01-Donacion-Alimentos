package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.AportacionDAO;
import com.donacionAlimentos.interfaces.IAportacionDAO;
import com.donacionAlimentos.models.Aportacion;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class AportacionController {

    private final IAportacionDAO aportacionDAO;

    public AportacionController() {
        this.aportacionDAO = new AportacionDAO();
    }

    public boolean agregarAportacion(Aportacion aportacion) {
        if (aportacion == null || aportacion.getFechaCaducidad() == null
                || aportacion.getCantidad().intValue() <= 0 || aportacion.getIdDonante() <= 0
                || aportacion.getIdAlimento() <= 0) {
            System.out.println("Error: Aportación o datos obligatorios no válidos");
            return false;
        }

        return aportacionDAO.create(aportacion);
    }

    public Aportacion buscarAportacionPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }

        return aportacionDAO.read(id);
    }

    public List<Aportacion> buscarAportaciones() {
        return aportacionDAO.readAll();
    }

    public boolean actualizarAportacion(Aportacion aportacion) {
        if (aportacion == null || aportacion.getIdAportacion() <= 0
                || aportacion.getFechaCaducidad() == null || aportacion.getCantidad().intValue() <= 0) {
            System.out.println("Error: Aportación no válida para actualizar");
            return false;
        }
        return aportacionDAO.update(aportacion);
    }

    public boolean eliminarAportacion(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }

        return aportacionDAO.delete(id);
    }

}
