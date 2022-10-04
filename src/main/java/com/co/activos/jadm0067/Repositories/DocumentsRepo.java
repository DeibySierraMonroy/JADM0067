package com.co.activos.jadm0067.Repositories;



import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import javax.ejb.Local;

/**
 * Define todos los metodos de acceso y manipulacion de datos de la entidad RHU.LIBROINGRESO_DOCUMENTO
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
@Local
public interface DocumentsRepo {

    /**
     * Inserta o actualiza todos los documentos asociados a un libro de ingreso
     *
     * @param libConsecutivo ID PK de la tabla LibroIngreso
     * @return El resultado de la ejecución del programa
     */
    DatabaseResultDto<?> synchronizedDocuments(Long libConsecutivo);

    /**
     * Obtiene todos los documentos de contratación asociados a un libro de ingreso
     *
     * @param libConsecutivo ID PK de la tabla LibroIngreso
     * @return El Listado de documentos asociados al parametro entrante
     */
    DatabaseResultDto<LibroIngresoDocumentoDto> getAllDocuments(Long libConsecutivo);

    /**
     * Actualiza la información de un documento
     *
     * @param documento Documento a actualizar
     * @return El resultado de la ejecución de la actualización
     */
    DatabaseResultDto<?> updateDocument(LibroIngresoDocumentoDto documento);

    /**
     * Ejecuta la función que obtiene la URL que permite visualizar un archivo en AZ
     *
     * @param usuario        usuario que se conecta a la app
     * @param ipUsuario      IP del equipo desde donde se realiza la peticion
     * @param azdCodigoCli ID único del documento en AZ DIGITAL
     * @return La URL de visualización del documento
     */
    DatabaseResultDto<String> getUrlDocument(String usuario, String ipUsuario, String azdCodigoCli);
}
