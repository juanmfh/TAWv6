package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.dao.TarifamovilFacadeLocal;
import diputacion.entity.Perfil;
import diputacion.entity.Tarifamovil;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaNuevoPerfil")
@SessionScoped
public class CtrVistaNuevoPerfil implements Serializable {

    /**
     * Creates a new instance of CtrVistaNuevoPerfil
     */
    @EJB
    private PerfilFacadeLocal perfilFacade;
    @EJB
    private TarifamovilFacadeLocal tarifaFacade;
    private List<String> listaTarifas;
    private List<Tarifamovil> listaTarifasMoviles;
    private String fechaFin;
    private Date fechaFinReal;
    private String limite;
    private double limiteReal;
    private Tarifamovil tarifa;

    public CtrVistaNuevoPerfil() {
    }

    @PostConstruct
    public void init() {
        listaTarifas = new LinkedList<String>();
        listaTarifasMoviles = tarifaFacade.findAll();
        for (int i = 0; i < listaTarifasMoviles.size(); i++) {
            listaTarifas.add(listaTarifasMoviles.get(i).getNombre());
        }
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

    public Tarifamovil getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifamovil tarifa) {
        this.tarifa = tarifa;
    }

    public List<String> getListaTarifas() {
        return listaTarifas;
    }

    public void setListaTarifas(List<String> listaTarifas) {
        this.listaTarifas = listaTarifas;
    }

    public String addPerfil() {
        if (limite == null) {
            return "VistaNuevoPerfil";
        } else {
            limiteReal = Double.valueOf(limite);
            return "/jsf/control_gasto/VistaAddPerfil.jsf";
            /*
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
             */
            /*
            if (limiteReal == 0) {
                return "VistaNuevoPerfil";
            } else {
                //Perfil pf = new Perfil();
                //pf.setIdperfil(perfilFacade.maxID() + 1);
                //pf.setLimite(limiteReal);
                //perfilFacade.create(pf);

                return "/jsf/control_gasto/VistaAddPerfil.jsf";
            }
            */
        }
    }

    public String addTarifa() {
        return "VistaNuevaTarifa";
    }
}