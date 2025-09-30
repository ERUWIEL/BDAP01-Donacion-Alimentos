package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.OrganizacionDAO;
import com.donacionAlimentos.interfaces.IOrganizacionDAO;
import com.donacionAlimentos.models.Organizacion;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LABCISCO-PC005
 */
public class OrganizacionController {

    private final IOrganizacionDAO organizacionDAO;

    /**
     * metodo constructor de la clase
     */
    public OrganizacionController() {
        this.organizacionDAO = new OrganizacionDAO();
    }
    /**
     * metodo que permite agregar una organizacion
     * @param idPersona
     * @param nombreOrganizacion
     * @return 
     */
    public boolean agregarOrganizacion(Integer idPersona, String nombreOrganizacion) {
        if (idPersona == null || idPersona <= 0 || nombreOrganizacion.trim().isEmpty()) {
            System.out.println("Error: Organizacion no válida");
            return false;
        } else {
            Organizacion organizacion = new Organizacion();
            organizacion.setIdPersona(idPersona);
            organizacion.setNombreOrganizacion(nombreOrganizacion);
            return organizacionDAO.create(organizacion);
        }
    }
    /**
     * metodo que permite buscar una organizacion
     * @param id
     * @return 
     */
    public Organizacion buscarOrganizacionPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return organizacionDAO.read(id);
    }
    /**
     * metodo que permite buscar todas las organizaciones
     * @return 
     */
    public List<Organizacion> buscarOrganizaciones() {
        return organizacionDAO.readAll();
    }
    /**
     * metodo que permite actualizar una organizacion
     * @param idPersona
     * @param nombreOrganizacion
     * @return 
     */
    public boolean actualizarOrganizacion(Integer idPersona, String nombreOrganizacion) {
        if (idPersona == null || idPersona <= 0 || nombreOrganizacion.trim().isEmpty()) {
            System.out.println("Error: Organizacion no válida");
            return false;
        } else {
            Organizacion organizacion = new Organizacion();
            organizacion.setIdPersona(idPersona);
            organizacion.setNombreOrganizacion(nombreOrganizacion);
            return organizacionDAO.update(organizacion);
        }
    }
    /**
     * metodo que permite eliminar una organizacion
     * @param id
     * @return 
     */
    public boolean eliminarOrganizacion(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return organizacionDAO.delete(id);
    }
    
        // metodos de utileria
    /**
     * metodo que tabula las organizaciones sin filtro
     *
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaOrganizaciones() {
        String[] columnas = {"ID", "NOMBRE"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Organizacion> lista = organizacionDAO.readAll();
        for (Organizacion o : lista) {
            modelo.addRow(new Object[]{o.getIdPersona(), o.getNombreOrganizacion()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las organizaciones por filtro
     *
     * @param filtro
     * @return formato de tabla
     */
    public DefaultTableModel obtenerTablaOrganizacionesPorFiltro(String filtro) {
        String[] columnas = {"ID", "TIPO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Organizacion> lista = organizacionDAO.readByFilter(filtro);
        for (Organizacion o : lista) {
            modelo.addRow(new Object[]{o.getIdPersona(), o.getNombreOrganizacion()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las organizaciones por filtro modal
     *
     * @param filtro
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaOrganizacionesPorFiltroModal(String filtro) {
        String[] columnas = {"ID", "TIPO"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        List<Organizacion> lista = organizacionDAO.readByFilterModal(filtro);
        for (Organizacion o : lista) {
            modelo.addRow(new Object[]{o.getIdPersona(), o.getNombreOrganizacion()});
        }
        return modelo;
    }
}
