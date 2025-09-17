package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Alimento;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IAlimentoDAO {

    void create(Alimento alimento);

    Alimento read(int id);

    void update(Alimento alimento);

    void delete(int id);

    List<Alimento> readAll();
}
