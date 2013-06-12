/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Sms;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface SmsFacadeLocal {

    void create(Sms sms);

    void edit(Sms sms);

    void remove(Sms sms);

    Sms find(Object id);

    List<Sms> findAll();

    List<Sms> findRange(int[] range);

    int count();
    
}
