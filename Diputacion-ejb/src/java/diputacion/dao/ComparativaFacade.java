/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Comparativa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class ComparativaFacade extends AbstractFacade<Comparativa> implements ComparativaFacadeLocal {
    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComparativaFacade() {
        super(Comparativa.class);
    }
    
}