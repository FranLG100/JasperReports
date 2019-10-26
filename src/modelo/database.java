package modelo;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Mouse
 */
public class database {
 /* DATOS PARA LA CONEXION */
  private ArrayList<String> partes=new ArrayList<String>();
  /** base de datos por defecto es test*/
  private String db = "falorente_profinal";
  /** usuario */
  private String user = "falor_fralg";
  /** contraseña de MySql*/
  private String password = "fralg100@gmail.com";
  /** Cadena de conexion */
  private String url = "jdbc:mysql://falorente.salesianas.es/falorente_profinal";
  /** variable para trabajar con la conexion a la base de datos */
  private Connection miConexion = null;
  
   /** Constructor de clase */
   public database(){
        this.url = "jdbc:mysql://falorente.salesianas.es/"+this.db;
       try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         miConexion = DriverManager.getConnection( "jdbc:mysql://falorente.salesianas.es/falorente_clientes", "falor_fralg" , "fralg100@gmail.com" );         
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
   }


   public Connection getConexion()
   {
	
    return this.miConexion;
   }

}