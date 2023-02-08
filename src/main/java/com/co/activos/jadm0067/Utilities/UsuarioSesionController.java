/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Utilities;


import com.co.activos.jadm0067.Utilities.OracleConnection;
import com.co.activos.jadm0067.Entities.UsuarioSesion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.naming.NamingException;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author anaponte
 */
public class UsuarioSesionController {

    private UsuarioSesion usuarioSesion;
    private String sesion;

    public UsuarioSesionController() {

    }

    ;
    
    public UsuarioSesionController(UsuarioSesion usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public boolean buscarUsuarioSesion() {
        Connection conexion = OracleConnection.getConnection();
        String error = null;
        String nombreFil;
        boolean existe = false;
        if (this.sesion == null || this.sesion.equals("")) {
            return existe;
        }
        try {
            String ConsultaSql = null;
            ConsultaSql = "{ call ADM.QB_CONSOLA_BMX.PL_CARGAR_SESSION(?,?,?) }";
            CallableStatement callableStatement;
            callableStatement = conexion.prepareCall(ConsultaSql);
            //Parametros de entrada
            callableStatement.setString(1, this.sesion);
            callableStatement.registerOutParameter(2, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(3, OracleTypes.CURSOR);

            callableStatement.execute();
            error = (String) callableStatement.getString(2);
            ResultSet rs = (ResultSet) callableStatement.getObject(3);

            if (error == null) {
                while (rs.next()) {
                    this.usuarioSesion = new UsuarioSesion(rs.getString("USS_ID"),
                            rs.getString("USS_ID_SESSION"),
                            rs.getString("USS_ID_SESSION_AS"),
                            rs.getString("USS_IP"),
                            rs.getString("USU_USUARIO"),
                            rs.getString("TDC_TD_EPL"),
                            rs.getLong("EPL_ND"),
                            rs.getString("TDC_TD"),
                            rs.getString("EMP_ND"),
                            rs.getString("TDC_TD_FIL"),
                            rs.getString("EMP_ND_FIL"),
                            rs.getString("USS_FECHA_INGRESO"),
                            rs.getString("EMS_ID"),
                            rs.getString("ESA_ID"),
                            rs.getString("DSA_ID"),
                            rs.getString("ID_USUARIO"),
                            rs.getString("CORREO"));

                    if (usuarioSesion.getEmpNdFil() != null) {
                        CallableStatement st = conexion.prepareCall("{? = call rhu.fb_empresa(?,?)}");
                        st.registerOutParameter(1, OracleTypes.VARCHAR);
                        st.setString(2, this.usuarioSesion.getTdcTdFil());
                        st.setDouble(3, Double.parseDouble(this.usuarioSesion.getEmpNdFil()));
                        st.execute();
                        nombreFil = st.getString(1);
                        this.usuarioSesion.setEmpNombreFil(nombreFil);
                    }

                    if (usuarioSesion.getEmpNd() != null) {
                        CallableStatement st = conexion.prepareCall("{? = call rhu.fb_empresa(?,?)}");
                        st.registerOutParameter(1, OracleTypes.VARCHAR);
                        st.setString(2, this.usuarioSesion.getTdcTd());
                        st.setDouble(3, Double.parseDouble(this.usuarioSesion.getEmpNd()));
                        st.execute();
                        nombreFil = st.getString(1);
                        this.usuarioSesion.setEmpNombrePal(nombreFil);
                    }
                    existe = true;
                }
            }
            //Se ingresan los primeros 3 campos en la respectivas Variables
            callableStatement.close();

        } catch (Exception edb) {
            edb.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (Exception edb2) {
                edb2.printStackTrace();
            }
        }

        return existe;
    }

    public UsuarioSesion getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesion usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
}
