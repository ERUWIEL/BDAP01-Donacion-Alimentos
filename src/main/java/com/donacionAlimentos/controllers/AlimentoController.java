package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.AlimentoDAO;
import com.donacionAlimentos.dao.AportacionDAO;
import com.donacionAlimentos.interfaces.IAlimentoDAO;
import com.donacionAlimentos.interfaces.IAportacionDAO;
import com.donacionAlimentos.models.Alimento;
import com.donacionAlimentos.models.Aportacion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * clase que controla los dao a alimentos
 *
 * @author erwbyel
 */
public class AlimentoController {

    private final IAlimentoDAO alimentoDAO;
    private final IAportacionDAO aportacionDAO;

    /**
     * constructor normal
     */
    public AlimentoController() {
        this.alimentoDAO = new AlimentoDAO();
        this.aportacionDAO = new AportacionDAO();
    }

    /**
     * metodo
     * @param nombre
     * @param tipo
     * @param cantidad
     * @param idDonante
     * @param fchCaducidad
     * @return 
     */
    public boolean agregarAlimento(String nombre, String tipo, BigDecimal cantidad, Integer idDonante, LocalDate fchCaducidad) {
        LocalDate fechaActual = LocalDate.now();
        if (nombre == null || nombre.trim().isBlank() || tipo == null || tipo.trim().isBlank() || cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0
                || idDonante == null || fchCaducidad == null || fchCaducidad.isBefore(fechaActual)) {
            System.out.println("Error: datos no validos");
            return false;
        }
        return true;
        //Aportacion aportacion = new Aportacion(0, fechaCaducidad, cantidad, , 0);
        //aportacionDAO.create(aportacion);
        
        //Alimento alimento = new Alimento();
        //alimento.set
               
        //Alimento alimento = alimentoDAO.read(idDonante);
        //if( == null){
        //    return alimentoDAO.update(alimento);
        //}else{
        //    return alimentoDAO.create(alimento);
        //}
    }

    public Alimento buscarAlimentoPorId(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido");
            return null;
        }
        return alimentoDAO.read(id);
    }

    public List<Alimento> buscarAlimentos() {
        return alimentoDAO.readAll();
    }

    public boolean actualizarAlimento(Alimento alimento) {
        if (alimento == null || alimento.getIdAlimento() <= 0
                || alimento.getNombre() == null || alimento.getNombre().trim().isEmpty()) {
            System.out.println("Error: Alimento no válido para actualizar");
            return false;
        }

        return alimentoDAO.update(alimento);
    }

    public boolean eliminarAlimento(int id) {
        if (id <= 0) {
            System.out.println("Error: ID no válido para eliminar");
            return false;
        }
        return alimentoDAO.delete(id);
    }

    // metodos de utileria
    /**
     * metodo que tabula las alimentos sin filtro
     *
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaAlimentos() {
        String[] columnas = {"ID", "NOMBRE", "TIPO", "CANTIDAD"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Alimento> lista = alimentoDAO.readAll();
        for (Alimento a : lista) {
            modelo.addRow(new Object[]{a.getIdAlimento(), a.getNombre(), a.getTipo(), a.getCantidad()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las alimentos por filtro
     *
     * @param filtro
     * @return formato de tabla
     */
    public DefaultTableModel obtenerTablaAlimentosPorFiltro(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "TIPO", "CANTIDAD"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Alimento> lista = alimentoDAO.readByFilter(filtro);
        for (Alimento a : lista) {
            modelo.addRow(new Object[]{a.getIdAlimento(), a.getNombre(), a.getTipo(), a.getCantidad()});
        }
        return modelo;
    }

    /**
     * metodo que tabula las alimentos por filtro modal
     *
     * @param filtro
     * @return formato tabla
     */
    public DefaultTableModel obtenerTablaAlimentosPorFiltroModal(String filtro) {
        String[] columnas = {"ID", "NOMBRE", "TIPO", "CANTIDAD"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        List<Alimento> lista = alimentoDAO.readByFilterModal(filtro);
        for (Alimento a : lista) {
            modelo.addRow(new Object[]{a.getIdAlimento(), a.getNombre(), a.getTipo(), a.getCantidad()});
        }
        return modelo;
    }
}
