/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public List<Usuario> findNotGrupoRescate(){
        return (List<Usuario>)em.createQuery("SELECT u FROM Usuario u WHERE u.grupoRescateidgrupoRescate IS NULL").getResultList();
    }
    
    @Override
    public Usuario findByDNIPassword(String dni, String password) {
        javax.persistence.Query q = getEntityManager().createNamedQuery("Usuario.findByDNIPassword");
        q.setParameter("dni", dni);
        q.setParameter("password", password);

        return (Usuario) q.getSingleResult();
    }
    
    public List<Usuario> listaUsuarios() {
      return (List<Usuario>) em.createQuery("SELECT u FROM Usuario u").getResultList();
    }
    
}
