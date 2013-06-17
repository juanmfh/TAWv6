/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Terminalfijo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface TerminalfijoFacadeLocal {

    void create(Terminalfijo terminalfijo);

    void edit(Terminalfijo terminalfijo);

    void remove(Terminalfijo terminalfijo);

    Terminalfijo find(Object id);

    List<Terminalfijo> findAll();

    List<Terminalfijo> findRange(int[] range);

    int count();

    Collection<Terminalfijo> terminalesOrdenadas();

    Collection<Terminalfijo> terminalesfiltradas(String municipio);

   Collection<Terminalfijo> terminalesfiltradasNumero(int numero);

    Collection<Terminalfijo> terminalesfiltradasNombre(String nombre, String apellido1);
}
