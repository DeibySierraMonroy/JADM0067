/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Repositories;

import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionSeguimiento;
import javax.ejb.Local;

/**
 *
 * @author desierra
 */
@Local
public interface AdministracionRepo {
    
       /**
     * Lista las cedulas en un estado EMC para el modulo de administracion.
     *
     * 
     * @return la Lista de cedulas en estado EMC;
     */
     DatabaseResultDto<InformacionSeguimiento> listarCedulasPendientesEMC();
    
}
