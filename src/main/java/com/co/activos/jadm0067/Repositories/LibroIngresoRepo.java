package com.co.activos.jadm0067.Repositories;


import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import javax.ejb.Local;

/**
 * Define todos los metodos de acceso y manipulacion de datos de la entidad RHU.LIBROINGRESO
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
@Local
public interface LibroIngresoRepo {

    DatabaseResultDto<LibroIngresoDto> getById(Long libConsecutivo);

}
