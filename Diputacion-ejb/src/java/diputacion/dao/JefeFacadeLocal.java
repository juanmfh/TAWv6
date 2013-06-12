/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Jefe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface JefeFacadeLocal {

    void create(Jefe jefe);

    void edit(Jefe jefe);

    void remove(Jefe jefe);

    Jefe find(Object id);

    List<Jefe> findAll();

    List<Jefe> findRange(int[] range);

    int count();
    
}
