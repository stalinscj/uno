
package Conexiones;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Clase que permite la conexión a la base de datos, en la cual están alojados los datos de los usuarios registrados en el sistema.
 * @author Stalin
 */
public class ConexionMYSQL {
    
    private String hostname;
    private String baseDeDatos;
    private String url;
    private String usuarioBD;
    private String claveBD;
    
    /**
     * Constructor de la conexion a la base de datos a partir de los parametros dados
     * @param hostname
     * @param baseDeDatos
     * @param usuarioBD 
     */
    public ConexionMYSQL(String hostname, String baseDeDatos, String usuarioBD) {
        this.hostname = hostname;
        this.baseDeDatos = baseDeDatos;
        //this.url = "jdbc:mysql://190.204.96.22/" + baseDeDatos;
        this.url = "jdbc:mysql://" + hostname+"/" + baseDeDatos;
        this.usuarioBD = usuarioBD;
        this.claveBD = "";
    }
    
    /**
     * Establece la conexion a la base de datos y devuelve el enlace a esta.
     * @return link;
     */
    public Connection conectar(){
        
        Connection link = null;
        
        try{
            //Cargamos el driver MYSQL
            Class.forName("org.gjt.mm.mysql.Driver");
            //crear enlace a la base de datos
            link = DriverManager.getConnection(url,usuarioBD,claveBD);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return link;
    }
    
}
