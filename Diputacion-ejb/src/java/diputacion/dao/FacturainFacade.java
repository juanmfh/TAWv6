/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Facturain;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class FacturainFacade extends AbstractFacade<Facturain> implements FacturainFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturainFacade() {
        super(Facturain.class);
    }

    public Object maxFacturaIn() {
        return em.createNamedQuery("Facturain.ultimoId").getSingleResult();
    }
}