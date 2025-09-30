package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Alimento;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IAlimentoDAO {

    boolean create(Alimento alimento);

    Alimento read(int id);

    boolean update(Alimento alimento);

    boolean delete(int id);

    List<Alimento> readAll();
    
    List<Alimento> readByFilter(String filtro);
    
    List<Alimento> readByFilterModal(String filtro);
}
