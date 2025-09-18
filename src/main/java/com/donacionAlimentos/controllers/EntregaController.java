package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.EntregaDAO;
import com.donacionAlimentos.interfaces.IEntregaDAO;
import com.donacionAlimentos.models.Entrega;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class EntregaController {

    private final IEntregaDAO entregaDAO;

    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
    }

    public boolean agregarEntrega(Entrega entrega) {
        if (entrega == null || entrega.getFechaEntrega() == null
                || entrega.getEstadoEntrega().name() == null || entrega.getEstadoEntrega().name().trim().isEmpty()
                || entrega.getIdOrganizacion() <= 0) {
            System.out.println("Error: Entrega o datos obligatorios no válidos");
            return false;
        }
        return entregaDAO.create(entrega);
    }

    public Entrega buscarEntregaPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return entregaDAO.read(id);
    }

    public List<Entrega> buscarEntregas() {
        return entregaDAO.readAll();
    }

    public boolean actualizarEntrega(Entrega entrega) {
        if (entrega == null || entrega.getIdEntrega() <= 0
                || entrega.getFechaEntrega() == null || entrega.getEstadoEntrega() == null
                || entrega.getEstadoEntrega().name().trim().isEmpty()) {
            System.out.println("Error: Entrega no válida para actualizar");
            return false;
        }
        return entregaDAO.update(entrega);
    }

    public boolean eliminarEntrega(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return entregaDAO.delete(id);
    }

}
