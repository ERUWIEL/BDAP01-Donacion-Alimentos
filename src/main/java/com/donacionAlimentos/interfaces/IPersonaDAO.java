package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Persona;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 * @author angel
 */
public interface IPersonaDAO {

    void create(Persona persona);

    Persona read(int id);

    void update(Persona persona);

    void delete(int id);

    List<Persona> readAll();
}
