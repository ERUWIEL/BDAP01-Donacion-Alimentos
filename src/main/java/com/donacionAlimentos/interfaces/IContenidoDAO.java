package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Contenido;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IContenidoDAO {

    void create(Contenido contenido);

    Contenido read(int id);

    void update(Contenido contenido);

    void delete(int id);

    List<Contenido> readAll();
}
