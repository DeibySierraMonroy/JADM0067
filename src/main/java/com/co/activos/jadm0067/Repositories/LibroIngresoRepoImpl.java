package com.co.activos.jadm0067.Repositories;


import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDto;
import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import com.co.activos.jadm0067.Utilities.OracleConnection;
import oracle.jdbc.OracleTypes;

import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementa todos los metodos de acceso y manipulacion de datos de la entidad RHU.LIBROINGRESO
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
@Stateless
public class LibroIngresoRepoImpl implements LibroIngresoRepo {

    @Override
    public DatabaseResultDto<LibroIngresoDto> getById(Long libConsecutivo) {
        try (Connection connection = OracleConnection.getConnection()) {
            String query = "call ADM.QB_APP_JADM0065_LOGIC.PL_OBTENER_LIBRO_INGRESO(?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(query)) {
                callableStatement.setLong("nmLibConsecutivo", libConsecutivo);
                callableStatement.registerOutParameter("curData", OracleTypes.CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject("curData");
                LibroIngresoDto libroIngreso = new LibroIngresoDto();
                while (resultSet.next()) {
                    libroIngreso.setLibConsecutivo(resultSet.getLong("lib_consecutivo"));
                    libroIngreso.setTdcTdEpl(resultSet.getString("tdc_td_epl"));
                    libroIngreso.setEplNd(resultSet.getInt("epl_nd"));
                    libroIngreso.setEmpleadoNombre(resultSet.getString("empleadoNombre"));
                    libroIngreso.setTdcTdPpal(resultSet.getString("tdc_td_ppal"));
                    libroIngreso.setEmpNdPpal(resultSet.getInt("emp_nd_ppal"));
                    libroIngreso.setPrincipalNombre(resultSet.getString("principalNombre"));
                    libroIngreso.setTdcTdFil(resultSet.getString("tdc_td_fil"));
                    libroIngreso.setEmpNdFil(resultSet.getInt("emp_nd_fil"));
                    libroIngreso.setUsuariaNombre(resultSet.getString("usuariaNombre"));
                    libroIngreso.setCnoCodigo(resultSet.getString("cno_codigo"));
                    libroIngreso.setCargoNombre(resultSet.getString("cargoNombre"));
                    libroIngreso.setLibEstado(resultSet.getString("lib_estado"));
                    libroIngreso.setRequisicion(resultSet.getInt("NRO_REQUISICION"));
                    libroIngreso.setRequisicion(resultSet.getInt("CTO_NUMERO"));
                }
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta existosa", libroIngreso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(DatabaseResultStatus.ERROR, "Error SQL al obtener la informacion para este libro de ingreso, causado por: " + e.getMessage());
        }
    }

}
