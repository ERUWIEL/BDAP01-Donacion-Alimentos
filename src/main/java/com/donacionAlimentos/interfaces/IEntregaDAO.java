package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Entrega;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IEntregaDAO {

    boolean create(Entrega entrega);

    Entrega read(int id);

    boolean update(Entrega entrega);

    boolean delete(int id);

    List<Entrega> readAll();
}
