package com.co.activos.jadm0067.Utilities;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Implementa todos los métodos que permiten obtener una conexión a la base de datos
 *
 * @author Francisco Javier Rincon
 * @version 1.0
 * @since JDK 1.8
 */
public class OracleConnection {

    private static final String DATASOURCE_NAME = "DS_Intrauser_Acti";
        static Connection conexion;

    /**
     * Obtiene una conexión a la BD a través JDNI configurado en el servidor de aplicaciones
     *
     * @return Conexión de base de datos
   
    public static Connection getConnection() {
        try {
            InitialContext ic = new InitialContext();
            DataSource dt = (DataSource) ic.lookup(DATASOURCE_NAME);
            return dt.getConnection();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    *   */
    
     public static Connection getConnection() {
          try {
               DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
             conexion = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=off)(FAILOVER=on)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.21.147)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.21.147)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.21.147)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=TEST)))", "WFORERO", "abc123");
             
          } catch (Exception e) {
                throw new RuntimeException();
          }
            return conexion;
      }
    

}
