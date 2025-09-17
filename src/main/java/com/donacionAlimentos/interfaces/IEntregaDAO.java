package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Entrega;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IEntregaDAO {

    void create(Entrega entrega);

    Entrega read(int id);

    void update(Entrega entrega);

    void delete(int id);

    List<Entrega> readAll();
}
