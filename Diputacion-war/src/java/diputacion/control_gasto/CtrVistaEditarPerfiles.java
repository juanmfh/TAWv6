package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.dao.TarifamovilFacadeLocal;
import diputacion.entity.Perfil;
import diputacion.entity.Tarifamovil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaEditarPerfiles")
@RequestScoped
public class CtrVistaEditarPerfiles implements Serializable {

    /**
     * Creates a new instance of CtrVistaEditarPerfiles
     */
    @EJB
    private PerfilFacadeLocal perfilFacade;
    @EJB
    private TarifamovilFacadeLocal tarifaFacade;
    private List<SelectItem> listaTarifas;
    private String fechaFin;
    private Date fechaFinReal;
    private String limite;
    private double limiteReal;
    private LinkedList<Perfil> listaPerfiles;
    private Integer idPerfil;
    private Object seleccionado;
    private String nombre;
    private String establecimientoLlamada;
    private String costeSMS;
    private String costeMinuto;
    private String precioDatos;
    private boolean tarifaDatos;

    public CtrVistaEditarPerfiles() {
    }

    @PostConstruct
    public void init() {
        listaPerfiles = new LinkedList<Perfil>();
        Collection<Perfil> coleccionPerfiles = perfilFacade.findAll();
        for (Perfil pf : coleccionPerfiles) {
            listaPerfiles.add(pf);
        }
        listaTarifas = new ArrayList<SelectItem>();
        List<Tarifamovil> listaTarifasMoviles = tarifaFacade.findAll();
        for (int i = 0; i < listaTarifasMoviles.size(); i++) {
            Tarifamovil tf = listaTarifasMoviles.get(i);
            listaTarifas.add(new SelectItem(tf.getIdtarifaMovil(), tf.getNombre()));
        }
        limite = "";
        fechaFin = "";
        seleccionado = null;
        int dia = new java.util.Date().getDate();
        int mes = new java.util.Date().getMonth() + 1;
        int anyo = new java.util.Date().getYear() + 1900;
        fechaFin = "";
        fechaFin += dia + "/" + mes + "/" + anyo;
        nombre = "";
        establecimientoLlamada = "";
        costeMinuto = "";
        costeSMS = "";
        precioDatos = "0.0";
    }

    public boolean isTarifaDatos() {
        return tarifaDatos;
    }

    public void setTarifaDatos(boolean tarifaDatos) {
        this.tarifaDatos = tarifaDatos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstablecimientoLlamada() {
        return establecimientoLlamada;
    }

    public void setEstablecimientoLlamada(String establecimientoLlamada) {
        this.establecimientoLlamada = establecimientoLlamada;
    }

    public String getCosteSMS() {
        return costeSMS;
    }

    public void setCosteSMS(String costeSMS) {
        this.costeSMS = costeSMS;
    }

    public String getCosteMinuto() {
        return costeMinuto;
    }

    public void setCosteMinuto(String costeMinuto) {
        this.costeMinuto = costeMinuto;
    }

    public String getPrecioDatos() {
        return precioDatos;
    }

    public void setPrecioDatos(String precioDatos) {
        this.precioDatos = precioDatos;
    }

    public Object getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Object seleccionado) {
        this.seleccionado = seleccionado;
    }

    public List<SelectItem> getListaTarifas() {
        return listaTarifas;
    }

    public void setListaTarifas(List<SelectItem> listaTarifas) {
        this.listaTarifas = listaTarifas;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public LinkedList<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(LinkedList<Perfil> perfiles) {
        this.listaPerfiles = perfiles;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer id) {
        this.idPerfil = id;
    }

    public String nuevoPerfil() {
        return "VistaNuevoPerfil";
    }

    public String nuevaTarifa() {
        return "VistaNuevaTarifa";
    }

    public String borrarPerfil() {
        Perfil pf = perfilFacade.find(idPerfil);
        if (pf.getLineamovilCollection().isEmpty()) {
            perfilFacade.remove(pf);
            init();
        }
        return "VistaEditarPerfiles";
    }

    public String addPerfil() {
        if (fechaFin.length() > 0) {
            StringTokenizer tokens = new StringTokenizer(fechaFin, "/");
            int[] datos = new int[3];
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                datos[i] = Integer.parseInt(str);
                System.out.println(datos[i]);
                i++;
            }
            fechaFinReal = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
        }
        if ("".equals(limite) || "".equals(fechaFin)) {
            return "VistaNuevoPerfil";
        } else {
            limiteReal = Double.valueOf(limite);
            if (limiteReal == 0) {
                return "VistaNuevoPerfil";
            }
            int tarifa = Integer.valueOf((String) seleccionado);
            Tarifamovil tf = tarifaFacade.find(tarifa);
            Perfil pf = new Perfil();
            pf.setIdperfil(perfilFacade.maxID() + 1);
            pf.setLimite(limiteReal);
            pf.setFechaFin(fechaFinReal);
            pf.setTarifaMovilidtarifaMovil(tf);
            pf.setLineamovilCollection(null);
            perfilFacade.create(pf);
            tf.getPerfilCollection().add(pf);
            tarifaFacade.edit(tf);
            this.init();
            return "VistaEditarPerfiles";
        }
    }

    public String addTarifa() {
        if (nombre.equals("") || establecimientoLlamada.equals("") || costeMinuto.equals("") || costeSMS.equals("") || precioDatos.equals("")) {
            return "VistaNuevaTarifa";
        } else {
            Tarifamovil tf = new Tarifamovil();
            tf.setIdtarifaMovil(tarifaFacade.maxID() + 1);
            tf.setNombre(nombre);
            tf.setCosteEstablecimiento(Double.valueOf(establecimientoLlamada));
            tf.setCosteMinuto(Double.valueOf(costeMinuto));
            tf.setCosteSMS(Double.valueOf(costeSMS));
            tf.setDatos(tarifaDatos);
            tf.setPrecioDatos(Double.valueOf(precioDatos));
            tarifaFacade.create(tf);
            this.init();
            return "VistaNuevoPerfil";
        }
    }

    public String borrarTarifa() {
        int tarifa = Integer.valueOf((String) seleccionado);
        Tarifamovil tf = tarifaFacade.find(tarifa);
        Collection<Perfil> coleccionPerfiles = tf.getPerfilCollection();
        for (Perfil pf : coleccionPerfiles) {
            pf.setTarifaMovilidtarifaMovil(null);
            perfilFacade.edit(pf);
        }
        tarifaFacade.remove(tf);
        this.init();
        return "VistaNuevoPerfil";
    }
}