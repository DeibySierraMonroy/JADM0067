/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Repositories;

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
import com.co.activos.jadm0067.Entities.DatosTaxonomia;
import com.co.activos.jadm0067.Entities.EstadisticasAnalista;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import javax.ejb.Local;

/**
 * Define todos los metodos y accion a realizar en mesaContrato del paquete
 * qb_JADM0067_mesa_contratos
 *
 * @author desierra
 */

public interface MesaContratoRepo {

    /**
     * Consultas la cedulas con estado 'EMC'
     *
     * @param vcstdoestado
     * @return Lista de cedulas en estado 'EMC' de tipo CedulaPeritaje
     */
    DatabaseResultDto<CedulaPeritaje> consultarCedulasPeritaje(String vcstdoestado);

    /**
     * Consultas la informacion general que contenga la cedula seleccionada.
     *
     * @param nmordencto contiene el valor de libroIngreso.
     * @return Un objeto de tipo InformacionPeritaje.
     */
    DatabaseResultDto<InformacionPeritaje> consultarDatosPeritaje(Long nmordencto);

    /**
     * Almacena el responsable del peritaje.
     *
     * @param vcresponsable contiene el valor del responsable del proceso.
     * @param vcestadoResponsable contiene el estado del responsable
     * (Visualizacion,Disponible).
     * @param nmlibconsecutivo contiene el valor de libroIngreso.
     * @return Un objeto de tipo InformacionPeritaje.
     */
    DatabaseResultDto<?> guardarCedulaPeritaje(String vcresponsable, String vcestadoResponsable, Integer nmlibconsecutivo);

    /**
     * Consulta el estado del Analista
     *
     * @param vcresponsable contiene el valor del responsable del proceso.
     *
     * @return el libro de ingreso si el analista esta un estado de
     * Visualizacion en caso de que no retornara un estado Disponible
     */
    DatabaseResultDto<?> consultarEstadoAnalista(String vcresponsable);

    /**
     * Implementacion de los repositorios para consultar los acuerdos por
     * empresa
     */
    /**
     * consulta la empresa.
     *
     * @param VCTDC_TD contiene el valor del tipo de documento de la empresa..
     * @param VCEMP_ND contiene el numero de la empresa a consultar.
     *
     * @return Lista de tipo Empresa.
     */
    DatabaseResultDto<Empresa> consultarEmpresa(String VCTDC_TD, String VCEMP_ND);

    /**
     * Consulta el Responsable Interno Empresa
     *
     * @param NMEMP_ACU_CODIGO
     * @return Lista de Responsable Interno
     */
    DatabaseResultDto<ResponsableInterno> consultarResponsableInternoEmpresa(Integer NMEMP_ACU_CODIGO);

    /**
     * Contiene las sucursales para la empresa que se consulta.
     *
     * @param NMEMP_ACU_CODIGO
     * @return Retonorna una lista de tipo Sucursal Empresa.
     */
    DatabaseResultDto<SucursalEmpresa> consultarSucursalEmpresa(Integer NMEMP_ACU_CODIGO);

    /**
     * Contiene los contacto de las sucursales consultadas.
     *
     * @param NMSUE_CODIGO
     * @return Retonorna una lista de tipo ContactoSucursal.
     */
//       
    DatabaseResultDto<ContactoSucursal> consultarContactoSucursal(Integer NMSUE_CODIGO);

    /**
     * Consulta las empresas Principales segun el acuerdo.
     *
     * @param NMEMP_ACU_CODIGO
     * @return Retonorna una lista de tipo EmpresaPrincipal.
     */
//       
    DatabaseResultDto<EmpresaPrincipal> consultarEmpresaPrincipal(Integer NMEMP_ACU_CODIGO);

    /**
     *
     * Consulta agrupacion segun la empresa a consulta.
     *
     * @param NMEMP_ACU_CODIGO
     * @return Retonorna una lista de tipo consultarAgrupacionEmpresa.
     */
//       
    DatabaseResultDto<AgrupacionEmpresa> consultarAgrupacionEmpresa(Integer NMEMP_ACU_CODIGO);

    /**
     * Connsulta el Acuerdo por agrupacion
     *
     * @param NMEMP_ACU_CODIGO
     * @param NMAGA_CODIGO
     * @return Retonorna una lista de tipo AcuerdoPorAgrupacion.
     */
//       
    DatabaseResultDto<AcuerdoPorAgrupacion> consultarAcuerdoPorAgrupacion(Integer NMEMP_ACU_CODIGO, Integer NMAGA_CODIGO);

    /**
     * Consulta agrupacion por atributo
     *
     * @param NMACC_CODIGO
     * @return Retonorna una lista de tipo AgrupacionAtributo
     */
//       
    DatabaseResultDto<AgrupacionAtributo> consultarAgrupacionAtributo(Integer NMACC_CODIGO);

    /**
     *
     * Consulta las observaciones generales del acuerdo.
     *
     * @param NMEMP_ACU_CODIGO
     * @return Retonorna una lista de tipo ObservacionesGenerales
     */
//       
    DatabaseResultDto<ObservacionesGenerales> consultarObservacionesGenerales(Integer NMEMP_ACU_CODIGO);

    /**
     * Consulta notas basado en la agrupacion seleccionada.
     *
     * @param NMAGA_CODIGO
     * @return Retorna una lista de tipo NotaPorAgrupacionEmpresa
     */
//       
    DatabaseResultDto<NotaPorAgrupacionEmpresa> consultarNotaPorAgrupacion(Integer NMAGA_CODIGO);

    /**
     *
     * Consultar los reponsables internos de la empresa.
     *
     * @param NMEMP_ACU_CODIGO
     * @param NMAOR_CODIGO
     * @return Retorna una lista de tipo consultarResponsableEmpresa
     */
//       
    DatabaseResultDto<ResponsableEmpresa> consultarResponsableEmpresa(Integer NMEMP_ACU_CODIGO, Integer NMAOR_CODIGO);

//       DatabaseResultDto<ResponsableContactoEmpresa> consultarResponsableContactoEmpresa(Integer NMRCL_CODIGO);
    /**
     * Obtiene los causales posibles para finalizar el proceso cuando el estado
     * es NAM
     *
     * @return Retorna una lista de tipo Causales
     */
    DatabaseResultDto<Causales> consultarCausales();

    /**
     * Obtiene la informacion personal del analista.
     *
     * @param TDC_TD_EPL
     * @param EPL_ND
     * @param Columna
     * @return Lista de tipo generico.
     */
    DatabaseResultDto<?> consultarInformacionAnalista(String TDC_TD_EPL, Long EPL_ND, String Columna);

    /**
     * Interfaz encargada de enviar los parametros para finalizar el proceso en
     * la mesa de contratos.
     *
     * @param NMLIBCONSECUTIVO
     * @param VCSTDOESTADO
     * @param VCOBSERVACION
     * @param VCUSUARIO
     * @param NMCAUSAL
     * @return String de tipo generico.
     */
    DatabaseResultDto<?> finalizarProceso(Long NMLIBCONSECUTIVO, String VCSTDOESTADO,
            String VCOBSERVACION, String VCUSUARIO,
            Integer NMCAUSAL);

    /**
     * Consulta las estadisticas del analista en la mesa.
     *
     * @return String de tipo EstadisticasAnalista.
     */
    DatabaseResultDto<EstadisticasAnalista> consultarEstadisticaAnalista(String vcresponsable);

    /**
     *
     * @param NMLIBCONSECUTIVO
     * @param VCSTDOESTADO
     * @return Retorna un String que contiene la observacion de la cedula la
     * cual estan validando.
     */
    DatabaseResultDto<String> consultarObservacionCedulaPeritaje(Long NMLIBCONSECUTIVO, String VCSTDOESTADO);

    /**
     *
     * @param NMLIBCONSECUTIVO
     * @param flujoPrincipal
     * @param tpProceso
     * @return Retorna Objeto de tipo DatosTaxonomia el cual contiene la
     * ubicacion de la carpeta de contratacion.
     */
    DatabaseResultDto<DatosTaxonomia> obtenerCarpetaDeContratacion(Long NMLIBCONSECUTIVO, String flujoPrincipal, String tpProceso);
    
 
    /**
     * @param log     
     * @param tipo     
     * @param aplicativo     
     * @param estadoTransaccio     
     * @param transaccion     
     * @return  Este metodo tiene como finalidad registrar la auditoria del envio documentos a firmar al candidato.
     */
   DatabaseResultDto<?> crearAuditoria(String log,DatabaseResultStatus tipo , String aplicativo,String transaccion,String estadoTransaccion);

}
