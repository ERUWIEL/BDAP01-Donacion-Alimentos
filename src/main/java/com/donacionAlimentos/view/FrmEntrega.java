/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

/**
 *
 * @author saidr
 */
import com.donacionAlimentos.dao.EntregaDAO;
import com.donacionAlimentos.models.Entrega;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmEntrega extends JFrame {

    private JLabel lblId, lblFecha, lblEstado, lblOrg;
    private JTextField txtId, txtFecha, txtOrg;
    private JComboBox<String> cmbEstado;
    private JButton btnAgregar, btnBuscar, btnActualizar, btnEliminar, btnRefrescar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private EntregaDAO entregaDAO;

    /**
     * Creates new form FrmEntrega
     */
    public FrmEntrega() {
        initComponents();
       setTitle("Gestión de Entregas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        entregaDAO = new EntregaDAO();

        // ---------- PANEL FORMULARIO ----------
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 15, 15));
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos de la Entrega",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelForm.setBackground(new Color(250, 250, 255));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField();
        txtId.setEnabled(false);

        JLabel lblFecha = new JLabel("Fecha (yyyy-MM-dd):");
        txtFecha = new JTextField();

        JLabel lblEstado = new JLabel("Estado:");
        cmbEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_PROCESO", "ENTREGADA"});

        JLabel lblOrg = new JLabel("ID Organización:");
        txtOrg = new JTextField();

        JLabel[] labels = {lblId, lblFecha, lblEstado, lblOrg};
        Component[] fields = {txtId, txtFecha, cmbEstado, txtOrg};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panelForm.add(labels[i]);
            panelForm.add(fields[i]);
        }

        add(panelForm, BorderLayout.NORTH);

        // ---------- TABLA ----------
        modelo = new DefaultTableModel(new String[]{"ID", "Fecha", "Estado", "ID Organización"}, 0);
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
        btnAgregar.addActionListener(e -> agregarEntrega());
        btnBuscar.addActionListener(e -> buscarEntrega());
        btnActualizar.addActionListener(e -> actualizarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnRefrescar.addActionListener(e -> cargarEntregas());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(tabla.getValueAt(fila, 0).toString());
                    txtFecha.setText(tabla.getValueAt(fila, 1).toString());
                    cmbEstado.setSelectedItem(tabla.getValueAt(fila, 2).toString());
                    txtOrg.setText(tabla.getValueAt(fila, 3).toString());
                }
            }
        });

        cargarEntregas();
    }
      private void cargarEntregas() {
        modelo.setRowCount(0);
        List<Entrega> entregas = entregaDAO.readAll();
        for (Entrega e : entregas) {
            modelo.addRow(new Object[]{
                e.getIdEntrega(),
                e.getFechaEntrega(),
                e.getEstadoEntrega(),
                e.getIdOrganizacion()
            });
        }
    }

    private void agregarEntrega() {
        try {
            String fecha = txtFecha.getText().trim();
            String estado = (String) cmbEstado.getSelectedItem();
            int idOrg = Integer.parseInt(txtOrg.getText().trim());

            Entrega entrega = new Entrega();
            entrega.setFechaEntrega(java.sql.Date.valueOf(fecha));
            entrega.setEstadoEntrega(Entrega.Estado.valueOf(estado));
            entrega.setIdOrganizacion(idOrg);

            if (entregaDAO.create(entrega)) {
                JOptionPane.showMessageDialog(this, "Entrega agregada con éxito.");
                cargarEntregas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar la entrega.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage());
        }
    }

    private void buscarEntrega() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Entrega entrega = entregaDAO.read(id);
            if (entrega != null) {
                txtFecha.setText(entrega.getFechaEntrega().toString());
                cmbEstado.setSelectedItem(entrega.getEstadoEntrega().name());
                txtOrg.setText(String.valueOf(entrega.getIdOrganizacion()));
            } else {
                JOptionPane.showMessageDialog(this, "Entrega no encontrada.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }
    }

    private void actualizarEntrega() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String fecha = txtFecha.getText().trim();
            String estado = (String) cmbEstado.getSelectedItem();
            int idOrg = Integer.parseInt(txtOrg.getText().trim());

            Entrega entrega = new Entrega();
            entrega.setIdEntrega(id);
            entrega.setFechaEntrega(java.sql.Date.valueOf(fecha));
            entrega.setEstadoEntrega(Entrega.Estado.valueOf(estado));
            entrega.setIdOrganizacion(idOrg);

            if (entregaDAO.update(entrega)) {
                JOptionPane.showMessageDialog(this, "Entrega actualizada con éxito.");
                cargarEntregas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la entrega.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en actualización: " + ex.getMessage());
        }
    }

    private void eliminarEntrega() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (entregaDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Entrega eliminada con éxito.");
                cargarEntregas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la entrega.");
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
            java.util.logging.Logger.getLogger(FrmEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEntrega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmEntrega().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
