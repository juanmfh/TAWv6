/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Administrador;
import diputacion.entity.Controlador;
import diputacion.entity.Jefe;
import diputacion.entity.Solicitud;
import diputacion.entity.Usuario;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JuanM
 */
@Stateless
public class SolicitudFacade extends AbstractFacade<Solicitud> implements SolicitudFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitudFacade() {
        super(Solicitud.class);
    }

    @Override
    public Collection<Solicitud> solicitudesPendientesTramitar(Jefe jefe) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesPendientesTramitar").setParameter("jefe", jefe);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesTramitadasDenegadas(Jefe jefe) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesTramitadasDenegadas").setParameter("jefe", jefe);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesPendientesValidar(Controlador controlador) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesPendientesValidar").setParameter("controlador", controlador);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesValidadasDenegadas(Controlador controlador) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesValidadasDenegadas").setParameter("controlador", controlador);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesPendientesEjecutar(Administrador administrador) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesPendientesEjecutar").setParameter("administrador", administrador);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesEjecutadas(Administrador administrador) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesEjecutadas").setParameter("administrador", administrador);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }

    @Override
    public Collection<Solicitud> solicitudesUsuario(Usuario usuario) {
        Query query = this.getEntityManager().createNamedQuery("Solicitud.solicitudesUsuario").setParameter("usuario", usuario);
        Collection<Solicitud> solicitudes = query.getResultList();
        return solicitudes;
    }
}
