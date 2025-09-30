/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

import com.donacionAlimentos.dao.PersonaDAO;
import com.donacionAlimentos.models.Persona;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saidr
 */
public class FrmPersona extends javax.swing.JFrame {
        // --- Componentes ---
    private JLabel lblId, lblNombre, lblApPaterno, lblApMaterno, lblCorreo, lblTelefono, lblDireccion;
    private JTextField txtId, txtNombre, txtApPaterno, txtApMaterno, txtCorreo, txtTelefono, txtDireccion;
    private JButton btnAgregar, btnModificar, btnEliminar, btnRefrescar;
    private JTable tablaPersonas;
    private DefaultTableModel modeloTabla;

    // --- DAO ---
    private PersonaDAO personaDAO = new PersonaDAO();

    /**
     * Creates new form FrmPersona
     */
    public FrmPersona() {
        initComponents();
        setTitle("Gestión de Personas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        personaDAO = new PersonaDAO();

        // ---------- PANEL SUPERIOR: Formulario ----------
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 15, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos de la Persona",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelFormulario.setBackground(new Color(250, 250, 255));

        lblId = new JLabel("ID:");
        txtId = new JTextField();
        txtId.setEnabled(false);

        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        lblApPaterno = new JLabel("Apellido Paterno:");
        txtApPaterno = new JTextField();

        lblApMaterno = new JLabel("Apellido Materno:");
        txtApMaterno = new JTextField();

        lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField();

        lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField();

        lblDireccion = new JLabel("Dirección:");
        txtDireccion = new JTextField();

        JLabel[] labels = {lblId, lblNombre, lblApPaterno, lblApMaterno, lblCorreo, lblTelefono, lblDireccion};
        JTextField[] fields = {txtId, txtNombre, txtApPaterno, txtApMaterno, txtCorreo, txtTelefono, txtDireccion};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panelFormulario.add(labels[i]);
            panelFormulario.add(fields[i]);
        }

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
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Correo", "Teléfono", "Dirección"}, 0
        );
        tablaPersonas = new JTable(modeloTabla);
        tablaPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPersonas.setRowHeight(22);
        tablaPersonas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPersonas.getTableHeader().setBackground(new Color(100, 149, 237));
        tablaPersonas.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaPersonas);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ---------- Eventos ----------
        btnAgregar.addActionListener(e -> agregarPersona());
        btnModificar.addActionListener(e -> modificarPersona());
        btnEliminar.addActionListener(e -> eliminarPersona());
        btnRefrescar.addActionListener(e -> {
            cargarPersonas();
            limpiarCampos();
        });

        tablaPersonas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaPersonas.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(tablaPersonas.getValueAt(fila, 0).toString());
                    txtNombre.setText(tablaPersonas.getValueAt(fila, 1).toString());
                    txtApPaterno.setText(tablaPersonas.getValueAt(fila, 2).toString());
                    txtApMaterno.setText(tablaPersonas.getValueAt(fila, 3).toString());
                    txtCorreo.setText(tablaPersonas.getValueAt(fila, 4).toString());
                    txtTelefono.setText(tablaPersonas.getValueAt(fila, 5).toString());
                    txtDireccion.setText(tablaPersonas.getValueAt(fila, 6).toString());
                }
            }
        });

        // Cargar datos iniciales
        cargarPersonas();

    }

    private void cargarPersonas() {
        modeloTabla.setRowCount(0);
        List<Persona> lista = personaDAO.readAll();
        for (Persona p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getIdPersona(),
                    p.getNombre(),
                    p.getApPaterno(),
                    p.getApMaterno(),
                    p.getCorreo(),
                    p.getTelefono(),
                    p.getDireccion()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApPaterno.setText("");
        txtApMaterno.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }

    private void agregarPersona() {
        Persona p = new Persona();
        p.setNombre(txtNombre.getText());
        p.setApPaterno(txtApPaterno.getText());
        p.setApMaterno(txtApMaterno.getText());
        p.setCorreo(txtCorreo.getText());
        p.setTelefono(txtTelefono.getText());
        p.setDireccion(txtDireccion.getText());

        if (personaDAO.create(p)) {
            JOptionPane.showMessageDialog(this, "Persona agregada correctamente");
            cargarPersonas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar persona");
        }
    }

    private void modificarPersona() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una persona de la tabla");
            return;
        }

        Persona p = new Persona();
        p.setIdPersona(Integer.parseInt(txtId.getText()));
        p.setNombre(txtNombre.getText());
        p.setApPaterno(txtApPaterno.getText());
        p.setApMaterno(txtApMaterno.getText());
        p.setCorreo(txtCorreo.getText());
        p.setTelefono(txtTelefono.getText());
        p.setDireccion(txtDireccion.getText());

        if (personaDAO.update(p)) {
            JOptionPane.showMessageDialog(this, "Persona modificada correctamente");
            cargarPersonas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar persona");
        }
    }

    private void eliminarPersona() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una persona de la tabla");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        if (personaDAO.delete(id)) {
            JOptionPane.showMessageDialog(this, "Persona eliminada correctamente");
            cargarPersonas();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar persona");
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
            java.util.logging.Logger.getLogger(FrmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPersona().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
