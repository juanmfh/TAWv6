package diputacion.solicitudes;

import diputacion.dao.SolicitudFacadeLocal;
import diputacion.entity.Solicitud;
import diputacion.entity.Usuario;
import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

/**
 *
 * @author benjamin
 */
@ManagedBean(name = "ctrVistaSolicitudesUsuario")
@RequestScoped
public class CtrVistaSolicitudesUsuario implements Serializable {

    @EJB
    private SolicitudFacadeLocal solicitudFacade;
    //@EJB
    //private UsuarioFacadeLocal usuarioFacade;
    private Collection<Solicitud> solicitudes;
    private Solicitud solicitudSeleccionada;
    private Usuario usuario;
    private String tipo;
    @Size(min = 20, max = 500)
    private String texto;
    private Collection<String> tiposSolicitud;

    /**
     * Creates a new instance of CtrVistaSolicitudesUsuario
     */
    public CtrVistaSolicitudesUsuario() {
    }

    public Collection<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(Collection<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Solicitud getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {
        this.solicitudSeleccionada = solicitudSeleccionada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Collection<String> getTiposSolicitud() {
        return tiposSolicitud;
    }

    public void setTiposSolicitud(Collection<String> tiposSolicitud) {
        this.tiposSolicitud = tiposSolicitud;
    }

    @PostConstruct
    public void inicializacion() {

        /*
         * Obtenemos el usuario logeado desde la sesion.
         */
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        usuario = (Usuario) externalContext.getSessionMap().get("usuario");

        /*
         * Suponemos que el siguiente usuario esta logueado
         */
        //usuario = usuarioFacade.find(10);


        /*
         * Si el usuario no existe se le redirige al index.jsf para 
         * que se autentifique.
         */
        if (usuario == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../ErrorAutorizacion.jsf");
            } catch (IOException ex) {
                Logger.getLogger(CtrVistaSolicitudesUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
         * Precarga de solicitudes
         */
        solicitudes = solicitudFacade.solicitudesUsuario(usuario);

        /*
         * Tipo de solicitudes para crear una nueva
         */
        tiposSolicitud = new LinkedList<String>();

        tiposSolicitud.add("CAMBIO DE PERFIL");
        tiposSolicitud.add("CAMBIO DE TARIFA");
        tiposSolicitud.add("CREAR GRUPO RESCATE");
        tiposSolicitud.add("AÑADIRME A GRUPO DE RESCATE");
        tiposSolicitud.add("ELIMINARME DE GRUPO DE RESCATE");
        tiposSolicitud.add("ELIMINAR GRUPO DE RESCATE");
        tiposSolicitud.add("PUBLICAR TELEFONO EN DIRECTORIO");
        tiposSolicitud.add("OCULTAR TELEFONO EN DIRECTORIO");


    }

    public String doEliminar() {

        solicitudFacade.remove(solicitudSeleccionada);
        this.inicializacion();

        return "VistaSolicitudesUsuario";

    }

    public String doVer() {
        return "VerSolicitud";
    }

    public String doNuevaSolicitud() {

        Solicitud solicitud = new Solicitud();
        solicitud.setTipo(this.tipo);
        solicitud.setTexto(this.texto);
        solicitud.setFecha(new Date());
        solicitud.setUsuarioIdusuario(usuario);
        solicitud.setJefeUsuarioIdusuario(usuario.getJefeUsuarioIdusuario());


        solicitudFacade.create(solicitud);

        this.inicializacion();

        return "VistaSolicitudesUsuario";
    }
}
