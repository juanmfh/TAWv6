/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.gestion_terminales;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import diputacion.entity.Terminalfijo;
import diputacion.entity.Usuario;

/**
 *
 * @author Joaquin
 */
@ManagedBean(name = "ctrGestionTerminalesFijo")
@SessionScoped
public class CtrGestionTerminalesFijo {
    
   // private LineaFijaFacade lineaFijaFacade;
    private String marca, modelo, linea, fecha;
    private Date fechaAlta;
    private Collection<Terminalfijo> terminales;
    private List<String> publico;
    private boolean pub;
    private Terminalfijo terminalSeleccionado;
    private Integer idterminalFijo;
    private Collection<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private Collection<Terminalfijo> terminalesLibres;

    public CtrGestionTerminalesFijo() {
    }

    //GETTER AND SETTER
    public Collection<Terminalfijo> getTerminalesLibres() {
        return terminalesLibres;
    }

    public void setTerminalesLibres(Collection<Terminalfijo> t) {
        terminalesLibres = t;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario u) {
        usuarioSeleccionado = u;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> u) {
        usuarios = u;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String f) {
        fecha = f;
    }

    public boolean getPub() {
        return pub;
    }

    public void setPub(boolean p) {
        pub = p;
    }

    public Integer getIdterminalFijo() {
        return idterminalFijo;
    }

    public void setIdterminalFijo(Integer id) {
        idterminalFijo = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String m) {
        marca = m;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String mo) {
        modelo = mo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date f) {
        fechaAlta = f;
    }

    public Collection<Terminalfijo> getTerminales() {
        return terminales;
    }

    public void setTerminales(Collection<Terminalfijo> t) {
        terminales = t;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String l) {
        linea = l;
    }

    public List<String> getPublico() {
        return publico;
    }

    public void setPublico(List<String> p) {
        publico = p;
    }

    public Terminalfijo getTerminalSeleccionado() {
        return terminalSeleccionado;
    }

    public void setTerminalSeleccionado(Terminalfijo tf) {
        terminalSeleccionado = tf;
    }

    
    
    
    
}
