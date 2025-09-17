package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Aportacion;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IAportacionDAO {

    void create(Aportacion aportacion);

    Aportacion read(int id);

    void update(Aportacion aportacion);

    void delete(int id);

    List<Aportacion> readAll();
}
