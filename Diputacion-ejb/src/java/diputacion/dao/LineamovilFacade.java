/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineamovil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class LineamovilFacade extends AbstractFacade<Lineamovil> implements LineamovilFacadeLocal {
    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LineamovilFacade() {
        super(Lineamovil.class);
    }
    
     //METODOS JOAQUIN GARCIA
    public int maxID()
    {
       int res;
        if(count()==0)
        {
            res=0;
        }
        else
        {
           List<Lineamovil> lista;
           lista=(List<Lineamovil>)em.createQuery("SELECT l FROM Lineamovil l").getResultList();
           res=lista.size();
         //res = (Integer) em.createNamedQuery("Terminalmovil.maximoID").getSingleResult();
        }
        
        return res;
    }

    
}
