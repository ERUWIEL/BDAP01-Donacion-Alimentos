package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.ContenidoDAO;
import com.donacionAlimentos.interfaces.IContenidoDAO;
import com.donacionAlimentos.models.Contenido;
import java.util.List;

/**
 *
 * @author LABCISCO-PC005
 */
public class ContenidoController {

    private final IContenidoDAO contenidoDAO;

    public ContenidoController() {
        this.contenidoDAO = new ContenidoDAO();
    }

    public boolean agregarContenido(Contenido contenido) {
        if (contenido == null || contenido.getIdAlimento() <= 0
                || contenido.getIdEntrega() <= 0 || contenido.getIdAportacion() <= 0) {
            System.out.println("Error: Contenido o datos obligatorios no válidos");
            return false;
        }
        return contenidoDAO.create(contenido);
    }

    public Contenido buscarContenidoPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return contenidoDAO.read(id);
    }

    public List<Contenido> buscarContenidos() {
        return contenidoDAO.readAll();
    }

    public boolean actualizarContenido(Contenido contenido) {
        if (contenido == null || contenido.getIdContenido() <= 0
                || contenido.getIdAlimento() <= 0 || contenido.getIdEntrega() <= 0
                || contenido.getIdAportacion() <= 0) {
            System.out.println("Error: Contenido no válido para actualizar");
            return false;
        }
        return contenidoDAO.update(contenido);
    }

    public boolean eliminarContenido(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return contenidoDAO.delete(id);
    }
}
