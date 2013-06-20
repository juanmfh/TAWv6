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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaEditarPerfiles")
@SessionScoped
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
        return "VistaNuevaTarifa";
    }
}