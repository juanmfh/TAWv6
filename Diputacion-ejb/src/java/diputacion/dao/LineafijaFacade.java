/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineafija;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class LineafijaFacade extends AbstractFacade<Lineafija> implements LineafijaFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LineafijaFacade() {
        super(Lineafija.class);
    }

    //METODOS JOAQUIN GARCIA
    @Override
    public Lineafija buscarNumero(int numero) {
        
        List<Lineafija> res = null;
        
        
        javax.persistence.Query q = getEntityManager().createNamedQuery("Lineafija.findByNumeroLinea");
        q.setParameter("numeroLinea", numero);

        res = (List<Lineafija>) q.getResultList();

        if (res != null && res.size() > 0) {
            return res.get(0);
        } else {
            return null;
        }
    }
}
