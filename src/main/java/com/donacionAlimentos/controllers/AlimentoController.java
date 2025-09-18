package com.donacionAlimentos.controllers;

import com.donacionAlimentos.dao.AlimentoDAO;
import com.donacionAlimentos.interfaces.IAlimentoDAO;
import com.donacionAlimentos.models.Alimento;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * clase que controla los dao a alimentos
 *
 * @author erwbyel
 */
public class AlimentoController {

    private final IAlimentoDAO alimentoDAO;

    /**
     * constructor normal
     * @throws SQLException 
     */
    public AlimentoController() throws SQLException {
        this.alimentoDAO = new AlimentoDAO();
    }

    public boolean agregarAlimento(Alimento alimento) {
        if (alimento == null || alimento.getNombre() == null || alimento.getNombre().trim().isEmpty()
                || alimento.getCantidad() == null || alimento.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Error: Alimento o datos obligatorios no válidos");
            return false;
        }
        return alimentoDAO.create(alimento);
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

}
