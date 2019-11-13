package controlador;

import vista.Vista;

public class Main {

    public static void main(String args[]) {

        Vista v = new Vista();
        Controlador con = new Controlador(v);
        v.setVisible(true);
        v.setLocationRelativeTo(null);
        con.listar(v.tabla);
    }
}
