package com.co.activos.jadm0067.bean;


import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static co.com.activos.jadm0067.util.primefaces.dialogs.DialogServices.showDefaultDialog;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.ERROR;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.DocumentsRepo;
import com.co.activos.jadm0067.Repositories.LibroIngresoRepo;
import com.co.activos.jadm0067.Utilities.SessionUtils;
import static com.co.activos.jadm0067.Utilities.SessionUtils.getParameterValue;
import com.co.activos.jadm0067.Utilities.UsuarioSesionController;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Bean - controlador encargado de administrar todos los programas necesarios
 * para el funcionamiento de la pantalla principal de check list de contratos
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
@Named
@ViewScoped
public class CheckBean implements Serializable {

    @Inject
    private LibroIngresoRepo libroIngresoRepo;

    @Inject
    private DocumentsRepo documentsRepo;

    private String libConsecutivo;
    private String usuario;
    private String ipUsuario;
    private LibroIngresoDto libroIngreso;
    private TreeNode<LibroIngresoDocumentoDto> documentos;
    private TreeNode<LibroIngresoDocumentoDto> documentoSelect;
    private InformacionPeritaje informacionAsociada;
    private String documentUrl;
        private UsuarioSesionController usuarioSesionController;

    @PostConstruct
    public void init() {
        obtenerDocumentos();
        ipUsuario = SessionUtils.getRemoteAddr();

    }

    public boolean sincronizarDocumentos() {
        try {
        
            DatabaseResultDto<?> syncResult = documentsRepo.synchronizedDocuments(Long.valueOf(informacionAsociada.getLib_consecutivo()));
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

        informacionAsociada = (InformacionPeritaje) getParameterValue("informacionAsociadaBean");
        usuarioSesionController = (UsuarioSesionController) getParameterValue("informacionUsuario");
        try {
            
            //1. Crea el nodo padre o raíz
            documentos = new DefaultTreeNode<>(new LibroIngresoDocumentoDto(), null);
            //2. Ejecuta la sincronización de documentos
            if (!sincronizarDocumentos()) {
                return;
            }
            //3. Obtiene los nodos padre, todos los documentos que no tienen hijos
            DatabaseResultDto<LibroIngresoDocumentoDto> documentsParent = documentsRepo.getAllDocumentsParent(Long.valueOf(informacionAsociada.getLib_consecutivo()));
            if (!documentsParent.getStatus().equals(SUCCESS)) {
                showDefaultDialog(ERROR.name(), documentsParent.getMessage());
            } else {
                //4. Obtiene los documentos que tienen un padre
                DatabaseResultDto<LibroIngresoDocumentoDto> documentsChild = documentsRepo.getAllDocumentsChild(Long.valueOf(informacionAsociada.getLib_consecutivo()));
                if (!documentsChild.getStatus().equals(SUCCESS)) {
                    showDefaultDialog(ERROR.name(), documentsParent.getMessage());
                    return;
                }
                //5. Almacena las listas de los documentos padres e hijos y crea los nodos del arbol
                List<LibroIngresoDocumentoDto> parent = documentsParent.getListResult();
                List<LibroIngresoDocumentoDto> child = documentsChild.getListResult();
                for (LibroIngresoDocumentoDto doc : parent) {
                    TreeNode<LibroIngresoDocumentoDto> nodeParent = new DefaultTreeNode<>(doc, documentos);
                    child.stream().filter(it -> Objects.equals(doc.getId(), it.getIdPadre())).forEach(ch -> {
                        TreeNode<LibroIngresoDocumentoDto> nodeChild = new DefaultTreeNode<>(ch, nodeParent);
                    });
                }
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
                documentoSelect = documentos.getChildren().stream().filter(it -> Objects.equals(it.getData().getId(), document.getId())).findFirst().orElse(null);
                getFullDocumentUrl();
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info !", "Documento Actualizado con Exito !."));
            }
        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al obtener los documentos para este libro de ingreso, causado por: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la URL para el documento seleccionado
     */
    public void getFullDocumentUrl() {
        documentUrl = null;
        try {
            LibroIngresoDocumentoDto document = documentoSelect.getData();
            if (!Objects.isNull(document.getDataErpAz().getAzdCodigoCli())) {
                DatabaseResultDto<String> urlResult = documentsRepo.getUrlDocument(usuarioSesionController.getUsuarioSesion().getUsuUsuario(), ipUsuario, document.getDataErpAz().getAzdCodigoCli());
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

    public TreeNode<LibroIngresoDocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(TreeNode<LibroIngresoDocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public TreeNode<LibroIngresoDocumentoDto> getDocumentoSelect() {
        return documentoSelect;
    }

    public void setDocumentoSelect(TreeNode<LibroIngresoDocumentoDto> documentoSelect) {
        this.documentoSelect = documentoSelect;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
