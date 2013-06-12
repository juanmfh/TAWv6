/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Terminalfijo;
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
    
}
