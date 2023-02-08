package com.co.activos.jadm0067.Repositories;

import com.co.activos.jadm0067.Entities.DataErpAzDto;
import oracle.jdbc.OracleTypes;

import javax.ejb.Stateless;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.LibroIngresoDocumentoDto;
import com.co.activos.jadm0067.Entities.TipoDocumentoDetalleDto;
import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.*;
import com.co.activos.jadm0067.Utilities.OracleConnection;

/**
 * Implementa todos los metodos de acceso y manipulacion de datos de la entidad RHU.LIBROINGRESO_DOCUMENTO
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
@Stateless
public class DocumentsRepoImpl implements DocumentsRepo {

    @Override
    public DatabaseResultDto<?> synchronizedDocuments(Long libConsecutivo) {
        try (Connection connection = OracleConnection.getConnection()) {
            String query = "call ADM.QB_APP_JADM0065_LOGIC.pl_sincroniza_libro_docs(?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(query)) {
                callableStatement.setLong("nmLibConsecutivo", libConsecutivo);
                callableStatement.registerOutParameter("vcEstadoProceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcMensajeProceso", OracleTypes.VARCHAR);
                callableStatement.execute();
                String status = callableStatement.getString("vcEstadoProceso");
                if (!status.equals("OK")) {
                    String message = callableStatement.getString("vcMensajeProceso");
                    return new DatabaseResultDto<>(ERROR, message);
                }
                return new DatabaseResultDto<>(SUCCESS, "Consulta exitosa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(ERROR, "Error SQL al sincronizar los documentos para este libro, causado por: " + e.getMessage());
        }
    }

    private DatabaseResultDto<LibroIngresoDocumentoDto> getAllDocuments(Long libConsecutivo, String option) {
        try (Connection connection = OracleConnection.getConnection()) {
            String query = "call ADM.QB_APP_JADM0065_LOGIC.PL_OBTENER_DOCUMENTOS(?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(query)) {
                callableStatement.setLong("nmLibConsecutivo", libConsecutivo);
                callableStatement.setString("vcOption", option);
                callableStatement.registerOutParameter("curData", OracleTypes.CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject("curData");
                List<LibroIngresoDocumentoDto> documents = new ArrayList<>();
                while (resultSet.next()) {
                    LibroIngresoDocumentoDto documentoDto = new LibroIngresoDocumentoDto();
                    documentoDto.setId(resultSet.getLong("ldo_id"));
                    documentoDto.setIdPadre(resultSet.getLong("ldo_id_padre"));
                    documentoDto.setLdoEstado(resultSet.getString("ldo_estado_mesa"));
                    documentoDto.setLdoTemporalEstado(documentoDto.getLdoEstadoNumerico(documentoDto.getLdoEstado()));
                    DataErpAzDto dataErpAz = new DataErpAzDto();
                    dataErpAz.setId(resultSet.getLong("dea_codigo"));
                    dataErpAz.setTxpCodigo(resultSet.getLong("txp_codigo"));
                    dataErpAz.setAzdCodigo(resultSet.getLong("azd_codigo"));
                    dataErpAz.setAzdCodigoCli(resultSet.getString("azd_codigo_cli"));
                    documentoDto.setDataErpAz(dataErpAz);
                    TipoDocumentoDetalleDto tipoDocumentoDetalle = new TipoDocumentoDetalleDto();
                    tipoDocumentoDetalle.setId(resultSet.getLong("tdd_id"));
                    tipoDocumentoDetalle.setTddNombreDocumento(resultSet.getString("tdd_nombre_documento"));
                    documentoDto.setTipoDocumentoDetalle(tipoDocumentoDetalle);
                    documentoDto.setNombreDocumento(resultSet.getString("nombre_documento"));
                    documents.add(documentoDto);
                }
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta existosa", documents);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(ERROR, "Error SQL al obtener los documentos asociados a este libro de ingreso, causado por: " + e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<LibroIngresoDocumentoDto> getAllDocumentsParent(Long libConsecutivo) {
        return getAllDocuments(libConsecutivo, "PARENT");
    }

    @Override
    public DatabaseResultDto<LibroIngresoDocumentoDto> getAllDocumentsChild(Long libConsecutivo) {
        return getAllDocuments(libConsecutivo, "CHILD");
    }

    @Override
    public DatabaseResultDto<LibroIngresoDocumentoDto> updateDocument(LibroIngresoDocumentoDto documento) {
        try (Connection connection = OracleConnection.getConnection()) {
            String query = "call ADM.qb_JADM0067_mesa_contratos.pl_actualizar_documento(?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(query)) {
                callableStatement.setLong("nmLdoId", documento.getId());
                callableStatement.setString("vcLdoEstado", documento.getLdoEstadoAlfanumerico(documento.getLdoTemporalEstado()));
                callableStatement.executeUpdate();
                return new DatabaseResultDto<>(SUCCESS, "Consulta exitosa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(ERROR, "Error SQL al actualizar este documento, causado por: " + e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<String> getUrlDocument(String usuario, String ipUsuario, String azdCodigoCli) {
        try (Connection connection = OracleConnection.getConnection()) {
            String query = "{? = call ADM.QB_DATOS_TAXONOMIA.FL_URL_OBTENER_DOCUMENTO_AZ(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(query)) {
                callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
                callableStatement.setString(2, usuario);
                callableStatement.setString(3, ipUsuario);
                callableStatement.setString(4, azdCodigoCli);
                callableStatement.executeUpdate();
                String url = callableStatement.getString(1);
                if (url == null || url.isEmpty()) {
                    return new DatabaseResultDto<>(ERROR, "La URL para visualizar este documento es nula", url);
                }
                return new DatabaseResultDto<>(SUCCESS, "Consulta exitosa", url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new DatabaseResultDto<>(ERROR, "Error SQL al obtener la URL para este documento, causado por: " + e.getMessage());
        }
    }
}
