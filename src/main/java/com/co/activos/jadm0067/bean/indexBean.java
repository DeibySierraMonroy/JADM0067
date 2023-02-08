/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.bean;

import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.MesaContratoRepo;
import static com.co.activos.jadm0067.Utilities.SessionUtils.getParameterValue;
import com.co.activos.jadm0067.Utilities.UsuarioSesionController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author desierra
 */
@Named
@ViewScoped
public class indexBean implements Serializable {

    private String sesionActiva;
    private PrimeFaces current;
    private UsuarioSesionController usuarioSesionController;

    @Inject
    MesaContratoRepo contratoRepo;

    @PostConstruct
    public void init() {
        current = PrimeFaces.current();
        current.executeScript("PF('infImportancia').show();");
        usuarioSesionController = new UsuarioSesionController();
        sesionActiva = (String) getParameterValue("USS_ID_SESSION");
        usuarioSesionController.setSesion(sesionActiva);
        usuarioSesionController.buscarUsuarioSesion();
        informacionDelAnalista();

      
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("informacionUsuario", usuarioSesionController);
    }

    public String informacionDelAnalista() {
        String nombreInformacionAnalista = "";
 
        try {
            DatabaseResultDto databaseResultDto
                    = contratoRepo.consultarInformacionAnalista(usuarioSesionController.getUsuarioSesion().getTdcTdEpl(),
                            usuarioSesionController.getUsuarioSesion().getEplNd(), "NOMBRE");
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                nombreInformacionAnalista = databaseResultDto.getMessage();
                return nombreInformacionAnalista;
            }

        } catch (Exception e) {
            System.out.println("Error al Consultar la Informacion del Analista" + e.getMessage());
        }
        return nombreInformacionAnalista;

    }

    public String getSesionActiva() {
        return sesionActiva;
    }

    public void setSesionActiva(String sesionActiva) {
        this.sesionActiva = sesionActiva;
    }

    public UsuarioSesionController getUsuarioSesionController() {
        return usuarioSesionController;
    }

    public void setUsuarioSesionController(UsuarioSesionController usuarioSesionController) {
        this.usuarioSesionController = usuarioSesionController;
    }

}
