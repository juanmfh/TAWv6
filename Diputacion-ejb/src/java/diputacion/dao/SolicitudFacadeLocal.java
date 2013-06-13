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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface SolicitudFacadeLocal {

    void create(Solicitud solicitud);

    void edit(Solicitud solicitud);

    void remove(Solicitud solicitud);

    Solicitud find(Object id);

    List<Solicitud> findAll();

    List<Solicitud> findRange(int[] range);

    int count();

    public Collection<Solicitud> solicitudesPendientesTramitar(Jefe jefe);

    public Collection<Solicitud> solicitudesTramitadasDenegadas(Jefe jefe);

    public Collection<Solicitud> solicitudesPendientesValidar(Controlador controlador);

    public Collection<Solicitud> solicitudesValidadasDenegadas(Controlador controlador);

    public Collection<Solicitud> solicitudesPendientesEjecutar(Administrador administrador);

    public Collection<Solicitud> solicitudesEjecutadas(Administrador administrador);

    public Collection<Solicitud> solicitudesUsuario(Usuario usuario);
    
}
