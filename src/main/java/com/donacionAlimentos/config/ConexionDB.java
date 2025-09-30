
package com.donacionAlimentos.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * clase de conexion MySQL
 * @author erwbyel
 */
public class ConexionDB {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    /**
     * codigo que consulta el archivo properties
     * para no ser publicadas en git como codigo
     */
    static {
        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("config/db.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("no se pudo acceder al archivo de propiedades");
            }
            props.load(input);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            
            Class.forName(driver);
            
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    /**
     * metodo que permite conectarse ala bd
     * @return
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
