package com.co.activos.jadm0067.bean;

import static co.com.activos.jadm0067.util.primefaces.dialogs.DialogServices.showDefaultDialog;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.Empresa;
import com.co.activos.jadm0067.Entities.Causales;
import com.co.activos.jadm0067.Entities.CedulaPeritaje;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.DatosTaxonomia;
import com.co.activos.jadm0067.Entities.EstadisticasAnalista;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.ERROR;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.MesaContratoRepo;
import com.co.activos.jadm0067.Utilities.SessionUtils;
import static com.co.activos.jadm0067.Utilities.SessionUtils.getParameterValue;
import com.co.activos.jadm0067.Utilities.UsuarioSesionController;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.charts.line.*;

@Named
@ViewScoped
public class mesaContratosBean implements Serializable {

    /*Declaracion de Atributos*/
    private String estadoCedula = "EMC";
    private String estadoResponsable = "";
    private String Estado = "AMC";
    private String Causales;
    private String observacion;
    private String sesionActiva;
    private String libConsecutivo;
    private String usuario;
    private String ipUsuario;
    private String documentUrl;
    private Integer totalCandidatos;
    private boolean opcionesNoAprobado = false;
    private String observacionFinal;
    private String redireccion;
    private String observacionCedulaPeritaje;

    /*Declaracion de objetos*/
    private CedulaPeritaje cedulaPeritaje;
    private EstadisticasAnalista analista;
    private InformacionPeritaje informacionAsociada;
    private Empresa empresa;
    private UsuarioSesionController usuarioSesionController;
    private LibroIngresoDto libroIngreso;
    private LibroIngresoDocumentoDto documentoSelect;
    private Causales causa;

    /*Declaracion de listas*/
    private List<CedulaPeritaje> cedulasPeritaje;

    private List<LibroIngresoDocumentoDto> documentos;
    private List<Causales> causalesList;

    /*Declaracion de utilitarios*/
    private HashMap permisoVista;
    private LineChartModel lineModel;
    private List<LocalDate> range;

    /* Variables estaticas para cosntruit la url */
    private final String urlRutaPrograma = "java -DgetResponse=S -cp C:/Genial/Geniexec/jadm0047.jar co.com.activos.jadm0047.ExecuteUrlMain";
    private DatosTaxonomia datosTaxonomia;
    private final String nombreDocumento = "CONTRATO-INTEGRAL.pdf";
    private final Integer idDocumento = 10;
    /*10 = CONTRATO INTEGRAL*/
    private final String flujoPrincipal = "CONTRATOS";
    private final String tpProceso = "PC";
    private final String mensaje = "CTO_NUMERO";
    private String urlCotext = SessionUtils.getUrlContext();
    private StringBuilder path = new StringBuilder(urlCotext);


    /*Inyecion de dependencias*/
    @Inject
    private MesaContratoRepo contratoRepo;

    /*Metodo inicial del bean */
    @PostConstruct
    public void init() {
        /*Metodo encargado inicializar los valores*/
        inicializacion();

        /*Metodo encargado de validar el estado del analista*/
        estadoDelAnalista();

    }

    public void inicializacion() {
        /*Metodo encargado de traer la session del usuario*/
        usuarioSesionController = (UsuarioSesionController) getParameterValue("informacionUsuario");
        sesionActiva = usuarioSesionController.getUsuarioSesion().getUsuUsuario();

        /*Metodo encargado de traer la ip del usuario*/
        ipUsuario = SessionUtils.getRemoteAddr();

        /* Obtiene la estadisticas del analista */
        estadisticasAnalista();

    }


    /* Metodo encargado de cargar las cedulas disponibles en estado EMC*/
    public void listarCedulasPeritaje() {
        cedulasPeritaje = new ArrayList<>();
        try {
            DatabaseResultDto<CedulaPeritaje> cedulasObtenidas = contratoRepo.consultarCedulasPeritaje(this.estadoCedula);
            if (cedulasObtenidas.getStatus().equals(SUCCESS)) {
                cedulasPeritaje = cedulasObtenidas.getListResult();
                totalCandidatos = cedulasPeritaje.size();
            }
        } catch (Exception e) {
            System.out.println("Error al traer la cedulas " + e.getMessage());
        }
    }


    /* Metodo encargado de validar el estado del analista*/
    public String estadoDelAnalista() {
        String redireccion = "";
        cedulaPeritaje = new CedulaPeritaje();
        try {
            DatabaseResultDto<?> databaseResultDto = contratoRepo.consultarEstadoAnalista(this.sesionActiva);
            if (databaseResultDto.getMessage().equals("V")) {
                cedulaPeritaje.setLib_consecutivo(databaseResultDto.getResultLong());
                informacionPeritaje();
                this.estadoCedula = "RMC";
                redireccion = "visualizacion?faces-redirect=true";
            } else {
                listarCedulasPeritaje();
                redireccion = "seleccion?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("Eror al traer la data " + e.getMessage());
        }
        return redireccion;
    }


    /*Metodo encargado de traer la informacion de la empresa y informacion candidato*/
    public void informacionPeritaje() {
        informacionAsociada = new InformacionPeritaje();
        try {
            if (!cedulaPeritaje.getLib_consecutivo().equals(null)) {
                DatabaseResultDto<InformacionPeritaje> databaseResultDto = contratoRepo.consultarDatosPeritaje(cedulaPeritaje.getLib_consecutivo());
                if (databaseResultDto.getStatus().equals(SUCCESS)) {
                    informacionAsociada = databaseResultDto.getSingleResult();
                    observacionCedulaPeritaje = consultarObservacion(cedulaPeritaje.getLib_consecutivo(), "EMC");
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("informacionAsociadaBean", informacionAsociada);
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LIB_CONSECUTIVO", informacionAsociada.getLib_consecutivo());
            }
        } catch (Exception e) {
            System.err.println("Error en informacionPeritaje " + e.getMessage());
        }
    }

    /*
    Funcion que permite almacenar la informacion de cedula peritaje al procedimiento Pl_almacenar_cedula_peritaje
     */
    public String seleccionCandidato(String accion) {
        String redireccion = "";
        try {
            if (accion.equals("Seleccionar")) {
                DatabaseResultDto<?> databaseResultDto = contratoRepo.guardarCedulaPeritaje(this.sesionActiva, "V", Integer.parseInt(informacionAsociada.getLib_consecutivo()));
                if (databaseResultDto.getStatus().equals(SUCCESS) && databaseResultDto.getMessage().equals("S")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info !", (String) databaseResultDto.getSingleResult()));
                    informacionPeritaje();
                    redireccion = "mesaContratos?faces-redirect=true";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", (String) databaseResultDto.getSingleResult()));
                    listarCedulasPeritaje();
                    informacionAsociada = new InformacionPeritaje();
                    cedulaPeritaje.setEpl_nd(null);
                }
            } else {
                redireccion = "mesaContratos?faces-redirect=true";
            }
        } catch (Exception e) {
            System.err.println("Error al almacenar la informacion " + e.getMessage());
        }
        return redireccion;
    }

    public void respuestaDelProceso() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info !", "Proceso Finalizado con Exito !."));
    }

    public void estadisticasAnalista() {
        analista = new EstadisticasAnalista();
        try {
            DatabaseResultDto<EstadisticasAnalista> databaseResultDto = contratoRepo.consultarEstadisticaAnalista(this.sesionActiva);
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                analista = databaseResultDto.getSingleResult();
            }
        } catch (Exception e) {
            System.err.println("Error al traer  las estadisticas " + e.getMessage());
        }

    }

    public String aprobarCandidato() {
        try {
            if (this.observacionFinal != "" && !this.observacionFinal.isEmpty()) {
                DatabaseResultDto<?> result = contratoRepo.finalizarProceso(
                        Long.parseLong(informacionAsociada.getLib_consecutivo()),
                        "PFC", this.observacionFinal, this.sesionActiva, 67);
                if (result.getMessage().equals("S")) {
                    redireccion = "seleccion?faces-redirect=true";
                    firmarArchivosConcatenados(Long.valueOf(informacionAsociada.getLib_consecutivo()),
                            informacionAsociada.getCorreo_empleado(), "desierra@activos.com.co", sesionActiva);
                } else {
                    redireccion = "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", "El Proceso no se pudo Finalizar con Exito !. " + result.toString()));
                }
            } else {
                redireccion = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", "La observacion no puede ir vacia , verifique de nuevo!. "));
            }
        } catch (Exception e) {
            System.out.println("Error al aprobar el candidato" + e.getMessage());
        }
        return redireccion;
    }

    public String rechazarCandidato() {
        try {
            if (this.observacionFinal != "" && !this.observacionFinal.isEmpty()) {
                DatabaseResultDto<?> result = contratoRepo.finalizarProceso(Long.parseLong(informacionAsociada.getLib_consecutivo()),
                        this.Estado, this.observacionFinal, this.sesionActiva, this.causa.getCodigo_causal());
                if (result.getMessage().equals("S")) {
                    redireccion = "seleccion?faces-redirect=true";
                } else {
                    redireccion = "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", "El Proceso no se pudo Finalizar con Exito !."));
                }
            } else {
                redireccion = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", "El Proceso no se pudo Finalizar debido a que la observacion o el tipo de causal no puede ir vacios , verifique de nuevo! !."));
            }
        } catch (Exception e) {
            System.out.println("Error al rechazar el candidato" + e.getMessage());
        }
        return redireccion;

    }

    public String enviarCandidatoFirmar() {
        String redireccionar = "";
        try {
            if (Estado.equals("AMC")) {
                redireccionar = aprobarCandidato();
            }
            if (Estado.equals("NAM")) {
                redireccionar = rechazarCandidato();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error !", "Al escoger el estado .!. " + e.getCause()));
        }
        return redireccionar;

    }

    public void cambiarEstadoFinal() {
        if (this.Estado.equals("NAM")) {
            this.opcionesNoAprobado = true;
            causales();
        } else {
            this.opcionesNoAprobado = false;
        }
    }

    public void seleccionarCausal() {
        if (this.Estado.equals("NAM")) {
            this.observacionFinal = this.causa.getDescripcion_causal() + "\n";
        }
    }

    public void causales() {
        this.causalesList = new ArrayList<>();
        try {
            DatabaseResultDto<Causales> causal = contratoRepo.consultarCausales();
            if (causal.getStatus().equals(SUCCESS)) {
                this.causalesList = causal.getListResult();
            } else {
                this.causalesList = new ArrayList<>();
                System.err.println("Error en : " + causal.getMessage());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public String consultarObservacion(Long NMLIBCONSECUTIVO, String VCSTDOESTADO) {
        String observacionCedula = "";
        try {
            DatabaseResultDto<String> databaseResultDto
                    = contratoRepo.consultarObservacionCedulaPeritaje(NMLIBCONSECUTIVO, VCSTDOESTADO);
            if (databaseResultDto.getStatus().equals(SUCCESS)) {
                observacionCedula = databaseResultDto.getMessage();
            } else {
                observacionCedula = "";
            }
        } catch (Exception e) {
            System.out.println("Error al traer la informacion de Consultar las observaciones.");
        }
        return observacionCedula;
    }

    public void firmarArchivosConcatenados(Long NMLIBCONSECUTIVO, String correoEmpleado, String correoResponsable, String usuResponsable) {

        try {
            // 1.Obtenemos el AzCodigoCli y DeaCodigo de la carpeta del usuario al cual se esta evaluando.
            DatabaseResultDto<?> databaseResultDto = contratoRepo.obtenerCarpetaDeContratacion(NMLIBCONSECUTIVO, flujoPrincipal, tpProceso);
            if (!databaseResultDto.getStatus().equals(SUCCESS)) {
                System.out.println("Error al consultar la carpeta del candidato");
            }
            datosTaxonomia = (DatosTaxonomia) databaseResultDto.getSingleResult();
            // 2.Obtenemos el context path del aplicativo  path + concatenado con la url del servlet
            path.append("/JADM0056/ServletConcatenarDocumentos?");
            // 3. Enviamos los parametros de DeaCodigo y AzCodigoCli
            path.append("DEA_CODIGO=").append(datosTaxonomia.getDeaCodigo()).append("&");
            // 4. Enviamos el idDocumento que se obtiene de la tabla adm.tipo_documento
            path.append("PRD_CODIGO=").append(idDocumento).append("&");
            // 5. Enviamos el tipo de flujo.    
            path.append("TIPO_FLUJO=").append(flujoPrincipal).append("&");
            // 6. Enviamos el nombre del documento.
            path.append("NOMBRE_ARCHIVO=").append(nombreDocumento).append("&");
            // 7. Enviamos el nombre del documento.
            path.append("MENSAJE=").append(mensaje + " " + informacionAsociada.getNumero_contrato()).append("&");
            // 8. Enviamos el nombre del documento.
            path.append("CORREOEMPLEADO=").append(correoEmpleado).append("&");
            // 9. Enviamos el nombre del documento.
            path.append("CORREORESPONSABLE=").append(correoResponsable).append("&");
            // 10. Enviamos el nombre del documento.
            path.append("LIB_CONSECUTIVO=").append(informacionAsociada.getLib_consecutivo()).append("&");
            // 11. Enviamos el nombre del documento.
            path.append("USU_USUARIO=").append(sesionActiva);
            // 12. Enviamos el nombre del documento.

            //Construimos y ejecutamos la url 
            Process proc = Runtime.getRuntime().exec(urlRutaPrograma + " \"" + path + "\"");
            System.out.println("Ruta final : " + urlRutaPrograma + " \"" + path + "\"");

            BufferedReader respuestaExistosa = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String respuesta = null;
            while (Objects.nonNull(respuesta = respuestaExistosa.readLine())) {
                System.out.println("Repuesta : " + respuesta);
            }
            contratoRepo.crearAuditoria(respuesta, DatabaseResultStatus.SUCCESS, "JADM0067:mesaContratosBean:firmarArchivosConcatenados",String.valueOf(NMLIBCONSECUTIVO), "OK");

        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al enviar a firmarArchivosConcatenados, causado por: " + e.getMessage());
            e.printStackTrace();

        }

    }

    //GETTER AND SETTER OF LOCAL VARS
    public List<LocalDate> getRange() {
        return range;
    }

    public void setRange(List<LocalDate> range) {
        this.range = range;
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

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public Integer getTotalCandidatos() {
        return totalCandidatos;
    }

    public void setTotalCandidatos(Integer totalCandidatos) {
        this.totalCandidatos = totalCandidatos;
    }

    public boolean getOpcionesNoAprobado() {
        return opcionesNoAprobado;
    }

    public void setOpcionesNoAprobado(boolean opcionesNoAprobado) {
        this.opcionesNoAprobado = opcionesNoAprobado;
    }

    public List<Causales> getCausalesList() {
        return causalesList;
    }

    public void setCausalesList(List<Causales> causalesList) {
        this.causalesList = causalesList;
    }

    public Causales getCausa() {
        return causa;
    }

    public void setCausa(Causales causa) {
        this.causa = causa;
    }

    public String getObservacionFinal() {
        return observacionFinal;
    }

    public void setObservacionFinal(String observacionFinal) {
        this.observacionFinal = observacionFinal;
    }

    public EstadisticasAnalista getAnalista() {
        return analista;
    }

    public void setAnalista(EstadisticasAnalista analista) {
        this.analista = analista;
    }

    public UsuarioSesionController getUsuarioSesionController() {
        return usuarioSesionController;
    }

    public void setUsuarioSesionController(UsuarioSesionController usuarioSesionController) {
        this.usuarioSesionController = usuarioSesionController;
    }

    public String getRedireccion() {
        return redireccion;
    }

    public void setRedireccion(String redireccion) {
        this.redireccion = redireccion;
    }

    public String getObservacionCedulaPeritaje() {
        return observacionCedulaPeritaje;
    }

    public void setObservacionCedulaPeritaje(String observacionCedulaPeritaje) {
        this.observacionCedulaPeritaje = observacionCedulaPeritaje;
    }

}
