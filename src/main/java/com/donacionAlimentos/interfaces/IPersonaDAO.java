package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Persona;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 * @author angel
 */
public interface IPersonaDAO {

    boolean create(Persona persona);

    Persona read(int id);

    boolean update(Persona persona);

    boolean delete(int id);

    List<Persona> readAll();
}
