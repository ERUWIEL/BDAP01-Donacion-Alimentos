/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

/**
 *
 * @author saidr
 */
import com.donacionAlimentos.dao.OrganizacionDAO;
import com.donacionAlimentos.models.Organizacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmOrganizacion extends JFrame {

    // --- Componentes ---
    private JLabel lblIdPersona, lblNombre;
    private JTextField txtIdPersona, txtNombre;
    private JButton btnAgregar, btnModificar, btnEliminar, btnRefrescar;
    private JTable tablaOrganizaciones;
    private DefaultTableModel modeloTabla;

    // --- DAO ---
    private OrganizacionDAO organizacionDAO = new OrganizacionDAO();

    /**
     * Creates new form FrmOrganizacion
     */
    public FrmOrganizacion() {
        initComponents();
                setTitle("Gestión de Organizaciones");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        organizacionDAO = new OrganizacionDAO();

        // ---------- PANEL SUPERIOR: Formulario ----------
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 15, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos de la Organización",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelFormulario.setBackground(new Color(250, 250, 255));

        lblIdPersona = new JLabel("ID Persona:");
        txtIdPersona = new JTextField();
        lblIdPersona.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblNombre = new JLabel("Nombre de la Organización:");
        txtNombre = new JTextField();
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panelFormulario.add(lblIdPersona);
        panelFormulario.add(txtIdPersona);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);

        add(panelFormulario, BorderLayout.NORTH);

        // ---------- PANEL CENTRAL: Botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 250));

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        JButton[] botones = {btnAgregar, btnModificar, btnEliminar, btnRefrescar};
        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            b.setBackground(new Color(100, 149, 237));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 130, 180)),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));
            panelBotones.add(b);
        }

        // ---------- PANEL INFERIOR: Tabla ----------
        modeloTabla = new DefaultTableModel(new String[]{"ID Persona", "Nombre Organización"}, 0);
        tablaOrganizaciones = new JTable(modeloTabla);
        tablaOrganizaciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaOrganizaciones.setRowHeight(22);
        tablaOrganizaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaOrganizaciones.getTableHeader().setBackground(new Color(100, 149, 237));
        tablaOrganizaciones.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaOrganizaciones);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ---------- Eventos ----------
        btnAgregar.addActionListener(e -> agregarOrganizacion());
        btnModificar.addActionListener(e -> modificarOrganizacion());
        btnEliminar.addActionListener(e -> eliminarOrganizacion());
        btnRefrescar.addActionListener(e -> {
            cargarOrganizaciones();
            limpiarCampos();
        });

        tablaOrganizaciones.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaOrganizaciones.getSelectedRow();
                if (fila >= 0) {
                    txtIdPersona.setText(tablaOrganizaciones.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaOrganizaciones.getValueAt(fila, 1).toString());
                }
            }
        });

        // Cargar datos iniciales
        cargarOrganizaciones();

    
    }
       private void cargarOrganizaciones() {
        modeloTabla.setRowCount(0);
        List<Organizacion> lista = organizacionDAO.readAll();
        for (Organizacion o : lista) {
            modeloTabla.addRow(new Object[]{
                    o.getIdPersona(),
                    o.getNombreOrganizacion()
            });
        }
    }

    private void limpiarCampos() {
        txtIdPersona.setText("");
        txtNombre.setText("");
    }

    private void agregarOrganizacion() {
        try {
            Organizacion o = new Organizacion();
            o.setIdPersona(Integer.parseInt(txtIdPersona.getText()));
            o.setNombreOrganizacion(txtNombre.getText());

            if (organizacionDAO.create(o)) {
                JOptionPane.showMessageDialog(this, "Organización agregada correctamente");
                cargarOrganizaciones();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar organización");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void modificarOrganizacion() {
        try {
            Organizacion o = new Organizacion();
            o.setIdPersona(Integer.parseInt(txtIdPersona.getText()));
            o.setNombreOrganizacion(txtNombre.getText());

            if (organizacionDAO.update(o)) {
                JOptionPane.showMessageDialog(this, "Organización modificada correctamente");
                cargarOrganizaciones();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar organización");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void eliminarOrganizacion() {
        try {
            int id = Integer.parseInt(txtIdPersona.getText());
            if (organizacionDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Organización eliminada correctamente");
                cargarOrganizaciones();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar organización");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro válido");
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmOrganizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmOrganizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmOrganizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmOrganizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmOrganizacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
