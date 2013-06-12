/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineafija;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LineafijaFacadeLocal {

    void create(Lineafija lineafija);

    void edit(Lineafija lineafija);

    void remove(Lineafija lineafija);

    Lineafija find(Object id);

    List<Lineafija> findAll();

    List<Lineafija> findRange(int[] range);

    int count();
    
}
