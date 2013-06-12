/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineafacturaout;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LineafacturaoutFacadeLocal {

    void create(Lineafacturaout lineafacturaout);

    void edit(Lineafacturaout lineafacturaout);

    void remove(Lineafacturaout lineafacturaout);

    Lineafacturaout find(Object id);

    List<Lineafacturaout> findAll();

    List<Lineafacturaout> findRange(int[] range);

    int count();
    
}
