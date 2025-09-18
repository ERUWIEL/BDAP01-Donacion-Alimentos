package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Contenido;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IContenidoDAO {

    boolean create(Contenido contenido);

    Contenido read(int id);

    boolean update(Contenido contenido);

    boolean delete(int id);

    List<Contenido> readAll();
}
