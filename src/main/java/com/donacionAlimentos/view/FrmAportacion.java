/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

/**
 *
 * @author saidr
 */
import com.donacionAlimentos.dao.AportacionDAO;
import com.donacionAlimentos.models.Aportacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

public class FrmAportacion extends JFrame {

    private JLabel lblId, lblFecha, lblCantidad, lblDonante, lblAlimento;
    private JTextField txtId, txtFecha, txtCantidad, txtDonante, txtAlimento;
    private JButton btnAgregar, btnBuscar, btnActualizar, btnEliminar, btnRefrescar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private AportacionDAO aportacionDAO;

    /**
     * Creates new form frmAportacion
     */
    public FrmAportacion() {
        initComponents();
  setTitle("Gestión de Aportaciones");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        aportacionDAO = new AportacionDAO();

        // ---------- PANEL FORMULARIO ----------
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 15, 15));
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos de la Aportación",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelForm.setBackground(new Color(250, 250, 255));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField();
        txtId.setEnabled(false);

        JLabel lblFecha = new JLabel("Fecha Caducidad (yyyy-MM-dd):");
        txtFecha = new JTextField();

        JLabel lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField();

        JLabel lblDonante = new JLabel("ID Donante:");
        txtDonante = new JTextField();

        JLabel lblAlimento = new JLabel("ID Alimento:");
        txtAlimento = new JTextField();

        JLabel[] labels = {lblId, lblFecha, lblCantidad, lblDonante, lblAlimento};
        JTextField[] fields = {txtId, txtFecha, txtCantidad, txtDonante, txtAlimento};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panelForm.add(labels[i]);
            panelForm.add(fields[i]);
        }

        add(panelForm, BorderLayout.NORTH);

        // ---------- TABLA ----------
        modelo = new DefaultTableModel(new String[]{"ID", "Fecha Caducidad", "Cantidad", "ID Donante", "ID Alimento"}, 0);
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(22);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(100, 149, 237));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // ---------- PANEL BOTONES ----------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 250));

        btnAgregar = new JButton("Agregar");
        btnBuscar = new JButton("Buscar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        JButton[] botones = {btnAgregar, btnBuscar, btnActualizar, btnEliminar, btnRefrescar};
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

        add(panelBotones, BorderLayout.SOUTH);

        // ---------- EVENTOS ----------
        btnAgregar.addActionListener(e -> agregarAportacion());
        btnBuscar.addActionListener(e -> buscarAportacion());
        btnActualizar.addActionListener(e -> actualizarAportacion());
        btnEliminar.addActionListener(e -> eliminarAportacion());
        btnRefrescar.addActionListener(e -> cargarAportaciones());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(tabla.getValueAt(fila, 0).toString());
                    txtFecha.setText(tabla.getValueAt(fila, 1).toString());
                    txtCantidad.setText(tabla.getValueAt(fila, 2).toString());
                    txtDonante.setText(tabla.getValueAt(fila, 3).toString());
                    txtAlimento.setText(tabla.getValueAt(fila, 4).toString());
                }
            }
        });

        cargarAportaciones();

    }
      private void cargarAportaciones() {
        modelo.setRowCount(0);
        List<Aportacion> aportaciones = aportacionDAO.readAll();
        for (Aportacion a : aportaciones) {
            modelo.addRow(new Object[]{
                a.getIdAportacion(),
                a.getFechaCaducidad(),
                a.getCantidad(),
                a.getIdDonante(),
                a.getIdAlimento()
            });
        }
    }

    private void agregarAportacion() {
        try {
            String fecha = txtFecha.getText().trim();
            BigDecimal cantidad = new BigDecimal(txtCantidad.getText().trim());
            int idDonante = Integer.parseInt(txtDonante.getText().trim());
            int idAlimento = Integer.parseInt(txtAlimento.getText().trim());

            Aportacion aportacion = new Aportacion();
            aportacion.setFechaCaducidad(java.sql.Date.valueOf(fecha));
            aportacion.setCantidad(cantidad);
            aportacion.setIdDonante(idDonante);
            aportacion.setIdAlimento(idAlimento);

            if (aportacionDAO.create(aportacion)) {
                JOptionPane.showMessageDialog(this, "Aportación registrada con éxito.");
                cargarAportaciones();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la aportación.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage());
        }
    }

    private void buscarAportacion() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Aportacion aportacion = aportacionDAO.read(id);
            if (aportacion != null) {
                txtFecha.setText(aportacion.getFechaCaducidad().toString());
                txtCantidad.setText(aportacion.getCantidad().toString());
                txtDonante.setText(String.valueOf(aportacion.getIdDonante()));
                txtAlimento.setText(String.valueOf(aportacion.getIdAlimento()));
            } else {
                JOptionPane.showMessageDialog(this, "Aportación no encontrada.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }
    }

    private void actualizarAportacion() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String fecha = txtFecha.getText().trim();
            BigDecimal cantidad = new BigDecimal(txtCantidad.getText().trim());
            int idDonante = Integer.parseInt(txtDonante.getText().trim());
            int idAlimento = Integer.parseInt(txtAlimento.getText().trim());

            Aportacion aportacion = new Aportacion();
            aportacion.setIdAportacion(id);
            aportacion.setFechaCaducidad(java.sql.Date.valueOf(fecha));
            aportacion.setCantidad(cantidad);
            aportacion.setIdDonante(idDonante);
            aportacion.setIdAlimento(idAlimento);

            if (aportacionDAO.update(aportacion)) {
                JOptionPane.showMessageDialog(this, "Aportación actualizada con éxito.");
                cargarAportaciones();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la aportación.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en actualización: " + ex.getMessage());
        }
    }

    private void eliminarAportacion() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (aportacionDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Aportación eliminada con éxito.");
                cargarAportaciones();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la aportación.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en eliminación: " + ex.getMessage());
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
            java.util.logging.Logger.getLogger(FrmAportacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAportacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAportacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAportacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAportacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
