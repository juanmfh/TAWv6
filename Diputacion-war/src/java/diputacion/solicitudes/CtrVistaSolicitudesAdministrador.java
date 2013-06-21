package diputacion.solicitudes;

import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.SolicitudFacadeLocal;
import diputacion.entity.Administrador;
import diputacion.entity.Solicitud;
import diputacion.entity.Usuario;
import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author benjamin
 */
@ManagedBean(name = "ctrVistaSolicitudesAdministrador")
@RequestScoped
public class CtrVistaSolicitudesAdministrador implements Serializable {

    @EJB
    private AdministradorFacadeLocal administradorFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacade;
    //@EJB
    //private UsuarioFacade usuarioFacade;
    private Solicitud solicitudSeleccionada;
    private Usuario usuario;
    private Administrador administrador;
    private Collection<Solicitud> solicitudesPendientes;
    private Collection<Solicitud> solicitudesAD;

    /**
     * Creates a new instance of CtrVistaSolicitudesUsuario
     */
    public CtrVistaSolicitudesAdministrador() {
    }

    public Solicitud getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
        this.solicitudSeleccionada = solicitudSeleccionada;
    }

    public Collection<Solicitud> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void setSolicitudesPendientes(Collection<Solicitud> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

    public Collection<Solicitud> getSolicitudesAD() {
        return solicitudesAD;
    }

    public void setSolicitudesAD(Collection<Solicitud> solicitudesAD) {
        this.solicitudesAD = solicitudesAD;
    }

    @PostConstruct
    public void inicializacion() {

        /*
         * Obtenemos el usuario logeado desde la sesion.
         */
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        usuario = (Usuario) externalContext.getSessionMap().get("usuario");

        /*
         * Suponemos que el siguiente usuario esta logueado y es administrador
         */
        // usuario = usuarioFacade.find(9);


        /*
         * Si el usuario no existe o no es administrador se le redirige al index.jsf para 
         * que se autentifique.
         */
        if (usuario != null) {

            administrador = administradorFacade.find(usuario.getIdusuario());

            if (administrador == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../../ErrorAutorizacion.jsf");
                } catch (IOException ex) {
                    Logger.getLogger(CtrVistaSolicitudesUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../ErrorAutorizacion.jsf");
            } catch (IOException ex) {
                Logger.getLogger(CtrVistaSolicitudesUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
         * Inicializacion de las solicitudes Pendientes de Ejecutar
         */
        solicitudesPendientes = solicitudFacade.solicitudesPendientesEjecutar(administrador);

        /*
         * Inicializacion de las solicitudes Ejecutadas para mostrarlas
         * en el historial. Esto deberia de estar en el metodo ver historial, pero
         * no funciona.
         */
        solicitudesAD = solicitudFacade.solicitudesEjecutadas(administrador);
    }

    public String doVer() {
        return "VerSolicitud";
    }

    public String doVerHistorial() {
        return "HistorialSolicitudesAdministrador";
    }

    public String doEjecutar() {

        solicitudSeleccionada.setDenegada(false);
        solicitudFacade.edit(solicitudSeleccionada);
        solicitudSeleccionada = null;
        this.inicializacion();

        return "VistaSolicitudesAdministrador";
    }
}
