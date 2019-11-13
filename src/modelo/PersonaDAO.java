
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PersonaDAO {

  PreparedStatement ps;
  ResultSet rs;
  Connection con;
  Conexion conectar = new Conexion();
  Persona p = new Persona();
  
  public List listar(){
   List<Persona>datos = new ArrayList<>();
   
   try{ 
    
     con = conectar.getConnection();
     ps = con.prepareStatement("select * from persona");
     rs = ps.executeQuery();
     
     while(rs.next()){
    
      Persona p = new Persona();
      p.setId(rs.getInt(1));
      p.setNombre(rs.getString(2));
      p.setCorreo(rs.getString(3));
      p.setTelefono(rs.getString(4));
      datos.add(p);
    }
     
   }catch(Exception e){
    
     JOptionPane.showMessageDialog(null,e.toString());
     
   }
   
   return datos;
  }
  
  public int agregar(Persona per){
 
   int r = 0;
   String sql = "insert into persona(nombre, correo, telefono) values(?, ?, ?)";
   
   try{
   
     con = conectar.getConnection();
     ps = con.prepareStatement(sql);
     ps.setString(1, per.getNombre());
     ps.setString(2, per.getCorreo());
     ps.setString(3, per.getTelefono());
     r = ps.executeUpdate();
     
     if(r == 1){
    
      return 1;
      
    }else{
    
      return 0;     
    }
     
   }catch(Exception e){
  
    JOptionPane.showMessageDialog(null, e.toString());
  }
   
   return r;
   
 }
  
  public int Actualizar(Persona per){
 
   int r = 0;
   String sql = "update persona set nombre = ?, correo = ?, telefono = ? where id = ?";
   
   try{
  
    con = conectar.getConnection();
    ps = con.prepareStatement(sql);
    ps.setString(1, per.getNombre());
    ps.setString(2, per.getCorreo());
    ps.setString(3, per.getTelefono());
    ps.setInt(4, per.getId());
    r = ps.executeUpdate();
    
    if(r == 1){
   
     return 1;
     
   }else{
     
     return 0;
     
   }
    
  }catch(Exception e){
  
    JOptionPane.showMessageDialog(null, e.toString());  
  }
   
   return r;
   
 }
  
  public int Delete(int id){
  
   int r = 0;
   String sql = "delete from persona where id =" + id;
   
   try{
  
    con= conectar.getConnection();
    ps = con.prepareStatement(sql);
    r = ps.executeUpdate();
    
  }catch(Exception e){
  
    JOptionPane.showMessageDialog(null, e.toString());
  }
   
   return r;
 }
}
