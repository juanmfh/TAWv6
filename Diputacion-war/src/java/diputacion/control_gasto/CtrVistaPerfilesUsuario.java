package diputacion.control_gasto;

import diputacion.dao.LineamovilFacade;
import diputacion.dao.PerfilFacade;
import diputacion.dao.UsuarioFacade;
import diputacion.entity.Lineamovil;
import diputacion.entity.Perfil;
import diputacion.entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Alejandro Ruiz Moyano
 */
@Named(value = "ctrVistaPerfilesUsuario")
@SessionScoped
public class CtrVistaPerfilesUsuario implements Serializable {

    /**
     * Creates a new instance of CtrVistaPerfilesUsuario
     */
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private LineamovilFacade lineaMovilFacade;
    @EJB
    private PerfilFacade perfilFacade;
    private Usuario usuario;
    private Collection<Lineamovil> coleccionMoviles;
    private LinkedList<Lineamovil> moviles;
    private LinkedList<Perfil> perfiles;
    private Integer idTarifa;

    public CtrVistaPerfilesUsuario() {
    }

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Usuario sessionUser = (Usuario) externalContext.getSessionMap().get("usuario");
        usuario = usuarioFacade.find(sessionUser.getIdusuario());
        coleccionMoviles = usuario.getLineamovilCollection();
        moviles = new LinkedList<Lineamovil>();
        perfiles = new LinkedList<Perfil>();
        for (Lineamovil l : coleccionMoviles) {
            moviles.add(l);
            perfiles.add(l.getPerfilIdperfil());
        }
    }

    public LinkedList<Lineamovil> getListaMoviles() {
        return moviles;
    }

    public void setListaMoviles(LinkedList<Lineamovil> moviles) {
        this.moviles = moviles;
    }

    public LinkedList<Perfil> getListaPerfiles() {
        return perfiles;
    }

    public void setListaPerfiles(LinkedList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }
}