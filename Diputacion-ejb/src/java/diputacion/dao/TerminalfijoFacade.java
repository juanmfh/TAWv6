/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Terminalfijo;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class TerminalfijoFacade extends AbstractFacade<Terminalfijo> implements TerminalfijoFacadeLocal {
    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerminalfijoFacade() {
        super(Terminalfijo.class);
    }
    
    //METODOS JOAQUIN GARCIA
    @Override
    public Collection<Terminalfijo> terminalesOrdenadas() {

        Collection<Terminalfijo> res;
        res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM Terminalfijo t ORDER BY t.lineaFijaidlineaFija.usuarioIdusuario.municipio").getResultList();
        return res;
    }

    @Override
    public Collection<Terminalfijo> terminalesfiltradas(String municipio) {

        Collection<Terminalfijo> res;
        res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM Terminalfijo t WHERE t.lineaFijaidlineaFija.usuarioIdusuario.municipio LIKE :municipio").setParameter("municipio", municipio).getResultList();
        return res;
    }

    @Override
    public Collection<Terminalfijo> terminalesfiltradasNumero(int numero) {

        Collection<Terminalfijo> res;
        res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM Terminalfijo t WHERE t.lineaFijaidlineaFija.numeroLinea = :numero ").setParameter("numero", numero).getResultList();
        return res;
    }

    @Override
    public Collection<Terminalfijo> terminalesfiltradasNombre(String nombre, String apellido1) {

        Collection<Terminalfijo> res;

        if (apellido1.length() == 0) {
            res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM Terminalfijo t WHERE t.lineaFijaidlineaFija.usuarioIdusuario.nombre LIKE :nombre ").setParameter("nombre", nombre).getResultList();
        }
        else if(nombre.length()==0)
        {
            res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM TerminalFijo t WHERE t.lineaFijaidlineaFija.usuarioIdusuario.apellido1 LIKE :apellido1 ").setParameter("apellido1", apellido1).getResultList();
        }
        else
        {
            res = (Collection<Terminalfijo>) em.createQuery("SELECT t FROM TerminalFijo t WHERE t.lineaFijaidlineaFija.usuarioIdusuario.nombre LIKE :nombre AND t.lineaFijaidlineaFija.usuarioIdusuario.apellido1 LIKE :apellido1").setParameter("apellido1", apellido1).setParameter("nombre", nombre).getResultList();
        }
        return res;
    }
}

    

