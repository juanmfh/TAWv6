/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Comparativa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface ComparativaFacadeLocal {

    void create(Comparativa comparativa);

    void edit(Comparativa comparativa);

    void remove(Comparativa comparativa);

    Comparativa find(Object id);

    List<Comparativa> findAll();

    List<Comparativa> findRange(int[] range);

    int count();

    public Object maxComparativa();
    
}
