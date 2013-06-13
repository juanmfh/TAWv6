package diputacion.solicitudes;

import diputacion.entity.Solicitud;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author benjamin
 */
@ManagedBean(name = "ctrVerSolicitud")
@RequestScoped
public class CtrVerSolicitud implements Serializable {

    private Solicitud solicitudSeleccionada;

    public CtrVerSolicitud() {
    }

    public Solicitud getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(Solicitud solicitudSeleccionada) {

        this.solicitudSeleccionada = solicitudSeleccionada;
    }
}
