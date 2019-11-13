package controlador;

import modelo.Persona;
import modelo.PersonaDAO;
import vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(Vista v) {

        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnBorrar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evnt) {
                if (evnt.getClickCount() == 1) {
                    editar();
                }
            }
        }
        );

    }

    @Override

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnListar) {

            limpiarTabla();
            listar(vista.tabla);
            nuevo();
        }
        if (e.getSource() == vista.btnNuevo) {

            nuevo();
        }

        if (e.getSource() == vista.btnGuardar) {

            if (vista.txtId.getText().isEmpty()) {
                agregar();
            } else {
                actualizar();
            }
            listar(vista.tabla);
            nuevo();
        }

        if (e.getSource() == vista.btnBorrar) {

            borrar();
            listar(vista.tabla);
            nuevo();
        }
    }

    void nuevo() {

        vista.txtId.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
        vista.txtNombre.requestFocus();
    }

    public void editar() {

        int fila = vista.tabla.getSelectedRow();
        if (fila == - 1) {

            JOptionPane.showMessageDialog(vista, "para editar primero debe seleccionar una fila de la tabla.");

        } else {

            int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
            String nom = (String) vista.tabla.getValueAt(fila, 1);
            String correo = (String) vista.tabla.getValueAt(fila, 2);
            String tel = (String) vista.tabla.getValueAt(fila, 3);
            vista.txtId.setText("" + id);
            vista.txtNombre.setText(nom);
            vista.txtCorreo.setText(correo);
            vista.txtTelefono.setText(tel);
        }
    }

    public void borrar() {

        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {

            JOptionPane.showMessageDialog(vista, "para editar primero debe seleccionar una fila de la tabla.");

        } else {

            int input = JOptionPane.showConfirmDialog(null,
                    "¿Borrar a " + vista.tabla.getValueAt(fila, 1).toString() + "?",
                    "Eliminar", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
                dao.Delete(id);
                System.out.println("Se ha borrado la persona con exito (" + id + ")");
            }
        }
        limpiarTabla();
    }

    public void agregar() {
        String nombre = vista.txtNombre.getText();
        String correo = vista.txtCorreo.getText();
        String tel = vista.txtTelefono.getText();
        p.setNombre(nombre);
        p.setCorreo(correo);
        p.setTelefono(tel);
        int r = dao.agregar(p);

        if (r == 1) {

            System.out.println("Se ha agregado la persona con exito.");

        } else { JOptionPane.showMessageDialog(vista, "Error");  }
        limpiarTabla();
    }

    public void actualizar() {
        if (vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "para actualizar debe seleccionar primero la opcion editar");
        } else {
            int id = Integer.parseInt(vista.txtId.getText());
            String nom = vista.txtNombre.getText();
            String correo = vista.txtCorreo.getText();
            String tel = vista.txtTelefono.getText();
            p.setId(id);
            p.setNombre(nom);
            p.setCorreo(correo);
            p.setTelefono(tel);
            int r = dao.Actualizar(p);

            if (r == 1) {
                System.out.println("Se ha actualizado la persona con exito (" + id + ")");

            } else { JOptionPane.showMessageDialog(vista, "Error");  }
        }
         limpiarTabla();
         
    }
    
    public void listar(JTable tabla){
     
      centrarCeldas(tabla);
      modelo = (DefaultTableModel)tabla.getModel();
      tabla.setModel(modelo);
      List<Persona>lista = dao.listar();
      Object[] objeto = new Object[4];
      
      for(int i = 0; i < lista.size(); i++){
        objeto[0] = lista.get(i).getId();
        objeto[1] = lista.get(i).getNombre();
        objeto[2] = lista.get(i).getCorreo();
        objeto[3] = lista.get(i).getTelefono();
        modelo.addRow(objeto);
      }
      
      tabla.setRowHeight(35);
      tabla.setRowMargin(10);
    }
    
    void centrarCeldas(JTable tabla){
      DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
      tcr.setHorizontalAlignment(SwingConstants.CENTER);
      
      for(int i = 0; i < vista.tabla.getColumnCount(); i++){
       tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
      }
    }
    
    void limpiarTabla(){
     for(int i = 0; i < vista.tabla.getRowCount(); i++){
      modelo.removeRow(i);
      i = i - 1;
     }
    }
}
