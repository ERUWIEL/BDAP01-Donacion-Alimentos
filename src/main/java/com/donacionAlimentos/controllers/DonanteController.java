package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.DonanteDAO;
import com.donacionAlimentos.interfaces.IDonanteDAO;
import com.donacionAlimentos.models.Donante;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LABCISCO-PC005
 */
public class DonanteController {

    private final IDonanteDAO donanteDAO;

    /**
     * metodo constructor de la clase
     */
    public DonanteController() {
        this.donanteDAO = new DonanteDAO();
    }
    
    /**
     * metodo que permite agregar un nuevo donante en base una persona existente
     * @param idPersona
     * @param tipo
     * @return 
     */
    public boolean agregarDonante(Integer idPersona, String tipo) {
        if (idPersona == null || idPersona <= 0 || tipo.trim().isEmpty()) {
            System.out.println("Error: Donante no válido");
            return false;
        } else {
            Donante donante = new Donante();
            donante.setIdPersona(idPersona);
            donante.setTipo(tipo);
            return donanteDAO.create(donante);
        }
    }

    /**
     * metodo que permite la busqueda de un donante por id
     * @param id
     * @return 
     */
    public Donante buscarDonantePorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }

        return donanteDAO.read(id);
    }

    /**
     * metodo que lista todos los donantes
     * @return 
     */
    public List<Donante> buscarDonantes() {
        return donanteDAO.readAll();
    }

    /**
     * metodo que permite actualizar un donante
     * @param idPersona
     * @param tipoNuevo
     * @return 
     */
    public boolean actualizarDonante(Integer idPersona, String tipoNuevo) {
        if (idPersona == null || tipoNuevo.trim().isEmpty()) {
            System.out.println("Error: Donante no válido");
            return false;
        } else {
            Donante donante = new Donante();
            donante.setIdPersona(idPersona);
            donante.setTipo(tipoNuevo);
            return donanteDAO.update(donante);
        }
    }

    /**
     * metodo que permite la eliminacion de un donante
     * @param id
     * @return 
     */
    public boolean eliminarDonante(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }

        return donanteDAO.delete(id);
    }

    // metodos de utileria
    /**
     * metodo que tabula las personas sin filtro
     *
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaDonantes() {
        String[] columnas = {"ID", "TIPO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Donante> lista = donanteDAO.readAll();
        for (Donante d : lista) {
            modelo.addRow(new Object[]{d.getIdPersona(), d.getTipo()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro
     *
     * @param filtro
     * @return formato de tabla
     */
    public DefaultTableModel obtenerTablaDonantesPorFiltro(String filtro) {
        String[] columnas = {"ID", "TIPO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Donante> lista = donanteDAO.readByFilter(filtro);
        for (Donante d : lista) {
            modelo.addRow(new Object[]{d.getIdPersona(), d.getTipo()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las personas por filtro modal
     *
     * @param filtro
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaDonantesPorFiltroModal(String filtro) {
        String[] columnas = {"ID", "TIPO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Donante> lista = donanteDAO.readByFilter(filtro);
        for (Donante d : lista) {
            modelo.addRow(new Object[]{d.getIdPersona(), d.getTipo()});
        }
        return modelo;
    }

}
