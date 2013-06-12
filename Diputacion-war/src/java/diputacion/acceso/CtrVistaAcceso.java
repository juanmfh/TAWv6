/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.acceso;

import diputacion.dao.UsuarioFacade;
import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Usuario;
import java.io.IOException;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fjsermu
 */
@ManagedBean(name = "ctrVistaAcceso")
@SessionScoped
public class CtrVistaAcceso implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    private Usuario usuario;
    private String DNI;
    private String password;

    /**
     * Creates a new instance of CtrVistaAcceso
     */
    public CtrVistaAcceso() {
    }

    public String getDNI() {
        return this.DNI;
    }

    public String getpassword() {
        return this.password;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.usuario = Usuario;
    }

    public String usuarioPassword() throws IOException {
        usuario = this.usuarioFacade.findByDNIPassword(this.DNI, this.password);

        if (usuario != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", this.usuario);
            
            return "index-logued";
        }

        return "";
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
    }
}
