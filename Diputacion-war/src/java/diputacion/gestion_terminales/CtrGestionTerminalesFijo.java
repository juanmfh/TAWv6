/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.gestion_terminales;

import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.LineafijaFacadeLocal;
import diputacion.dao.TerminalfijoFacadeLocal;
import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Administrador;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import diputacion.entity.Terminalfijo;
import diputacion.entity.Usuario;
import java.text.ParseException;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import diputacion.entity.Lineafija;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Joaquin
 */
@ManagedBean(name = "ctrGestionTerminalesFijo")
@SessionScoped
public class CtrGestionTerminalesFijo implements Serializable {

    @EJB
    private LineafijaFacadeLocal lineafijaFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private TerminalfijoFacadeLocal terminalfijoFacade;
    @EJB
    private AdministradorFacadeLocal administradorFacade;
    //VARIABLES
    private String marca, modelo, linea, fecha;
    private Date fechaAlta;
    private Collection<Terminalfijo> terminales;
    private List<String> publico;
    private boolean pub;
    private Terminalfijo terminalSeleccionado;
    private Integer idterminalFijo;
    private Collection<Usuario> usuarios;
    private Usuario usuarioSeleccionado, usuario;
    private Collection<Terminalfijo> terminalesLibres;
    private Administrador administrador;
    private boolean admin = true;

    public CtrGestionTerminalesFijo() {
    }

    //GETTER AND SETTER
    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador a) {
        administrador = a;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario u) {
        usuario = u;
    }

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

    //METODOS-------------------------------------------------------------------
    @PostConstruct
    public void inicializacion() {

        terminales = terminalfijoFacade.findAll();
        //OBTENEMOS LA FECHO DE SISTEMA
        int dia = new java.util.Date().getDate();
        int mes = new java.util.Date().getMonth() + 1;
        int ano = new java.util.Date().getYear() + 1900;
        //PONEMOS LA FECHA EN BLANCO
        fecha = "";
        fecha += dia + "/" + mes + "/" + ano;
        marca = "";
        modelo = "";
        linea = "";
        //RECOGEMOS LOS USUARIOS
        usuarios = usuarioFacade.findAll();
    }

    //METODO QUE COMPRUEBA SI SOMOS ADMINISTRADOR
    public boolean esAdministrador() {
        boolean res = true;

        // Obtenemos el usuario logeado desde la sesion
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        usuario = (Usuario) externalContext.getSessionMap().get("usuario");

        if (usuario != null) {
            administrador = administradorFacade.find(usuario.getIdusuario());

            if (administrador == null) {
                res = false;
            }
        } else {
            res = true;
        }

        return res;
    }

    public String formularioInsertar() throws IOException {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();

            return "jsf/gestion_terminales/FormularioInsertarFijo.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    public String pagListadoTerminalFijo() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();

            return "jsf/gestion_terminales/ListadoTerminalFijo.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    public String pagListadoUsuarios() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();

            return "jsf/gestion_terminales/ListadoUsuarios.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    public String insertarTerminalFijo() throws ParseException, IOException {

        //CREAMOS EL OBJETO TERMINAL FIJO
        Terminalfijo tfnuevo;
        Lineafija lf;
        tfnuevo = new Terminalfijo();
        tfnuevo.setMarca(marca);
        tfnuevo.setModelo(modelo);


        //COMPROBAMOS SI EL CAMPO PUBLICO ESTA MARCADO
        if (!publico.isEmpty()) {
            pub = true;
        }

        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA
        if (linea.length() > 0) {
            lf = new Lineafija();
            
            //COMPRABAMOS QUE LA LINEA RECIBIDA TIENE LOS DATOS CORRECTOS
            if (esNumero(linea)) {
                
                //PASEAMOS LA FECHA
                if (fecha.length() > 0) {


                    StringTokenizer tokens = new StringTokenizer(fecha, "/");;
                    int[] datos = new int[3];
                    int i = 0;
                    while (tokens.hasMoreTokens()) {
                        String str = tokens.nextToken();
                        datos[i] = Integer.parseInt(str);
                        i++;
                    }
                    fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                    lf.setFechaAlta(fechaAlta);
                }

                lf.setNumeroLinea(Integer.parseInt(linea));
                lf.setPublico(pub);
                lineafijaFacade.create(lf);
                tfnuevo.setLineaFijaidlineaFija(lf);
                pub = false;
            } else {
                return "FormularioInsertarFijoErrorLinea";
            }
        }


        //INSERTAMOS EN LA BD
        terminalfijoFacade.create(tfnuevo);
        this.inicializacion();
        return "ListadoTerminalFijo";


    }

    public void borrarTerminalFijo() {

        terminalfijoFacade.remove(terminalSeleccionado);
        this.inicializacion();


    }

    public String modificarTerminalFijo() {

        marca = terminalSeleccionado.getMarca();
        modelo = terminalSeleccionado.getModelo();
        linea = "";
        if (terminalSeleccionado.getLineaFijaidlineaFija() != null) {
            linea += terminalSeleccionado.getLineaFijaidlineaFija().getNumeroLinea();
        }
        fecha = "";
        if (terminalSeleccionado.getLineaFijaidlineaFija() != null && terminalSeleccionado.getLineaFijaidlineaFija().getFechaAlta() != null) {

            int dia = terminalSeleccionado.getLineaFijaidlineaFija().getFechaAlta().getDate();
            int mes = terminalSeleccionado.getLineaFijaidlineaFija().getFechaAlta().getMonth() + 1;
            int ano = terminalSeleccionado.getLineaFijaidlineaFija().getFechaAlta().getYear() + 1900;
            fecha += dia + "/" + mes + "/" + ano;
        }


        return "FormularioModificarFijo";

    }

    public String modificarTerminalFijoDatos() {
        Lineafija lf = null;
        terminalSeleccionado.setMarca(marca);
        terminalSeleccionado.setModelo(modelo);
        
        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA    
        if (!publico.isEmpty()) {
            pub = true;
        }

        //COMPROBAMOS SI LA LINEA EXISTE O ES UNA NUEVA.
        String aux;
        aux = linea;
        if(aux.length()==0)
        {
            aux="0";
        }
        lf=lineafijaFacade.buscarNumero(Integer.parseInt(aux));
        
        if (linea.length() > 0 && lf!=null) {
            //PASEAMOS LA FECHA
            if (fecha.length() > 0) {
                StringTokenizer tokens = new StringTokenizer(fecha, "/");;
                int[] datos = new int[3];
                int i = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[i] = Integer.parseInt(str);
                    i++;
                }
                fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                lf.setFechaAlta(fechaAlta);
            }
            
            

            lf.setNumeroLinea(Integer.parseInt(linea));
            lf.setPublico(pub);
            lineafijaFacade.edit(lf);
            terminalSeleccionado.setLineaFijaidlineaFija(lf);
            pub = false;
        }
        else
        {
            lf = new Lineafija();

            //PASEAMOS LA FECHA
            if (fecha.length() > 0) {
                StringTokenizer tokens = new StringTokenizer(fecha, "/");;
                int[] datos = new int[3];
                int i = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[i] = Integer.parseInt(str);
                    i++;
                }
                fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                lf.setFechaAlta(fechaAlta);
            }
            
            

            lf.setNumeroLinea(Integer.parseInt(linea));
            lf.setPublico(pub);
            lineafijaFacade.create(lf);
            terminalSeleccionado.setLineaFijaidlineaFija(lf);
            pub = false;
        }

        //MODIFICAMOS EN LA BD
        terminalfijoFacade.edit(terminalSeleccionado);

        this.inicializacion();
        return "ListadoTerminalFijo";

    }

    public void volver() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../index-logued.jsf");
    }

    public void volverListado() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../index-logued.jsf");
    }

    public String terminalesLibres() {

        terminalesLibres = new ArrayList<Terminalfijo>();

        terminales = terminalfijoFacade.findAll();
        for (Terminalfijo t : terminales) {
            if (t.getLineaFijaidlineaFija() != null && t.getLineaFijaidlineaFija().getUsuarioIdusuario() == null) {
                terminalesLibres.add(t);
            }
        }

        return "TerminalesLibres";
    }

    public void asignarTerminal() {

        Terminalfijo tf = terminalfijoFacade.find(terminalSeleccionado.getIdterminalFijo());
        Usuario user = usuarioFacade.find(usuarioSeleccionado.getIdusuario());
        Lineafija lf = lineafijaFacade.find(tf.getLineaFijaidlineaFija().getIdlineaFija());
        lf.setUsuarioIdusuario(usuarioSeleccionado);

        lineafijaFacade.edit(lf);
        this.terminalesLibres();
    }

    //FUNCION AUXILIAR PARA SABER SI EL STRING ES UN NUMERO
    private boolean esNumero(String numero) {
        boolean res = true;
        try {
            Integer.parseInt(numero);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}
