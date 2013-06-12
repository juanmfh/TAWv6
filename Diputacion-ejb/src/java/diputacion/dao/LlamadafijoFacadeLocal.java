/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Llamadafijo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LlamadafijoFacadeLocal {

    void create(Llamadafijo llamadafijo);

    void edit(Llamadafijo llamadafijo);

    void remove(Llamadafijo llamadafijo);

    Llamadafijo find(Object id);

    List<Llamadafijo> findAll();

    List<Llamadafijo> findRange(int[] range);

    int count();
    
}
