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
import com.co.activos.jadm0067.Utilities.OracleConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import oracle.jdbc.OracleType;
import oracle.jdbc.internal.OracleTypes;

/**
 * Implementa todos los metodos de acceso y manipulacion de datos para la logica
 * de Mesa de contratos.
 *
 * @author desierra
 */
@Stateless
public class MesaContratoRepoImpl implements MesaContratoRepo {

    @Override
    public DatabaseResultDto<CedulaPeritaje> consultarCedulasPeritaje(String vcstdoestado) {
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_consulta_cedulas(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                // Parametros de entrada
                callableStatement.setString("vcstdoestado", vcstdoestado);
                // Parametros de Salida
                callableStatement.registerOutParameter("csconsulta", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                callableStatement.execute();

                ResultSet resulSet = (ResultSet) callableStatement.getObject("csconsulta");
                List<CedulaPeritaje> cedulas = new ArrayList<>();
                while (resulSet.next()) {
                    CedulaPeritaje cedulaPeritaje = new CedulaPeritaje();
                    cedulaPeritaje.setTdc_td_epl(resulSet.getString("TDC_TD_EPL"));
                    cedulaPeritaje.setEpl_nd(resulSet.getString("EPL_ND"));
                    cedulaPeritaje.setLib_consecutivo(resulSet.getLong("LIB_CONSECUTIVO"));
                    cedulas.add(cedulaPeritaje);
                }

                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", cedulas);

            }
        } catch (Exception e) {
            e.printStackTrace();

            return new DatabaseResultDto<>(DatabaseResultStatus.ERROR, "Error al traera las cedulas para peritaje causado por : " + e.getMessage());

        }

    }

    @Override
    public DatabaseResultDto<InformacionPeritaje> consultarDatosPeritaje(Long nmordencto) {

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_consulta_datos(?,?,?,?)";
            try (CallableStatement callbableStatement = connection.prepareCall(consulta)) {

                //Parametros Entrada
                callbableStatement.setLong("nmordencto", nmordencto);

                //Parametros de Salida
                callbableStatement.registerOutParameter("csconsulta", OracleTypes.CURSOR);
                callbableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callbableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                callbableStatement.execute();

                ResultSet resultSet = (ResultSet) callbableStatement.getObject("csconsulta");

                InformacionPeritaje informacionPeritaje = new InformacionPeritaje();
                while (resultSet.next()) {

                    informacionPeritaje.setEpl_nd(resultSet.getString("epl_nd"));
                    informacionPeritaje.setTdc_td_epl(resultSet.getString("tdc_td_epl"));
                    informacionPeritaje.setEmp_nd(resultSet.getString("emp_nd"));
                    informacionPeritaje.setTdc_td(resultSet.getString("tdc_td"));
                    informacionPeritaje.setEmp_nom(resultSet.getString("emp_nom"));
                    informacionPeritaje.setLib_consecutivo(resultSet.getString("Lib_CONSECUTIVO"));
                    informacionPeritaje.setNombre_empleado(resultSet.getString("nombre_empleado"));
                    informacionPeritaje.setCargo_empleado(resultSet.getString("cargo_empleado"));
                    informacionPeritaje.setCorreo_empleado(resultSet.getString("correo_empleado"));
                    informacionPeritaje.setNumero_contrato(resultSet.getLong("cto_numero"));
                    

                }
                callbableStatement.close();

                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", informacionPeritaje);
            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, "Error al consultar la informacion para la cedula " + nmordencto + " causada por : " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<?> guardarCedulaPeritaje(String vcresponsable, String vcestadoResponsable, Integer nmlibconsecutivo) {
        String resultado = "";
        String estadoConsulta = "";

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_almacenar_cedula_peritaje(?,?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setString("vcresponsable", vcresponsable);
                callableStatement.setString("vcestadoResponsable", vcestadoResponsable);
                callableStatement.setInt("nmlibconsecutivo", nmlibconsecutivo);

                //Parametros de Salida
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);
                
                

                callableStatement.execute();

                resultado = (String) callableStatement.getObject("vcmensajeproceso");
                estadoConsulta = (String) callableStatement.getObject("vcestadoproceso");
                callableStatement.close();

            }
            return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, estadoConsulta, resultado);
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, "Error Almacenando en : guardarCedulaPeritaje, causado por :" + e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<?> consultarEstadoAnalista(String vcresponsable) {
        Long numLibroIngreso = 0l;
        String estadoProceso = "";

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_consultar_estado_peritaje(?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setString("vcresponsable", vcresponsable);

                //Parametros de salida
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("nmlibconsecutivo", OracleTypes.NUMBER);

                callableStatement.execute();

                numLibroIngreso = (Long) callableStatement.getLong("nmlibconsecutivo");
                estadoProceso = (String) callableStatement.getString("vcestadoproceso");

                callableStatement.close();
            }

            return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, estadoProceso, numLibroIngreso);

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<Empresa> consultarEmpresa(String VCTDC_TD, String VCEMP_ND) {
        String estadoProceso = "";
        String mensajeProceso = "";

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_EMPRESA(?,?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                // parametros de entrada 
                callableStatement.setString("VCTDC_TD", VCTDC_TD);
                callableStatement.setString("VCEMP_ND", VCEMP_ND);

                // parametos de salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");
                Empresa empresa = new Empresa();
                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");

                    while (resultSet.next()) {
                        empresa.setEMP_ACU_CODIGO(resultSet.getInt("EMP_ACU_CODIGO"));
                        empresa.setTDC_TD_FIL(resultSet.getString("TDC_TD_FIL"));
                        empresa.setEMP_ND_FIL(resultSet.getInt("EMP_ND_FIL"));
                        empresa.setEMP_DV(resultSet.getString("EMP_DV"));
                        empresa.setEMP_RAZON_SOCIAL(resultSet.getString("EMP_RAZON_SOCIAL"));
                        empresa.setEMP_CIUDAD(resultSet.getString("EMP_CIUDAD"));
                        empresa.setEMP_TELEFONOS(resultSet.getString("EMP_TELEFONOS"));
                        empresa.setEMP_CELULAR(resultSet.getString("EMP_CELULAR"));
                        empresa.setEMP_DIRECCION_PRINCIPAL(resultSet.getString("EMP_DIRECCION_PRINCIPAL"));
                        empresa.setEMP_EMAIL(resultSet.getString("EMP_EMAIL"));
                        empresa.setEMP_TIPO_OPERACION(resultSet.getString("EMP_TIPO_OPERACION"));
                        empresa.setEMP_MEDIO_CONSULTA(resultSet.getString("EMP_MEDIO_CONSULTA"));
                        empresa.setEMP_AGRUPA_EMPRESARIAL(resultSet.getString("EMP_AGRUPA_EMPRESARIAL"));
                        empresa.setEMP_COD_AGRUPA_EMPRESARIAL(resultSet.getString("EMP_COD_AGRUPA_EMPRESARIAL"));
                        empresa.setEMP_CODIGO_CIU(resultSet.getString("EMP_CODIGO_CIU"));
                        empresa.setEMP_ACTIVIDAD_ECONOMICA(resultSet.getString("EMP_ACTIVIDAD_ECONOMICA"));
                        empresa.setEMP_REPRESENTANTE_LEGAL(resultSet.getString("EMP_REPRESENTANTE_LEGAL"));
                        empresa.setEMP_DOC_REPRESENTANTE_LEGAL(resultSet.getString("EMP_DOC_REPRESENTANTE_LEGAL"));
                        empresa.setEMP_AUTORIDAD_RRHH(resultSet.getString("EMP_AUTORIDAD_RRHH"));
                        empresa.setEMP_CARGO_AUTORIDAD_RRHH(resultSet.getString("EMP_CARGO_AUTORIDAD_RRHH"));
                        empresa.setEMP_CLI_SOLICITA_CELULAR(resultSet.getString("EMP_CLI_SOLICITA_CELULAR"));
                        empresa.setEMP_ENTREGA_PROFESIONGRAMA(resultSet.getString("EMP_ENTREGA_PROFESIONGRAMA"));
                        empresa.setEMP_CIU_PRESENCIA_FISICA(resultSet.getString("EMP_CIU_PRESENCIA_FISICA"));
                        empresa.setEMP_MANEJO_IND_ESPECIALES(resultSet.getString("EMP_MANEJO_IND_ESPECIALES"));
                        empresa.setEMP_CLI_PER_FUN_ARRA_OPERA(resultSet.getString("EMP_CLI_PER_FUN_ARRA_OPERA"));
                        empresa.setEMP_CLI_MOD_COMP_ORGA(resultSet.getString("EMP_CLI_MOD_COMP_ORGA"));
                        empresa.setEMP_CLI_ENT_COMP_ORG_INI_OPER(resultSet.getString("EMP_CLI_ENT_COMP_ORG_INI_OPER"));
                        empresa.setEMP_CLI_COMP_GEN_CAR(resultSet.getString("EMP_CLI_COMP_GEN_CAR"));
                        empresa.setEMP_APLIC_PARAM_OPERACION(resultSet.getString("EMP_APLIC_PARAM_OPERACION"));
                        empresa.setAUD_FECHA(resultSet.getString("AUD_FECHA"));
                        empresa.setAUD_USUARIO(resultSet.getString("AUD_USUARIO"));
                        empresa.setCOD_POSTAL(resultSet.getString("COD_POSTAL"));
                        empresa.setNUM_MATRICULA_MERCANTIL(resultSet.getString("NUM_MATRICULA_MERCANTIL"));

                    }
                } else {
                    empresa = new Empresa();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", empresa);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());

        }
    }

    @Override
    public DatabaseResultDto<ResponsableInterno> consultarResponsableInternoEmpresa(Integer NMEMP_ACU_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<ResponsableInterno> responsable;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_REPONSABLES(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);

                //Parametros de salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    responsable = new ArrayList<>();
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    while (resultSet.next()) {
                        ResponsableInterno responsableInterno = new ResponsableInterno();
                        responsableInterno.setRIN_MAIL(resultSet.getString("RIN_MAIL"));
                        responsableInterno.setRIN_NOMBRE(resultSet.getString("RIN_NOMBRE"));
                        responsableInterno.setRIN_TELEFONO(resultSet.getString("RIN_TELEFONO"));
                        responsableInterno.setTCA_DESCRIPCION(resultSet.getString("TCA_DESCRIPCION"));
                        responsable.add(responsableInterno);
                    }

                } else {
                    responsable = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", responsable);
            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());

        }

    }

    @Override
    public DatabaseResultDto<SucursalEmpresa> consultarSucursalEmpresa(Integer NMEMP_ACU_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<SucursalEmpresa> sucursal;

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_SUCURSAL_EMPRESA(?,?,?,?) ";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);

                //Parametros de salida
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    sucursal = new ArrayList<>();
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");

                    while (resultSet.next()) {
                        SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
                        sucursalEmpresa.setSUE_CODIGO(resultSet.getString("SUE_CODIGO"));
                        sucursalEmpresa.setSUE_NOMBRE(resultSet.getString("SUE_NOMBRE"));
                        sucursalEmpresa.setSUE_DIRECCION(resultSet.getString("SUE_DIRECCION"));
                        sucursal.add(sucursalEmpresa);
                    }

                } else {
                    sucursal = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", sucursal);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<ContactoSucursal> consultarContactoSucursal(Integer NMSUE_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<ContactoSucursal> contactoSucursal;

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_CONTACTO_SUCURSAL(?,?,?,?) ";

            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Entrada
                callableStatement.setInt("NMSUE_CODIGO", NMSUE_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    contactoSucursal = new ArrayList<>();
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");

                    while (resultSet.next()) {
                        ContactoSucursal sucursal = new ContactoSucursal();
                        sucursal.setTIC_CODIGO(resultSet.getString("TIC_CODIGO"));
                        sucursal.setCSU_NOMBRE(resultSet.getString("CSU_NOMBRE"));
                        sucursal.setCSU_DESCRIPCION(resultSet.getString("CSU_DESCRIPCION"));
                        sucursal.setSUE_CODIGO(resultSet.getString("SUE_CODIGO"));
                        contactoSucursal.add(sucursal);
                    }

                } else {
                    contactoSucursal = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", contactoSucursal);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<EmpresaPrincipal> consultarEmpresaPrincipal(Integer NMEMP_ACU_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<EmpresaPrincipal> empresaPrincipal;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_EMPRESA_PRINCIPAL(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);

                // Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();
                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    empresaPrincipal = new ArrayList<>();
                    while (resultSet.next()) {
                        EmpresaPrincipal empresaPrin = new EmpresaPrincipal();
                        empresaPrin.setEEMPRESA_PRINCIPAL(resultSet.getString("EMPRESA"));
                        empresaPrincipal.add(empresaPrin);
                    }
                } else {
                    empresaPrincipal = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", empresaPrincipal);
            }
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<AgrupacionEmpresa> consultarAgrupacionEmpresa(Integer NMEMP_ACU_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<AgrupacionEmpresa> agrupacionEmpresas;

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_AGRUPACIONES(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    agrupacionEmpresas = new ArrayList<>();

                    while (resultSet.next()) {
                        AgrupacionEmpresa agrupacionEmpresa = new AgrupacionEmpresa();
                        agrupacionEmpresa.setAGA_CODIGO(resultSet.getInt("AGA_CODIGO"));
                        agrupacionEmpresa.setAGA_DESCRIPCION(resultSet.getString("AGA_DESCRIPCION"));
                        agrupacionEmpresa.setPAC_CODIGO(resultSet.getString("PAC_CODIGO"));
                        agrupacionEmpresa.setAOR_CODIGO(resultSet.getInt("AOR_CODIGO"));
                        agrupacionEmpresas.add(agrupacionEmpresa);
                    }

                } else {
                    agrupacionEmpresas = new ArrayList<>();
                }

                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", agrupacionEmpresas);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<ResponsableEmpresa> consultarResponsableEmpresa(Integer NMEMP_ACU_CODIGO, Integer NMAOR_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<ResponsableEmpresa> responsableEmpresas;

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_RESPONSABLES_EMP(?,?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);
                callableStatement.setInt("NMAOR_CODIGO", NMAOR_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    responsableEmpresas = new ArrayList<>();
                    while (resultSet.next()) {
                        ResponsableEmpresa responsableEmpresa = new ResponsableEmpresa();
                        responsableEmpresa.setRCL_CARGO(resultSet.getString("RCL_CARGO"));
                        responsableEmpresa.setRCL_CODIGO(resultSet.getInt("RCL_CODIGO"));
                        responsableEmpresa.setRCL_NOMBRE(resultSet.getString("RCL_NOMBRE"));
                        responsableEmpresa.setRCL_OBSERVACION(resultSet.getString("RCL_OBSERVACION"));
                        responsableEmpresa.setTRS_CODIGO(resultSet.getInt("TRS_CODIGO"));
                        responsableEmpresa.setTRS_TIPO(resultSet.getString("TRS_TIPO"));
                        responsableEmpresas.add(responsableEmpresa);
                    }
                } else {
                    responsableEmpresas = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", responsableEmpresas);
            }
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<ObservacionesGenerales> consultarObservacionesGenerales(Integer NMEMP_ACU_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<ObservacionesGenerales> observacionesGeneraleses;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_OBSERVACIONES_GEN(?,?,?,?)";

            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    observacionesGeneraleses = new ArrayList<>();
                    while (resultSet.next()) {
                        ObservacionesGenerales generales = new ObservacionesGenerales();
                        generales.setAUD_FECHA(resultSet.getString("AUD_FECHA"));
                        generales.setAUD_USUARIO(resultSet.getString("AUD_USUARIO"));
                        generales.setOBG_DETALLE(resultSet.getString("OBG_DETALLE"));
                        generales.setOBS_CODIGO(resultSet.getInt("OBS_CODIGO"));
                        observacionesGeneraleses.add(generales);
                    }

                } else {
                    observacionesGeneraleses = new ArrayList<>();
                }

                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", observacionesGeneraleses);
            }

        } catch (Exception e) {

            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<AcuerdoPorAgrupacion> consultarAcuerdoPorAgrupacion(Integer NMEMP_ACU_CODIGO, Integer NMAGA_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<AcuerdoPorAgrupacion> acuerdoPorAgrupacions;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_ACUERDO_POR_AGRUP(?,?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setInt("NMEMP_ACU_CODIGO", NMEMP_ACU_CODIGO);
                callableStatement.setInt("NMAGA_CODIGO", NMAGA_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    acuerdoPorAgrupacions = new ArrayList<>();
                    while (resultSet.next()) {
                        AcuerdoPorAgrupacion acuerdoPorAgrupacion = new AcuerdoPorAgrupacion();
                        acuerdoPorAgrupacion.setACC_CODIGO(resultSet.getInt("ACC_CODIGO"));
                        acuerdoPorAgrupacion.setACC_OBSERVACION(resultSet.getString("ACC_OBSERVACION"));
                        acuerdoPorAgrupacion.setACC_TIPO_DETALLE(resultSet.getString("ACC_TIPO_DETALLE"));
                        acuerdoPorAgrupacion.setACC_VALOR_ATRIBUTO(resultSet.getString("ACC_VALOR_ATRIBUTO"));
                        acuerdoPorAgrupacion.setATR_NOMBRE(resultSet.getString("ATR_NOMBRE"));
                        acuerdoPorAgrupacions.add(acuerdoPorAgrupacion);
                    }
                } else {

                    acuerdoPorAgrupacions = new ArrayList<>();
                }

                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", acuerdoPorAgrupacions);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<AgrupacionAtributo> consultarAgrupacionAtributo(Integer NMACC_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<AgrupacionAtributo> agrupacionAtributos;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_VALOR_ATRIBUTO(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada
                callableStatement.setInt("NMACC_CODIGO", NMACC_CODIGO);

                //Parametros de salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();
                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    agrupacionAtributos = new ArrayList<>();

                    while (resultSet.next()) {

                        AgrupacionAtributo agrupacionAtributo = new AgrupacionAtributo();
                        agrupacionAtributo.setATR_NOMBRE(resultSet.getString("ATR_NOMBRE"));
                        agrupacionAtributo.setATR_NOMBRE_DETALLE(resultSet.getString("ATR_NOMBRE_DETALLE"));
                        agrupacionAtributo.setDAC_CODIGO(resultSet.getInt("DAC_CODIGO"));
                        agrupacionAtributo.setDAC_DETALLE_DESCRIPCION(resultSet.getString("DAC_DETALLE_DESCRIPCION"));
                        agrupacionAtributo.setDAC_OBSERVACION(resultSet.getString("DAC_OBSERVACION"));
                        agrupacionAtributo.setDAC_VALOR_ATR_DETALLE(resultSet.getString("DAC_VALOR_ATR_DETALLE"));

                        agrupacionAtributos.add(agrupacionAtributo);

                    }

                } else {
                    agrupacionAtributos = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", agrupacionAtributos);

            }
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<NotaPorAgrupacionEmpresa> consultarNotaPorAgrupacion(Integer NMAGA_CODIGO) {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<NotaPorAgrupacionEmpresa> agrupacionEmpresas;

        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ACR.QB_ACUERDO_EMPRESA.PL_CONSULTAR_NOTA_AGRUPACION(?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada 
                callableStatement.setInt("NMAGA_CODIGO", NMAGA_CODIGO);

                //Parametros de Salida
                callableStatement.registerOutParameter("CSCONSULTA", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callableStatement.getString("VCMENSAJEPROCESO");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("CSCONSULTA");
                    agrupacionEmpresas = new ArrayList<>();

                    while (resultSet.next()) {
                        NotaPorAgrupacionEmpresa agrupacionEmpresa = new NotaPorAgrupacionEmpresa();
                        agrupacionEmpresa.setAUD_FECHA(resultSet.getString("AUD_FECHA"));
                        agrupacionEmpresa.setAUD_USUARIO(resultSet.getString("AUD_USUARIO"));
                        agrupacionEmpresa.setNOA_CODIGO(resultSet.getInt("NOA_CODIGO"));
                        agrupacionEmpresa.setNOA_DESCRIPCION(resultSet.getString("NOA_DESCRIPCION"));
                        agrupacionEmpresa.setNOA_ORDEN(resultSet.getInt("NOA_ORDEN"));
                        agrupacionEmpresas.add(agrupacionEmpresa);
                    }

                } else {
                    agrupacionEmpresas = new ArrayList<>();
                }

                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", agrupacionEmpresas);

            }
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<Causales> consultarCausales() {
        String estadoProceso = "";
        String mensajeProceso = "";
        List<Causales> causaleses;

        try (Connection connection = OracleConnection.getConnection()) {

            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_consultar_causales(?,?,?)";

            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de salida
                callableStatement.registerOutParameter("csconsulta", OracleTypes.CURSOR);
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                callableStatement.execute();

                estadoProceso = callableStatement.getString("vcestadoproceso");
                mensajeProceso = callableStatement.getString("vcmensajeproceso");

                if (estadoProceso.equals("S")) {
                    ResultSet resultSet = (ResultSet) callableStatement.getObject("csconsulta");
                    causaleses = new ArrayList<>();
                    while (resultSet.next()) {
                        Causales c = new Causales();
                        c.setCodigo_causal(resultSet.getInt("CODIGO_CAUSAL"));
                        c.setDescripcion_causal(resultSet.getString("DESCRIPCION_CAUSAL"));
                        c.setEcl_secuencia(resultSet.getInt("ECL_SECUENCIA"));
                        causaleses.add(c);
                    }
                } else {
                    causaleses = new ArrayList<>();
                }
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", causaleses);

            }

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<?> finalizarProceso(Long NMLIBCONSECUTIVO, String VCSTDOESTADO, String VCOBSERVACION, String VCUSUARIO, Integer NMCAUSAL) {
        String estadoProceso = "";
        String mensajeProceso = "";
        try (Connection connection = OracleConnection.getConnection()) {

                String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_finalizar_proceso(?,?,?,?,?,?,?)";

            try (CallableStatement callbleStatement = connection.prepareCall(consulta)) {

                //Parametros de entrada 
                callbleStatement.setLong("NMLIBCONSECUTIVO", NMLIBCONSECUTIVO);
                callbleStatement.setString("VCSTDOESTADO", VCSTDOESTADO);
                callbleStatement.setString("VCOBSERVACION", VCOBSERVACION);
                callbleStatement.setString("VCUSUARIO", VCUSUARIO);
                callbleStatement.setInt("NMCAUSAL", NMCAUSAL);

                //Parametros de Salida
                callbleStatement.registerOutParameter("VCESTADOPROCESO", OracleTypes.VARCHAR);
                callbleStatement.registerOutParameter("VCMENSAJEPROCESO", OracleTypes.VARCHAR);

                callbleStatement.execute();

                estadoProceso = callbleStatement.getString("VCESTADOPROCESO");
                mensajeProceso = callbleStatement.getString("VCMENSAJEPROCESO");

                callbleStatement.close();
            }
            return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, estadoProceso);

        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, estadoProceso + mensajeProceso + " " + e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<?> consultarInformacionAnalista(String TDC_TD_EPL, Long EPL_ND, String Columna) {
        String nombre;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "{? = call RHU.FB_EMPLEADO_COLUMNA(?,?,?)}";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Salida
                callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);

                //Parametros de Entrada
                callableStatement.setString(2, TDC_TD_EPL);
                callableStatement.setLong(3, EPL_ND);
                callableStatement.setString(4, Columna);
                callableStatement.executeQuery();

                nombre = callableStatement.getString(1);
                callableStatement.close();
                return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, nombre);
            }
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, e.getMessage());
        }

    }

    @Override
    public DatabaseResultDto<EstadisticasAnalista> consultarEstadisticaAnalista(String vcresponsable) {

        EstadisticasAnalista analista;
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call ADM.qb_jadm0067_mesa_contratos.pl_estadistica_analista(?,?,?,?,?,?)";
            try (CallableStatement call = connection.prepareCall(consulta)) {

                //Parametros de entrada
                call.setString("vcresponsable", vcresponsable);

                //Parametros de salida
                call.registerOutParameter("nmaprobados", OracleTypes.NUMBER);
                call.registerOutParameter("nmrechazados", OracleTypes.NUMBER);
                call.registerOutParameter("nmtotal", OracleTypes.NUMBER);
                call.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                call.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                call.execute();

                analista = new EstadisticasAnalista();
                analista.setCandidatosAprobados(call.getInt("nmaprobados"));
                analista.setCandidatosRechazados(call.getInt("nmrechazados"));
                analista.setTotalCandidatos(call.getInt("nmtotal"));

                call.close();

            }
            return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Exitoso", analista);
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<String>consultarObservacionCedulaPeritaje(Long NMLIBCONSECUTIVO, String VCSTDOESTADO) {
        String resultado = "";
        try (Connection connection = OracleConnection.getConnection()) {
            String consulta = "call  ADM.qb_jadm0067_mesa_contratos.pl_consultar_observacion(?,?,?,?,?)";
            try (CallableStatement callableStatement = connection.prepareCall(consulta)) {

                //Parametros de Entrada
                callableStatement.setLong("NMLIBCONSECUTIVO", NMLIBCONSECUTIVO);
                callableStatement.setString("VCSTDOESTADO", VCSTDOESTADO);

                //Parammetros de Salida
                callableStatement.registerOutParameter("VCRESULTADO", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcestadoproceso", OracleTypes.VARCHAR);
                callableStatement.registerOutParameter("vcmensajeproceso", OracleTypes.VARCHAR);

                callableStatement.execute();

                resultado = callableStatement.getString("VCRESULTADO");
                
                callableStatement.close();

            }
            return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, resultado);
        } catch (Exception e) {
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, e.getMessage());
        }
    }

    @Override
    public DatabaseResultDto<DatosTaxonomia> obtenerCarpetaDeContratacion(Long NMLIBCONSECUTIVO, String flujoPrincipal , String tpProceso) {
        DatosTaxonomia datosTaxonomia = new DatosTaxonomia();
        try (Connection connection = OracleConnection.getConnection() ){
            String consulta = "call ADM.QB_DATOS_TAXONOMIA.PL_OBTENER_LIBRO_PROCESO(?,?,?,?,?,?,?)";
            try(CallableStatement call = connection.prepareCall(consulta)){
            
                //Parametros de Entrada.
                call.setLong("nmLibConsecutivo", NMLIBCONSECUTIVO);
                call.setString("vcTipoFlujoPrincipal", flujoPrincipal);
                call.setString("vcProceso", tpProceso);
                
                //Parametros de Salida
                call.registerOutParameter("nmDeaCodigo", OracleTypes.VARCHAR);
                call.registerOutParameter("vcAzCodigoCli", OracleTypes.VARCHAR);
                call.registerOutParameter("vcEstadoProceso", OracleTypes.VARCHAR);
                call.registerOutParameter("vcMensajeProceso", OracleTypes.VARCHAR);
                
                call.execute();
                
                if(call.getString("vcEstadoProceso").equals("OK")){
                  datosTaxonomia.setAzCodigoCli(call.getString("vcAzCodigoCli"));
                  datosTaxonomia.setDeaCodigo(call.getString("nmDeaCodigo"));
                }
                
                call.close();

            }
             return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta Exitosa", datosTaxonomia);
             
        } catch (Exception e) {
             return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, e.getMessage());
            
        }
    }

    @Override
    public DatabaseResultDto<?> crearAuditoria(String log, DatabaseResultStatus tipo, String aplicativo, String transaccion, String estadoTransaccion) {
          String resultado = "";
        try ( Connection connection = OracleConnection.getConnection()) {
            String consulta = "call adm.qb_JADM0067_mesa_contratos.pl_insertar_auditoria(?,?,?,?,?,?,?)";
            try ( CallableStatement call = connection.prepareCall(consulta)) {

                //Parametros  de entrada
                call.setString("VCLOG", log);
                call.setString("VCTYPE_ERROR", String.valueOf(tipo));
                call.setString("VCAPLICATIVO", aplicativo);
                call.setString("VCTRANSACCION", transaccion);
                call.setString("VCESTADOTRANSACCION", estadoTransaccion);

                //Parametros de Salida
                call.registerOutParameter("vcestadoproceso", OracleType.VARCHAR2);
                call.registerOutParameter("vcmensajeproceso", OracleType.VARCHAR2);
                call.execute();
                call.close();
      }
      return new DatabaseResultDto<>(DatabaseResultStatus.SUCCESS, "Consulta realizada correctamente", resultado);
 
      }catch (Exception e){
            return new DatabaseResultDto<>(DatabaseResultStatus.WARNING, "Error no controlado en CrearDirectorioDao.obtenerTipoDocumentosDet, causado por: " + e.getMessage());
      }
    }

}
