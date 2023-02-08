/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.bean;

import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.AcuerdoPorAgrupacion;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.AgrupacionAtributo;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.AgrupacionEmpresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ContactoSucursal;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.Empresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.EmpresaPrincipal;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.NotaPorAgrupacionEmpresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ObservacionesGenerales;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ResponsableEmpresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ResponsableInterno;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.SucursalEmpresa;
import com.co.activos.jadm0067.Entities.Causales;
import com.co.activos.jadm0067.Entities.CedulaPeritaje;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import static com.co.activos.jadm0067.Utilities.SessionUtils.getParameterValue;
import com.co.activos.jadm0067.Repositories.MesaContratoRepo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.charts.line.LineChartModel;

/**
 *
 * @author desierra
 */
@Named
@ViewScoped
public class acuerdoEmpresaBean implements Serializable {

    /*Declaracion de atributos*/
    private String libConsecutivo;
    private String usuario;
    private String ipUsuario;
    private String documentUrl;
    private Integer totalCandidatos;
    private boolean opcionesNoAprobado = false;
    private String observacionFinal;

    /*Declaracion de objetos*/
    private CedulaPeritaje cedulaPeritaje;
    private AgrupacionEmpresa agrupacion;
    private InformacionPeritaje informacionAsociada;
    private Empresa empresa;
    private AcuerdoPorAgrupacion acuerdoPorAgrupacion;
    private LibroIngresoDto libroIngreso;
    private LibroIngresoDocumentoDto documentoSelect;
    private Causales causa;

    /*Declaracion de listas*/
    private List<CedulaPeritaje> cedulasPeritaje;
    private List<ResponsableInterno> responsableInternos;
    private List<AgrupacionEmpresa> agrupacionEmpresas;
    private List<ObservacionesGenerales> observacionesGeneraleses;
    private List<ResponsableEmpresa> responsableEmpresas;
    private List<AcuerdoPorAgrupacion> acuerdoPorAgrupacions;
    private List<SucursalEmpresa> sucursal;
    private List<ContactoSucursal> contactoSucur;
    private List<EmpresaPrincipal> empresaPrincipals;
    private List<AgrupacionAtributo> agrupacionAtributos;
    private List<NotaPorAgrupacionEmpresa> agrupacionEmpresas1;
    private List<LibroIngresoDocumentoDto> documentos;
    private List<Causales> causalesList;

    /*Declaracion de utilitarios*/
    private static HttpSession sesion;
    private HashMap permisoVista;
    private LineChartModel lineModel;
    private List<LocalDate> range;
    private PrimeFaces current;
    
    
    
    

    @Inject
    private MesaContratoRepo contratoRepo;

    @PostConstruct
    public void init() {     
        informacionAsociada = (InformacionPeritaje) getParameterValue("informacionAsociadaBean");
        
        cargarAcuerdoEmpresa();
    }
    
    public void cargarAcuerdoEmpresa(){ 
        informacionEmpresaAcuerdo();
        consultarAgrupacionEmpresa();
        consultarResponsableEmpresa();
        consultarObservacionesGenerales();
        sucursalEmpresa();
        contactoSucursalEmpresa();
        responsableInternoCliente();       
        consultarPrincipal();
    }
    
    
    public void informacionEmpresaAcuerdo() {

        try {
            DatabaseResultDto<Empresa> informacionEmpresa
                    = contratoRepo.consultarEmpresa(informacionAsociada.getTdc_td(), informacionAsociada.getEmp_nd());
            if (informacionEmpresa.getStatus().equals(SUCCESS)) {
                this.empresa = informacionEmpresa.getSingleResult();
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) informacionEmpresa.getMessage()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de acuerdo cliente: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    
      public void responsableInternoCliente() {

        try {
            DatabaseResultDto<ResponsableInterno> informacionResponsableInterno
                    = contratoRepo.consultarResponsableInternoEmpresa(empresa.getEMP_ACU_CODIGO());
            if (informacionResponsableInterno.getStatus().equals(SUCCESS)) {
                responsableInternos = (List<ResponsableInterno>) informacionResponsableInterno.getListResult();

            } else {
                responsableInternos = new ArrayList<>();
                System.out.println("No tiene informacio para los responsables internos. Para esta empresa");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de acuerdo cliente: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void sucursalEmpresa() {

        try {
            DatabaseResultDto<SucursalEmpresa> sucursalEmpresa
                    = contratoRepo.consultarSucursalEmpresa(empresa.getEMP_ACU_CODIGO());
            if (sucursalEmpresa.getStatus().equals(SUCCESS)) {
                sucursal = sucursalEmpresa.getListResult();
            } else {
                sucursal = new ArrayList<>();
                System.err.println("No cuenta con sucursales la empresa.");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de sucursal Empresa : " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void contactoSucursalEmpresa() {

        try {
            DatabaseResultDto<ContactoSucursal> contactoSucursal
                    = contratoRepo.consultarContactoSucursal(Integer.parseInt(sucursal.get(0).getSUE_CODIGO()));
            if (contactoSucursal.getStatus().equals(SUCCESS)) {
                contactoSucur = contactoSucursal.getListResult();
            } else {
                contactoSucur = new ArrayList<>();
                System.err.println("No tiene informacion de contacto sucursal para esta empresa");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de contacto Sucursal Empresa: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void consultarPrincipal() {

        try {
            DatabaseResultDto<EmpresaPrincipal> principal = contratoRepo.consultarEmpresaPrincipal(empresa.getEMP_ACU_CODIGO());
            if (principal.getStatus().equals(SUCCESS)) {
                empresaPrincipals = principal.getListResult();
            } else {
                empresaPrincipals = new ArrayList<>();
                System.err.println("No cuenta con empresa Principal esta empresa");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de contacto Sucursal Empresa: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void consultarAgrupacionEmpresa() {

        try {
            DatabaseResultDto<AgrupacionEmpresa> agrupacion = contratoRepo.consultarAgrupacionEmpresa(empresa.getEMP_ACU_CODIGO());
            if (agrupacion.getStatus().equals(SUCCESS)) {
                agrupacionEmpresas = agrupacion.getListResult();
            } else {
                agrupacionEmpresas = new ArrayList<>();
                System.err.println("No se cuenta con agrupaciones empresas para esta empresa");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de Agrupacion Empresa: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void consultarResponsableEmpresa() {

        try {
            DatabaseResultDto<ResponsableEmpresa> responsable = contratoRepo.consultarResponsableEmpresa(empresa.getEMP_ACU_CODIGO(),
                    agrupacionEmpresas.get(1).getAOR_CODIGO());
            if (responsable.getStatus().equals(SUCCESS)) {
                responsableEmpresas = responsable.getListResult();

            } else {
                responsableEmpresas = new ArrayList<>();
                System.err.println("No se cuenta con responsables empresas para esta empresa");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información Responsable Empresa: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void consultarObservacionesGenerales() {

        try {
            DatabaseResultDto<ObservacionesGenerales> observaciones = contratoRepo.consultarObservacionesGenerales(empresa.getEMP_ACU_CODIGO());
            if (observaciones.getStatus().equals(SUCCESS)) {
                observacionesGeneraleses = observaciones.getListResult();
               
            } else {
                observacionesGeneraleses = new ArrayList<>();
                System.out.println("com.co.activos.jadm0067.bean.acuerdoEmpresaBean.consultarObservacionesGenerales()");
                     
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de Observaciones Generales " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void agrupacionSeleccionada() {
        
        try {
            DatabaseResultDto<AcuerdoPorAgrupacion> databaseResultDto = contratoRepo.consultarAcuerdoPorAgrupacion(empresa.getEMP_ACU_CODIGO(), agrupacion.getAGA_CODIGO());
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                acuerdoPorAgrupacions = databaseResultDto.getListResult();        
                consultarNota();
                agrupacionAtributos = new ArrayList<>();
                acuerdoPorAgrupacion = new AcuerdoPorAgrupacion();

            } else {
                observacionesGeneraleses = new ArrayList<>();
                System.out.println("com.co.activos.jadm0067.bean.acuerdoEmpresaBean.agrupacionSeleccionada()");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void agrupacionAtributoSeleccionado(SelectEvent<AcuerdoPorAgrupacion> event) {

        try {
            DatabaseResultDto<AgrupacionAtributo> databaseResultDto
                    = contratoRepo.consultarAgrupacionAtributo(acuerdoPorAgrupacion.getACC_CODIGO());
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                agrupacionAtributos = databaseResultDto.getListResult();
            } else {
                agrupacionAtributos = new ArrayList<>();
                System.out.println("com.co.activos.jadm0067.bean.acuerdoEmpresaBean.agrupacionAtributoSeleccionado()");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void consultarNota() {

        try {
            DatabaseResultDto<NotaPorAgrupacionEmpresa> databaseResultDto = contratoRepo.consultarNotaPorAgrupacion(agrupacion.getAGA_CODIGO());
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                agrupacionEmpresas1 = databaseResultDto.getListResult();
            } else {
                agrupacionEmpresas1 = new ArrayList<>();
                System.out.println("com.co.activos.jadm0067.bean.acuerdoEmpresaBean.consultarNota()");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
    

  
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getLibConsecutivo() {
        return libConsecutivo;
    }

    public void setLibConsecutivo(String libConsecutivo) {
        this.libConsecutivo = libConsecutivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Integer getTotalCandidatos() {
        return totalCandidatos;
    }

    public void setTotalCandidatos(Integer totalCandidatos) {
        this.totalCandidatos = totalCandidatos;
    }

    public boolean isOpcionesNoAprobado() {
        return opcionesNoAprobado;
    }

    public void setOpcionesNoAprobado(boolean opcionesNoAprobado) {
        this.opcionesNoAprobado = opcionesNoAprobado;
    }

    public String getObservacionFinal() {
        return observacionFinal;
    }

    public void setObservacionFinal(String observacionFinal) {
        this.observacionFinal = observacionFinal;
    }

    public CedulaPeritaje getCedulaPeritaje() {
        return cedulaPeritaje;
    }

    public void setCedulaPeritaje(CedulaPeritaje cedulaPeritaje) {
        this.cedulaPeritaje = cedulaPeritaje;
    }

    public AgrupacionEmpresa getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(AgrupacionEmpresa agrupacion) {
        this.agrupacion = agrupacion;
    }

    public InformacionPeritaje getInformacionAsociada() {
        return informacionAsociada;
    }

    public void setInformacionAsociada(InformacionPeritaje informacionAsociada) {
        this.informacionAsociada = informacionAsociada;
    }

    public AcuerdoPorAgrupacion getAcuerdoPorAgrupacion() {
        return acuerdoPorAgrupacion;
    }

    public void setAcuerdoPorAgrupacion(AcuerdoPorAgrupacion acuerdoPorAgrupacion) {
        this.acuerdoPorAgrupacion = acuerdoPorAgrupacion;
    }

    public LibroIngresoDto getLibroIngreso() {
        return libroIngreso;
    }

    public void setLibroIngreso(LibroIngresoDto libroIngreso) {
        this.libroIngreso = libroIngreso;
    }

    public LibroIngresoDocumentoDto getDocumentoSelect() {
        return documentoSelect;
    }

    public void setDocumentoSelect(LibroIngresoDocumentoDto documentoSelect) {
        this.documentoSelect = documentoSelect;
    }

    public Causales getCausa() {
        return causa;
    }

    public void setCausa(Causales causa) {
        this.causa = causa;
    }

    public List<CedulaPeritaje> getCedulasPeritaje() {
        return cedulasPeritaje;
    }

    public void setCedulasPeritaje(List<CedulaPeritaje> cedulasPeritaje) {
        this.cedulasPeritaje = cedulasPeritaje;
    }

    public List<ResponsableInterno> getResponsableInternos() {
        return responsableInternos;
    }

    public void setResponsableInternos(List<ResponsableInterno> responsableInternos) {
        this.responsableInternos = responsableInternos;
    }

    public List<AgrupacionEmpresa> getAgrupacionEmpresas() {
        return agrupacionEmpresas;
    }

    public void setAgrupacionEmpresas(List<AgrupacionEmpresa> agrupacionEmpresas) {
        this.agrupacionEmpresas = agrupacionEmpresas;
    }

    public List<ObservacionesGenerales> getObservacionesGeneraleses() {
        return observacionesGeneraleses;
    }

    public void setObservacionesGeneraleses(List<ObservacionesGenerales> observacionesGeneraleses) {
        this.observacionesGeneraleses = observacionesGeneraleses;
    }

    public List<ResponsableEmpresa> getResponsableEmpresas() {
        return responsableEmpresas;
    }

    public void setResponsableEmpresas(List<ResponsableEmpresa> responsableEmpresas) {
        this.responsableEmpresas = responsableEmpresas;
    }

    public List<AcuerdoPorAgrupacion> getAcuerdoPorAgrupacions() {
        return acuerdoPorAgrupacions;
    }

    public void setAcuerdoPorAgrupacions(List<AcuerdoPorAgrupacion> acuerdoPorAgrupacions) {
        this.acuerdoPorAgrupacions = acuerdoPorAgrupacions;
    }

    public List<SucursalEmpresa> getSucursal() {
        return sucursal;
    }

    public void setSucursal(List<SucursalEmpresa> sucursal) {
        this.sucursal = sucursal;
    }

    public List<ContactoSucursal> getContactoSucur() {
        return contactoSucur;
    }

    public void setContactoSucur(List<ContactoSucursal> contactoSucur) {
        this.contactoSucur = contactoSucur;
    }

    public List<EmpresaPrincipal> getEmpresaPrincipals() {
        return empresaPrincipals;
    }

    public void setEmpresaPrincipals(List<EmpresaPrincipal> empresaPrincipals) {
        this.empresaPrincipals = empresaPrincipals;
    }

    public List<AgrupacionAtributo> getAgrupacionAtributos() {
        return agrupacionAtributos;
    }

    public void setAgrupacionAtributos(List<AgrupacionAtributo> agrupacionAtributos) {
        this.agrupacionAtributos = agrupacionAtributos;
    }

    public List<NotaPorAgrupacionEmpresa> getAgrupacionEmpresas1() {
        return agrupacionEmpresas1;
    }

    public void setAgrupacionEmpresas1(List<NotaPorAgrupacionEmpresa> agrupacionEmpresas1) {
        this.agrupacionEmpresas1 = agrupacionEmpresas1;
    }

    public List<LibroIngresoDocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<LibroIngresoDocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public List<Causales> getCausalesList() {
        return causalesList;
    }

    public void setCausalesList(List<Causales> causalesList) {
        this.causalesList = causalesList;
    }

    public static HttpSession getSesion() {
        return sesion;
    }

    public static void setSesion(HttpSession sesion) {
        acuerdoEmpresaBean.sesion = sesion;
    }

    public HashMap getPermisoVista() {
        return permisoVista;
    }

    public void setPermisoVista(HashMap permisoVista) {
        this.permisoVista = permisoVista;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public List<LocalDate> getRange() {
        return range;
    }

    public void setRange(List<LocalDate> range) {
        this.range = range;
    }

    public PrimeFaces getCurrent() {
        return current;
    }

    public void setCurrent(PrimeFaces current) {
        this.current = current;
    }

    public MesaContratoRepo getContratoRepo() {
        return contratoRepo;
    }

    public void setContratoRepo(MesaContratoRepo contratoRepo) {
        this.contratoRepo = contratoRepo;
    }
    
    
    
    
}
