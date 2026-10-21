package proyect;

import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author carri
 */
public class tipunit extends JFrame {

    JMenu archivo, bd;
    JMenuItem db_c, db_r, db_u, db_d;
    JMenuBar mb;
    public JLabel lbll, lb_id, lb_t, lb_d, lb_a, lb_m, lb_ur;
    public JTextField txtl, tipo, descripcion, auto, moto, urbano;
    public JButton btnl, btn_consulta, btn_alta, btn_actualizar, btn_eliminar;

    tipunit() {
        this.setBounds(150, 50, 800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu();
        this.setResizable(true);
        this.setLayout(null);

        this.lbll = new JLabel("Bienvenido a mi proyecto de unidades");
        this.lbll.setBounds(300, 5, 200, 30);

        this.lb_id = new JLabel("Id");
        this.lb_id.setBounds(100, 40, 200, 30);

        this.lb_t = new JLabel("Tipo");
        this.lb_t.setBounds(100, 80, 60, 30);

        this.lb_d = new JLabel("Descripción");
        this.lb_d.setBounds(100, 120, 60, 30);

        this.lb_a = new JLabel("Auto");
        this.lb_a.setBounds(100, 160, 80, 30);

        this.lb_m = new JLabel("Moto");
        this.lb_m.setBounds(100, 200, 70, 30);

        this.lb_ur = new JLabel("Urbano");
        this.lb_ur.setBounds(100, 240, 60, 30);

        this.txtl = new JTextField("");
        this.txtl.setBounds(170, 40, 200, 30);

        this.tipo = new JTextField("");
        this.tipo.setBounds(170, 80, 200, 30);

        this.descripcion = new JTextField("");
        this.descripcion.setBounds(170, 120, 200, 30);

        this.auto = new JTextField("");
        this.auto.setBounds(170, 160, 200, 30);

        this.moto = new JTextField("");
        this.moto.setBounds(170, 200, 200, 30);

        this.urbano = new JTextField("");
        this.urbano.setBounds(170, 240, 200, 30);

        this.btnl = new JButton("Enviar");
        this.btnl.setBounds(180, 340, 100, 40);

        this.btn_consulta = new JButton("consultar BD");
        this.btn_consulta.setBounds(60, 340, 100, 40);
        this.btn_consulta.addActionListener(e -> consultar());

        // boton Alta DB
        this.btn_alta = new JButton("Alta DB");
        this.btn_alta.setBounds(290, 340, 100, 40);
        this.btn_alta.addActionListener(e -> insertar());

        // boton Actualizar
        this.btn_actualizar = new JButton("Actualizar");
        this.btn_actualizar.setBounds(400, 340, 100, 40);
        this.btn_actualizar.addActionListener((e) -> actualizar());

        // boton Eliminar
        this.btn_eliminar = new JButton("Eliminar");
        this.btn_eliminar.setBounds(510, 340, 100, 40);
        this.btn_eliminar.addActionListener((e) -> eliminar());

        // componentes al frame
        this.add(btn_alta);
        this.add(btn_consulta);
        this.add(lb_t);
        this.add(tipo);
        this.add(descripcion);
        this.add(auto);
        this.add(moto);
        this.add(urbano);
        this.add(lb_d);
        this.add(lbll);
        this.add(lb_m);
        this.add(lb_a);
        this.add(lb_ur);
        this.add(btnl);
        this.add(btn_eliminar);
        this.add(btn_actualizar);
        this.add(txtl);
        this.add(lb_id);
        this.setVisible(true);
    }

    public void actualizar() {
        try {
            Connection cnx;
            PreparedStatement ps;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("UPDATE vehiculos SET tipo = ? WHERE id = ?");
            ps.setString(1, this.tipo.getText());
            // CORREGIDO: Se usa txtl y se quita el punto extra
            ps.setString(2, (this.txtl.getText()));

            ps.executeUpdate();

            this.consultar();
            cnx.close();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Conexión a base de datos
    public void consultar() {
        Connection cnx;
        PreparedStatement ps;
        ResultSet rs;

        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("SELECT * FROM vehiculos");
            rs = ps.executeQuery();
            System.out.println("========================================");
            while (rs.next()) {
                System.out.println("ID" + rs.getString("id") + "- Tipo: " + rs.getString("tipo"));
            }
            cnx.close();

        } catch (SQLException ex) {
            System.out.println("Error conexion" + ex.getMessage());
        }
    }

    // Método para eliminar
    public void eliminar() {
        try {
            Connection cnx;
            PreparedStatement ps;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("DELETE FROM vehiculos WHERE id = ?");
            ps.setString(1,(this.txtl.getText()));

            ps.execute();

            this.consultar();
            cnx.close();

        } catch (SQLException ex) {
            System.out.println("Error en la eliminación: " + ex.getMessage());
        }
    }

    // metodo para Insertar
    public void insertar() {
        Connection cnx;
        PreparedStatement ps;

        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("INSERT INTO vehiculos (id, tipo, descripcion, auto, moto, urbano) VALUES (?, ?, ?, ?, ?, ?)");

            // obtenemos datos de las cajas de texto
            ps.setString(1, txtl.getText());
            ps.setString(2, tipo.getText());
            ps.setString(3, descripcion.getText());
            ps.setString(4, auto.getText());
            ps.setString(5, moto.getText());
            ps.setString(6, urbano.getText());

            ps.executeUpdate();
            cnx.close();

            JOptionPane.showMessageDialog(rootPane, "Registro guardado");
            txtl.setText("");
            tipo.setText("");
            descripcion.setText("");
            auto.setText("");
            moto.setText("");
            urbano.setText("");

        } catch (SQLException ex) {
            System.out.println("Error en la inserción: " + ex.getMessage());
        }   
    }

    public void menu() {
        this.mb = new JMenuBar();
        this.setJMenuBar(mb);
        this.archivo = new JMenu("Archivo");
        this.mb.add(this.archivo);

        this.bd = new JMenu("Base De Datos");
        this.mb.add(bd);

        this.db_c = new JMenuItem("C - Crear");
        this.db_c.addActionListener((e) -> insertar());
        this.bd.add(this.db_c);

        this.db_r = new JMenuItem("R - Consulta");
        this.db_r.addActionListener((e) -> consultar());
        this.bd.add(this.db_r);

        this.db_u = new JMenuItem("U - Actualizar");
        this.db_u.addActionListener((e) -> actualizar());
        this.bd.add(this.db_u);

        this.db_d = new JMenuItem("D - Eliminar");
        this.db_d.addActionListener((e) -> eliminar());
        this.bd.add(this.db_d);
    }

    public static void main(String[] args) {
        new tipunit();
    }
}
