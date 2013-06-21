/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.usuarios;

import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Usuario;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Fjsermu
 */
@ManagedBean(name = "ctrVistaUsuarios")
@SessionScoped
public class CtrVistaUsuarios implements Serializable {

    @EJB
    UsuarioFacadeLocal usuarioFacade;
    private String nombre, apellido1, apellido2, dni, municipio, nick, password;
    private Collection<Usuario> usuarios;
    private Usuario usuario;

    /**
     * Creates a new instance of CtrVistaUsuarios
     */
    public CtrVistaUsuarios() {
    }

    @PostConstruct
    public void init() {
        //ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //Usuario sessionUser = (Usuario) externalContext.getSessionMap().get("usuario");
        //usuario = usuarioFacade.find(sessionUser.getIdusuario());

        this.usuarios = this.usuarioFacade.listaUsuarios();



    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Collection<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario u) {
        usuario = u;
    }

    public String listadoUsuarios() {
        this.usuarios = this.usuarioFacade.listaUsuarios();

        return "/jsf/usuarios/VistaUsuarios.jsf";
    }

    public void crearUsuario() {

        System.out.println("metodo ejecutado");

        Usuario u = new Usuario();
        u.setNombre(this.nombre);
        u.setDni(this.dni);
        u.setPassword(this.password);
        u.setApellido1(this.apellido1);
        u.setApellido2(this.apellido2);
        u.setMunicipio(this.municipio);
        u.setNick(this.nick);

        this.usuarioFacade.create(u);

        this.listadoUsuarios();
    }
}
