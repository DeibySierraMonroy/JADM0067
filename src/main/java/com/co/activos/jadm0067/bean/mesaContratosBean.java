package com.co.activos.jadm0067.bean;

import static co.com.activos.jadm0065.util.primefaces.dialogs.DialogServices.showDefaultDialog;
import co.com.activos.jadm0065.util.strings.CustomStringUtils;
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
import com.co.activos.jadm0067.Entities.CedulaPeritaje;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.ERROR;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.DocumentsRepo;
import com.co.activos.jadm0067.Repositories.LibroIngresoRepo;
import com.co.activos.jadm0067.Repositories.MesaContratoRepo;
import com.co.activos.jadm0067.Utilities.SessionUtils;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

@Named
@ViewScoped
public class mesaContratosBean implements Serializable {

    private String estadoCedula = "EMC";
    private HashMap permisoVista;
    private String estadoResponsable = "";
    private List<String> Estados;
    private String Estado;
    private String Causales;
    private String observacion;
    private CedulaPeritaje cedulaPeritaje;
    private List<CedulaPeritaje> cedulasPeritaje;
    private List<ResponsableInterno> responsableInternos;
    private List<AgrupacionEmpresa> agrupacionEmpresas;
    private AgrupacionEmpresa agrupacion;
    private List<ObservacionesGenerales> observacionesGeneraleses;
    private List<ResponsableEmpresa> responsableEmpresas;
    private List<AcuerdoPorAgrupacion> acuerdoPorAgrupacions;
    private List<SucursalEmpresa> sucursal;
    private List<ContactoSucursal> contactoSucur;
    private List<EmpresaPrincipal> empresaPrincipals;
    private List<AgrupacionAtributo> agrupacionAtributos;
    private List<NotaPorAgrupacionEmpresa> agrupacionEmpresas1;
    private InformacionPeritaje informacionAsociada;
    private Empresa empresa;
    private static HttpSession sesion;
    private String sesionActiva;
    private AcuerdoPorAgrupacion acuerdoPorAgrupacion;

    /*   
   * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
     */
    private String libConsecutivo;
    private String usuario;
    private String ipUsuario;
    private LibroIngresoDto libroIngreso;
    private List<LibroIngresoDocumentoDto> documentos;
    private LibroIngresoDocumentoDto documentoSelect;
    private String documentUrl;

    @Inject
    private LibroIngresoRepo libroIngresoRepo;

    @Inject
    private DocumentsRepo documentsRepo;

    @Inject
    private MesaContratoRepo contratoRepo;

    @PostConstruct
    public void init() {
            libConsecutivo = "1496102";
        cedulasPeritaje = new ArrayList<>();
        listarCedulasPeritaje();
        this.sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.sesionActiva = (String) sesion.getAttribute("USS_ID_SESSION");
        cedulaPeritaje = new CedulaPeritaje();
        agrupacion = new AgrupacionEmpresa();
       
        ipUsuario = SessionUtils.getRemoteAddr();
        estadoDelAnalista();
        Estados = Arrays.asList("NAM", "AMC");

    }

    public void estadoDelAnalista() {
        cedulaPeritaje = new CedulaPeritaje();
        try {
            DatabaseResultDto<?> databaseResultDto = contratoRepo.consultarEstadoAnalista(this.sesionActiva);
            if (databaseResultDto.getMessage().equals("V")) {
                permisoEstadoVisualizacion();
                cedulaPeritaje.setLib_consecutivo(databaseResultDto.getResultLong());
                informacionPeritaje();
                DatabaseResultDto<Empresa> informacionEmpresa = contratoRepo.consultarEmpresa(informacionAsociada.getTdc_td(), informacionAsociada.getEmp_nd());
                informacionEmpresaAcuerdo();
                responsableInternoCliente();
                sucursalEmpresa();
                contactoSucursalEmpresa();
                consultarPrincipal();
                consultarAgrupacionEmpresa();
                consultarResponsableEmpresa();
                consultarObservacionesGenerales();
                obtenerDocumentos();
                this.estadoCedula = "RMC";

                //  this.cedulaPeritaje.setLib_consecutivo(databaseResultDto.getResultLong());
            } else {
                permisoEstadoSeleccion();
                listarCedulasPeritaje();

            }

        } catch (Exception e) {
            System.out.println("Eror al traer la data " + e.getMessage());
        }

    }

    public void listarCedulasPeritaje() {
        cedulasPeritaje = new ArrayList<>();
        try {
            DatabaseResultDto<CedulaPeritaje> cedulasObtenidas = contratoRepo.consultarCedulasPeritaje(this.estadoCedula);
            if (cedulasObtenidas.getStatus().equals(SUCCESS)) {
                cedulasPeritaje = cedulasObtenidas.getListResult();
            }
        } catch (Exception e) {
            System.out.println("Error al traer la cedulas " + e.getMessage());
        }
    }

    public void informacionPeritaje() {
        informacionAsociada = new InformacionPeritaje();
        try {
            if (!cedulaPeritaje.getLib_consecutivo().equals(null)) {
                DatabaseResultDto<InformacionPeritaje> databaseResultDto = contratoRepo.consultarDatosPeritaje(cedulaPeritaje.getLib_consecutivo());
                if (databaseResultDto.getStatus().equals(SUCCESS)) {
                    informacionAsociada = databaseResultDto.getSingleResult();
                }
            }
        } catch (Exception e) {
            System.err.println("Error en informacionPeritaje " + e.getMessage());
        }
    }

    public void informacionEmpresaAcuerdo() {

        try {
            DatabaseResultDto<Empresa> informacionEmpresa
                    = contratoRepo.consultarEmpresa(informacionAsociada.getTdc_td(), informacionAsociada.getEmp_nd());
            if (informacionEmpresa.getStatus().equals(SUCCESS)) {
                empresa = informacionEmpresa.getSingleResult();
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

    /*
    Funcion que permite validar los permisos en una estado de Seleccion del analista de mesa de contratos
     */
    public void permisoEstadoSeleccion() {
        permisoVista = new HashMap<>();
        permisoVista.put("historialEmpleado", Boolean.TRUE);
        permisoVista.put("seleccionCandidato", Boolean.TRUE);
        permisoVista.put("estadoFlujo", 0);

    }

    public void permisoEstadoVisualizacion() {
        permisoVista = new HashMap<>();
        permisoVista.put("historialEmpleado", Boolean.TRUE);
        permisoVista.put("seleccionCandidato", Boolean.FALSE);
        permisoVista.put("estadoFlujo", 1);
        permisoVista.put("EstadoVisualizacion", Boolean.TRUE);

    }

    public void Visualizacion() {
        permisoVista = new HashMap<>();
        permisoVista.put("historialEmpleado", Boolean.FALSE);
        permisoVista.put("seleccionCandidato", Boolean.FALSE);
        permisoVista.put("EstadoVisualizacion", Boolean.FALSE);
        permisoVista.put("visualizacionDocumentos", Boolean.TRUE);
        permisoVista.put("estadoFlujo", 1);
    }

    /*
    Funcion que permite almacenar la informacion de cedula peritaje al procedimiento Pl_almacenar_cedula_peritaje
     */
    public void seleccionCandidato(String accion) {
        try {
            if (accion.equals("Seleccionar")) {
                DatabaseResultDto<?> databaseResultDto = contratoRepo.guardarCedulaPeritaje(this.sesionActiva, "V", Integer.parseInt(informacionAsociada.getLib_consecutivo()));
                if (databaseResultDto.getStatus().equals(SUCCESS) && databaseResultDto.getMessage().equals("S")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info !", (String) databaseResultDto.getSingleResult()));
                    informacionEmpresaAcuerdo();
                    responsableInternoCliente();
                    sucursalEmpresa();
                    contactoSucursalEmpresa();
                    consultarPrincipal();
                    Visualizacion();
                    consultarAgrupacionEmpresa();
                    consultarResponsableEmpresa();
                    consultarObservacionesGenerales();
                    consultarNota();
                    agrupacionSeleccionada();
                    obtenerDocumentos();

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", (String) databaseResultDto.getSingleResult()));
                    listarCedulasPeritaje();
                    informacionAsociada = new InformacionPeritaje();
                }
            } else {

                Visualizacion();

            }
        } catch (Exception e) {
            System.err.println("Error al almacenar la informacion " + e.getMessage());
        }

    }

    public void responsableInternoCliente() {

        try {
            DatabaseResultDto<ResponsableInterno> informacionResponsableInterno
                    = contratoRepo.consultarResponsableInternoEmpresa(empresa.getEMP_ACU_CODIGO());
            if (informacionResponsableInterno.getStatus().equals(SUCCESS)) {
                responsableInternos = (List<ResponsableInterno>) informacionResponsableInterno.getListResult();

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) informacionResponsableInterno.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) sucursalEmpresa.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) contactoSucursal.getMessage()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                    "Error no controlado al obtener la información de contacto Sucursal Empresa: " + e.getMessage()));
            e.printStackTrace();
        }

    }

    public void consultarPrincipal() {

        try {
            DatabaseResultDto<EmpresaPrincipal> principal = contratoRepo.consultarEmpresaPrincipal(empresa.getTDC_TD_FIL(),
                    empresa.getEMP_ND_FIL().toString());
            if (principal.getStatus().equals(SUCCESS)) {
                empresaPrincipals = principal.getListResult();
            } else {
                contactoSucur = new ArrayList<>();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) principal.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) agrupacion.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) responsable.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) observaciones.getMessage()));
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !",
                        (String) databaseResultDto.getMessage()));
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
                
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public void cambioEstadoCandidato() {
        System.out.println("com.co.activos.jadm0067.bean.mesaContratosBean.cambioEstadoCandidato()");
    }

    public void closeDialog() {
        System.out.println("LLego");
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('cambioEstado').hide();");

    }

    public void enviarCandidatoFirmar() throws IOException {
        System.out.println("LLego");
        PrimeFaces current = PrimeFaces.current();
        System.out.println("Estado : " + getEstado()
                + " Causal : " + getCausal()
                + " Texto  : " + getObservacion()
        );
        current.executeScript("PF('cambioEstado').hide();");
        init();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("mesaContratos.xhtml");
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /*   
   * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
     */
//    public void getParameters() {
//        try {
//            libConsecutivo = "1496102";
//            usuario = this.sesionActiva;
//            if (CustomStringUtils.anyItemIsNullOrEmpty(libConsecutivo, usuario)) {
//                showDefaultDialog("Alerta", "Loa parámetros de libro de ingreso y usuario, son obligatorios para ejecutar este modulo");
//            } else {
//                obtenerLibroIngreso();
//            }
//        } catch (Exception e) {
//            showDefaultDialog(ERROR.name(), "Error no controlado al obtener los parámetros de sesión de este modulo, causado por: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    /**
     * Obtiene la informacion detallada del libro de ingreso
     */
//    public void obtenerLibroIngreso() {
//        libroIngreso = null;
//        try {
//            DatabaseResultDto<LibroIngresoDto> libroIngresoResult = libroIngresoRepo.getById(Long.valueOf(1496102));
//            if (!libroIngresoResult.getStatus().equals(SUCCESS)) {
//                showDefaultDialog(ERROR.name(), libroIngresoResult.getMessage());
//            } else {
//                libroIngreso = libroIngresoResult.getSingleResult();
//                if (Objects.isNull(libroIngreso.getLibConsecutivo())) {
//                    showDefaultDialog("Alerta", "No se encontro información en la base de datos para este libro");
//                    return;
//                }
//                obtenerDocumentos();
//            }
//        } catch (Exception e) {
//            showDefaultDialog(ERROR.name(), "Error no controlado al obtener la información de este libro de ingreso, causado por: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    /**
     * Inserta y actualiza todos los documentos asociados a un libro de ingreso
     *
     * @return false si el procedimiento termina con error de lo contrario
     * retorna true
     */
    public boolean sincronizarDocumentos() {
        try {

            DatabaseResultDto<?> syncResult = documentsRepo.synchronizedDocuments(Long.valueOf(libConsecutivo));
            if (!syncResult.getStatus().equals(SUCCESS)) {
                showDefaultDialog(ERROR.name(), syncResult.getMessage());
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al sincronizar los documentos para este libro de ingreso, causado por: " + e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * Obtiene el listado de los documentos requeridos
     */
    private void obtenerDocumentos() {
        documentos = new ArrayList<>();
        documentoSelect = null;
        try {
            if (!sincronizarDocumentos()) {
                return;
            }
            DatabaseResultDto<LibroIngresoDocumentoDto> documentosResult = documentsRepo.getAllDocuments(Long.valueOf(1496102));
            if (!documentosResult.getStatus().equals(SUCCESS)) {
                showDefaultDialog(ERROR.name(), documentosResult.getMessage());
            } else {
                documentos = documentosResult.getListResult();
            }
        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al obtener los documentos para este libro de ingreso, causado por: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de un documento en la base de datos
     */
    public void actualizarEstadoDocumento(LibroIngresoDocumentoDto document) {
        try {
            DatabaseResultDto<?> updateDocumentResult = documentsRepo.updateDocument(document);
            if (!updateDocumentResult.getStatus().equals(SUCCESS)) {
                showDefaultDialog(ERROR.name(), updateDocumentResult.getMessage());
            } else {
                obtenerDocumentos();
                documentoSelect = document;
                getFullDocumentUrl();
                showDefaultDialog("Notificación", "Estado del documento, actualizado correctamente.");
            }
        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al obtener los documentos para este libro de ingreso, causado por: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la URL para el documento seleccionado
     */
    public void ObtenerUrl() {
        System.out.println("Entro");

    }

    public void getFullDocumentUrl() {
        documentUrl = null;
        try {
            if (!Objects.isNull(documentoSelect.getDataErpAz().getAzdCodigoCli())) {
                DatabaseResultDto<String> urlResult = documentsRepo.getUrlDocument(usuario, ipUsuario, documentoSelect.getDataErpAz().getAzdCodigoCli());
                if (!urlResult.getStatus().equals(SUCCESS)) {
                    showDefaultDialog(ERROR.name(), urlResult.getMessage());
                } else {
                    documentUrl = urlResult.getSingleResult();
                }
            }
        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al obtener la url para este documento, causado por: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //GETTER AND SETTER OF LOCAL VARS
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

    public LibroIngresoDto getLibroIngreso() {
        return libroIngreso;
    }

    public void setLibroIngreso(LibroIngresoDto libroIngreso) {
        this.libroIngreso = libroIngreso;
    }

    public List<LibroIngresoDocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<LibroIngresoDocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public LibroIngresoDocumentoDto getDocumentoSelect() {
        return documentoSelect;
    }

    public void setDocumentoSelect(LibroIngresoDocumentoDto documentoSelect) {
        this.documentoSelect = documentoSelect;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public HashMap getPermisoVista() {
        return permisoVista;
    }

    public void setPermisoVista(HashMap permisoVista) {
        this.permisoVista = permisoVista;
    }

    public List<String> getEstados() {
        return Estados;
    }

    public void setEstados(List<String> estados) {
        Estados = estados;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCausal() {
        return Causales;
    }

    public void setCausal(String Causal) {
        this.Causales = Causal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<CedulaPeritaje> getCedulasPeritaje() {
        return cedulasPeritaje;
    }

    public void setCedulasPeritaje(List<CedulaPeritaje> cedulasPeritaje) {
        this.cedulasPeritaje = cedulasPeritaje;
    }

    public String getEstadoCedulas() {
        return estadoCedula;
    }

    public void setEstadoCedulas(String estadoCedulas) {
        this.estadoCedula = estadoCedulas;
    }

    public CedulaPeritaje getCedulaPeritaje() {
        return cedulaPeritaje;
    }

    public void setCedulaPeritaje(CedulaPeritaje cedulaPeritaje) {
        this.cedulaPeritaje = cedulaPeritaje;
    }

    public InformacionPeritaje getInformacionAsociada() {
        return informacionAsociada;
    }

    public void setInformacionAsociada(InformacionPeritaje informacionAsociada) {
        this.informacionAsociada = informacionAsociada;
    }

    public String getEstadoResponsable() {
        return estadoResponsable;
    }

    public void setEstadoResponsable(String estadoResponsable) {
        this.estadoResponsable = estadoResponsable;
    }

    public String getSesionActiva() {
        return sesionActiva;
    }

    public void setSesionActiva(String sesionActiva) {
        this.sesionActiva = sesionActiva;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<ResponsableInterno> getResponsableInternos() {
        return responsableInternos;
    }

    public void setResponsableInternos(List<ResponsableInterno> responsableInternos) {
        this.responsableInternos = responsableInternos;
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

    public AgrupacionEmpresa getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(AgrupacionEmpresa agrupacion) {
        this.agrupacion = agrupacion;
    }

    public List<AcuerdoPorAgrupacion> getAcuerdoPorAgrupacions() {
        return acuerdoPorAgrupacions;
    }

    public void setAcuerdoPorAgrupacions(List<AcuerdoPorAgrupacion> acuerdoPorAgrupacions) {
        this.acuerdoPorAgrupacions = acuerdoPorAgrupacions;
    }

    public AcuerdoPorAgrupacion getAcuerdoPorAgrupacion() {
        return acuerdoPorAgrupacion;
    }

    public void setAcuerdoPorAgrupacion(AcuerdoPorAgrupacion acuerdoPorAgrupacion) {
        this.acuerdoPorAgrupacion = acuerdoPorAgrupacion;
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

}
