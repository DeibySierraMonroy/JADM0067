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
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ResponsableContactoEmpresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ResponsableEmpresa;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.ResponsableInterno;
import com.co.activos.jadm0067.Entities.AcuerdoEmpresa.SucursalEmpresa;
import com.co.activos.jadm0067.Entities.CedulaPeritaje;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionPeritaje;
import static java.util.Collections.*;
import javax.ejb.Local;

/**
 * Define todos los metodos y accion a realizar en mesaContrato del paquete qb_JADM0067_mesa_contratos
 * 
 * @author desierra
 */


    @Local
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
     * @param vcestadoResponsable contiene el estado del responsable (Visualizacion,Disponible).
     * @param nmlibconsecutivo contiene el valor de libroIngreso.
     * @return Un objeto de tipo InformacionPeritaje.
     */
       
       DatabaseResultDto<?> guardarCedulaPeritaje(String vcresponsable,String vcestadoResponsable,Integer nmlibconsecutivo);
       
       
          /**
     *Consulta el estado del Analista
     *
     * @param vcresponsable contiene el valor del responsable del proceso.
     *
     * @return el libro de ingreso si el analista esta un estado de Visualizacion en caso de que no retornara un estado Disponible
     */
       
       DatabaseResultDto<?> consultarEstadoAnalista(String vcresponsable);
       
       DatabaseResultDto<Empresa> consultarEmpresa (String VCTDC_TD , String VCEMP_ND );
       
          
       DatabaseResultDto<ResponsableInterno> consultarResponsableInternoEmpresa(Integer NMEMP_ACU_CODIGO);
       
//       DatabaseResultDto<ResponsableEmpresa> consultarResponsableEmpresa(Integer NMEMP_ACU_CODIGO);
       
       DatabaseResultDto<SucursalEmpresa> consultarSucursalEmpresa(Integer NMEMP_ACU_CODIGO);
//       
        DatabaseResultDto<ContactoSucursal> consultarContactoSucursal(Integer NMSUE_CODIGO);
//       
     DatabaseResultDto<EmpresaPrincipal> consultarEmpresaPrincipal(String VCTDC_TD , String VCEMP_ND );
//       
       DatabaseResultDto<AgrupacionEmpresa> consultarAgrupacionEmpresa (Integer NMEMP_ACU_CODIGO);
//       
      DatabaseResultDto<AcuerdoPorAgrupacion> consultarAcuerdoPorAgrupacion(Integer NMEMP_ACU_CODIGO , Integer NMAGA_CODIGO);
//       
       DatabaseResultDto<AgrupacionAtributo> consultarAgrupacionAtributo(Integer NMACC_CODIGO);
//       
       DatabaseResultDto<ObservacionesGenerales> consultarObservacionesGenerales(Integer NMEMP_ACU_CODIGO);
//       
       DatabaseResultDto<NotaPorAgrupacionEmpresa> consultarNotaPorAgrupacion(Integer NMAGA_CODIGO);
//       
     DatabaseResultDto<ResponsableEmpresa> consultarResponsableEmpresa(Integer NMEMP_ACU_CODIGO , Integer NMAOR_CODIGO);
//       
//       DatabaseResultDto<ResponsableContactoEmpresa> consultarResponsableContactoEmpresa(Integer NMRCL_CODIGO);
       
    
       
}
