package diputacion.solicitudes;

import diputacion.dao.ControladorFacadeLocal;
import diputacion.dao.SolicitudFacadeLocal;
import diputacion.entity.Controlador;
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
@ManagedBean(name = "ctrVistaSolicitudesControlador")
@RequestScoped
public class CtrVistaSolicitudesControlador implements Serializable {

    @EJB
    private ControladorFacadeLocal controladorFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacade;
    //@EJB
    //private UsuarioFacadeLocal usuarioFacade;
    private Solicitud solicitudSeleccionada;
    private Usuario usuario;
    private Controlador controlador;
    private Collection<Solicitud> solicitudesPendientes;
    private Collection<Solicitud> solicitudesAD;

    /**
     * Creates a new instance of CtrVistaSolicitudesUsuario
     */
    public CtrVistaSolicitudesControlador() {
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
         * Suponemos que el siguiente usuario esta logueado y es Controlador
         */
        //usuario = usuarioFacade.find(12);

        /*
         * Si el usuario no existe o no es controaldor se le redirige al index.jsf 
         * para que se autentifique.
         */
        if (usuario != null) {

            controlador = controladorFacade.find(usuario.getIdusuario());

            if (controlador == null) {
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
         * Inicializacion de las solicitudes Pendientes de Validar
         */
        solicitudesPendientes = solicitudFacade.solicitudesPendientesValidar(controlador);

        /*
         * Inicializacion de las solicitudes Validadas/Denegadas para mostrarlas
         * en el historial. Esto deberia de estar en el metodo ver historial, pero
         * no funciona.
         */
        solicitudesAD = solicitudFacade.solicitudesValidadasDenegadas(controlador);

    }

    public String doVer() {
        return "VerSolicitud";
    }

    public String doVerHistorial() {

        solicitudesAD = solicitudFacade.solicitudesValidadasDenegadas(controlador);
        return "HistorialSolicitudesControlador";
    }

    public String doValidar() {

        solicitudSeleccionada.setAdministradorUsuarioIdusuario(controlador.getAdministradorUsuarioIdusuario());
        solicitudFacade.edit(solicitudSeleccionada);
        solicitudSeleccionada = null;
        this.inicializacion();

        return "VistaSolicitudesControlador";
    }

    public String doDenegar() {
        solicitudSeleccionada.setDenegada(true);
        solicitudFacade.edit(solicitudSeleccionada);
        solicitudSeleccionada = null;
        this.inicializacion();

        return "VistaSolicitudesControlador";
    }
}
