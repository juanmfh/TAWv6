package diputacion.solicitudes;

import diputacion.dao.JefeFacadeLocal;
import diputacion.dao.SolicitudFacadeLocal;
import diputacion.entity.Jefe;
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
@ManagedBean(name = "ctrVistaSolicitudesJefe")
@RequestScoped
public class CtrVistaSolicitudesJefe implements Serializable {

    @EJB
    private JefeFacadeLocal jefeFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacade;
    //@EJB
    //private UsuarioFacadeLocal usuarioFacade;
    private Solicitud solicitudSeleccionada;
    private Usuario usuario;
    private Jefe jefe;
    private Collection<Solicitud> solicitudesPendientes;
    private Collection<Solicitud> solicitudesAD;

    /**
     * Creates a new instance of CtrVistaSolicitudesUsuario
     */
    public CtrVistaSolicitudesJefe() {
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
         * Suponemos que el siguiente usuario esta logueado y es jefe
         */
        //usuario = usuarioFacade.find(1);


        /*
         * Si el usuario no existe o no es jefe se le redirige al index.jsf para 
         * que se autentifique.
         */
        if (usuario != null) {

            jefe = jefeFacade.find(usuario.getIdusuario());

            if (jefe == null) {
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
         * Inicializacion de las solicitudes Pendientes de Tramitar
         */
        solicitudesPendientes = solicitudFacade.solicitudesPendientesTramitar(jefe);


        /*
         * Inicializacion de las solicitudes Tramitadas/Denegadas para mostrarlas
         * en el historial. Esto deberia de estar en el metodo ver historial, para
         * inicializarse cuando se le invocara, pero no funciona.
         */
        solicitudesAD = solicitudFacade.solicitudesTramitadasDenegadas(jefe);


    }

    public String doVer() {

        return "VerSolicitud";
    }

    public String doVerHistorial() {

        return "HistorialSolicitudesJefe";
    }

    public String doTramitar() {

        solicitudSeleccionada.setControladorUsuarioIdusuario(jefe.getControladorUsuarioIdusuario());
        solicitudFacade.edit(solicitudSeleccionada);
        solicitudSeleccionada = null;
        this.inicializacion();

        return "VistaSolicitudesJefe";
    }

    public String doDenegar() {

        solicitudSeleccionada.setDenegada(true);
        solicitudFacade.edit(solicitudSeleccionada);
        solicitudSeleccionada = null;
        this.inicializacion();

        return "VistaSolicitudesJefe";
    }
}
