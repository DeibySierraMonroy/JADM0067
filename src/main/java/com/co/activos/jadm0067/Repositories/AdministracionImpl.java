/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Repositories;

import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionSeguimiento;
import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.ERROR;
import com.co.activos.jadm0067.Utilities.OracleConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author desierra
 */
@Stateless
public class AdministracionImpl implements AdministracionRepo {

    
    @Override
    public DatabaseResultDto<InformacionSeguimiento> listarCedulasPendientesEMC() {
          String result = "";

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_seguimiento_mesa(?,?,?)";

            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de salida
                callableStatement.registerOutParameter("csconsulta", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                callableStatement.execute();

               result = callableStatement.getString("vcestadoproceso");

                ResultSet resultSet = (ResultSet) callableStatement.getObject("csconsulta");
                List<InformacionSeguimiento> informacionSegui = new ArrayList<>();

                while (resultSet.next()) {

                    InformacionSeguimiento informacionSeguimiento = new InformacionSeguimiento();
                    informacionSeguimiento.setFecha_estado_emc(resultSet.getDate("fecha"));
                    informacionSeguimiento.setInc_usuario_nue(resultSet.getString("inc_usuario_nue"));
                    informacionSeguimiento.setEpl_nd(resultSet.getString("epl_nd"));
                    informacionSeguimiento.setEmpresa(resultSet.getString("empresa"));
                    informacionSeguimiento.setTdc_td_epl(resultSet.getString("tdc_td_epl"));
                    informacionSeguimiento.setLib_consecutivo(resultSet.getString("lib_consecutivo"));
                    informacionSeguimiento.setTiempo_transcurrido(resultSet.getString("TIEMPO_TRANCURRIDO"));
                    

                    informacionSegui.add(informacionSeguimiento);

                }
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta existosa", informacionSegui);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(ERROR, "Error SQL al obtener la lista de cedulas en estado EMC, causado por: " + e.getMessage() + result );
        }

    }

}
