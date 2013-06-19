package diputacion.control_gasto;

import java.io.Serializable;
import javax.annotation.PostConstruct;
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
    

    public CtrVistaNuevoPerfil() {
    }

    @PostConstruct
    public void init() {
        
    }
}