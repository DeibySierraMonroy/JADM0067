/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

import static co.com.activos.jadm0067.util.primefaces.dialogs.DialogServices.showDefaultDialog;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.ERROR;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.MesaContratoRepo;
import com.co.activos.jadm0067.Utilities.SessionUtils;
import javax.inject.Inject;

/**
 *
 * @author desierra
 * @param urlRutaPrograma contiene la ruta del proyecto utilitario jadm0047 el
 * cual se encargara de inicializar el servlet ServletPdfReports en JADM0056.
 * @param datosTaxonomia Objeto que contiene los atributos de salida al llamar
 * al procedimiento ADM.QB_DATOS_TAXONOMIA.PL_OBTENER_LIBRO_PROCESO.
 * @param nombreDocumento contiene el nombre del archivo el cual se enviara al
 * ServletPdfReports como parametro para crear la taxonomia de dicho archivo.
 * @param idDocumento contiene el id del documento alojado en la tabla
 * (ADM.tipo_documento)
 */
public class firmarArchivosConcatenados {

    @Inject
    private MesaContratoRepo contratoRepo;

    private final String urlRutaPrograma = "java -DgetResponse=S -cp C:/Genial/Geniexec/jadm0047.jar co.com.activos.jadm0047.ExecuteUrlMain";

    private DatosTaxonomia datosTaxonomia;

    private final String nombreDocumento = "CONTRATO INTEGRAL";

    private final Integer idDocumento = 10; /*10 = CONTRATO INTEGRAL*/
   

    private final String flujoPrincipal = "CONTRATOS";

    private final String tpProceso = "PC";
    
    private final String mensaje = "FIRMA_CONTRATO_INTEGRAL";

    private String urlCotext= SessionUtils.getUrlContext();

    private StringBuilder path = new StringBuilder(urlCotext);
    

    

    public void firmarArchivosConcatenados(Long NMLIBCONSECUTIVO , String correoEmpleado , String correoResponsable , String usuResponsable) {

        try {
            // 1.Obtenemos el AzCodigoCli y DeaCodigo de la carpeta del usuario al cual se esta evaluando.
            DatabaseResultDto<?> databaseResultDto = contratoRepo.obtenerCarpetaDeContratacion(1625443l, "CONTRATOS", "PC");
            if (!databaseResultDto.getStatus().equals(SUCCESS)) {
                System.out.println("Error al consultar la carpeta del candidato");
            }
            datosTaxonomia = (DatosTaxonomia) databaseResultDto.getSingleResult();
            // 2.Obtenemos el context path del aplicativo  path + concatenado con la url del servlet
            path.append("/JADM0056/ServletConcatenarDocumentos?");
            // 3. Enviamos los parametros de DeaCodigo y AzCodigoCli
            path.append("DEA_CODIGO=").append(123456).append("&"); 
            // 4. Enviamos el idDocumento que se obtiene de la tabla adm.tipo_documento
            path.append("PRD_CODIGO=").append(idDocumento).append("&");
            // 5. Enviamos el tipo de flujo.
            path.append("TIPO_FLUJO=").append(flujoPrincipal).append("&");
            // 6. Enviamos el nombre del documento.
            path.append("NOMBRE_ARCHIVO=").append(nombreDocumento).append("&");
            // 7. Enviamos el nombre del documento.
            path.append("MENSAJE=").append(mensaje).append("&");
            // 8. Enviamos el nombre del documento.
            path.append("CORREOEMPLEADO=").append("desierra@activos.com.co").append("&");
            // 9. Enviamos el nombre del documento.
            path.append("CORREORESPONSABLE=").append("juforero@activos.com.co").append("&");
            // 10. Enviamos el nombre del documento.
            path.append("LIB_CONSECUTIVO=").append(NMLIBCONSECUTIVO).append("&");
            // 11. Enviamos el nombre del documento.
            path.append("USU_USUARIO=").append(NMLIBCONSECUTIVO);
            // 12. Enviamos el nombre del documento.
            
          
            //Construimos y ejecutamos la url 
        
	  // Runtime.getRuntime().exec(urlRutaPrograma + path); 
            System.out.println("Ruta final : " + urlRutaPrograma + " ' " + path + " ' ");
        

        } catch (Exception e) {
            showDefaultDialog(ERROR.name(), "Error no controlado al enviar a firmarArchivosConcatenados, causado por: " + e.getMessage());
            e.printStackTrace();

        }

    }

}
