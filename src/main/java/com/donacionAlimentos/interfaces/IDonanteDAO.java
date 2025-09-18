package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Donante;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IDonanteDAO {

    boolean create(Donante donante);

    Donante read(int id);

    boolean update(Donante donante);

    boolean delete(int id);

    List<Donante> readAll();
}
