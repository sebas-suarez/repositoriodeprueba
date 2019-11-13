
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
   
  String url = "jdbc:mysql://localhost:3306/bd_ejemplo";
  String user = "root" , pass = "1234";
  Connection con;
  
  public Connection getConnection(){
    try{
    
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(url, user, pass);
      
    }catch(Exception e){
      
      JOptionPane.showMessageDialog(null,e.toString());    
    }
    
    return con;
  }
}
