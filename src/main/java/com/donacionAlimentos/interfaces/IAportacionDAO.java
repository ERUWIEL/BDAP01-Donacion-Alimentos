package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Aportacion;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IAportacionDAO {

    boolean create(Aportacion aportacion);

    Aportacion read(int id);

    boolean update(Aportacion aportacion);

    boolean delete(int id);

    List<Aportacion> readAll();
}
