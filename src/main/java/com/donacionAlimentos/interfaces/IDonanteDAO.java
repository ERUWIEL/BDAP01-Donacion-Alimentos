package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Donante;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IDonanteDAO {

    void create(Donante donante);

    Donante read(int id);

    void update(Donante donante);

    void delete(int id);

    List<Donante> readAll();
}
