/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

import com.donacionAlimentos.dao.AlimentoDAO;
import com.donacionAlimentos.models.Alimento;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saidr
 */
public class FrmAlimento extends javax.swing.JFrame {
    private JLabel lblId, lblNombre, lblCantidad, lblTipo;
    private JTextField txtId, txtNombre, txtCantidad, txtTipo;
    private JButton btnInsertar, btnActualizar, btnEliminar, btnBuscar, btnListar;
    private JTable tablaAlimentos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    private AlimentoDAO alimentoDAO;

    /**
     * Creates new form FrmAlimento
     */
    public FrmAlimento() {
        initComponents();
                // Configuración básica
        setTitle("Gestión de Alimentos");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        alimentoDAO = new AlimentoDAO();

        // ---------- PANEL SUPERIOR: Formulario ----------
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 15, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos del Alimento",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelFormulario.setBackground(new Color(250, 250, 255));

        lblId = new JLabel("ID Alimento:");
        txtId = new JTextField();

        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField();

        lblTipo = new JLabel("Tipo de Alimento:");
        txtTipo = new JTextField();

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblCantidad);
        panelFormulario.add(txtCantidad);
        panelFormulario.add(lblTipo);
        panelFormulario.add(txtTipo);

        // ---------- PANEL CENTRAL: Botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 250));

        btnInsertar = new JButton("Insertar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnListar = new JButton("Listar");

        JButton[] botones = {btnInsertar, btnActualizar, btnEliminar, btnBuscar, btnListar};
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
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "Tipo"}, 0);
        tablaAlimentos = new JTable(modeloTabla);
        tablaAlimentos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaAlimentos.setRowHeight(22);
        tablaAlimentos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaAlimentos.getTableHeader().setBackground(new Color(100, 149, 237));
        tablaAlimentos.getTableHeader().setForeground(Color.WHITE);

        scrollTabla = new JScrollPane(tablaAlimentos);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        // ---------- Agregar paneles al frame ----------
        add(panelFormulario, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        // ---------- Acciones de botones ----------
        btnInsertar.addActionListener(e -> insertarAlimento());
        btnActualizar.addActionListener(e -> actualizarAlimento());
        btnEliminar.addActionListener(e -> eliminarAlimento());
        btnBuscar.addActionListener(e -> buscarAlimento());
        btnListar.addActionListener(e -> listarAlimentos());
    }
     private void insertarAlimento() {
        try {
            String nombre = txtNombre.getText();
            BigDecimal cantidad = new BigDecimal(txtCantidad.getText());
            String tipo = txtTipo.getText();

            Alimento alimento = new Alimento();
            alimento.setNombre(nombre);
            alimento.setCantidad(cantidad);
            alimento.setTipo(tipo);

            if (alimentoDAO.create(alimento)) {
                JOptionPane.showMessageDialog(this, "Alimento insertado correctamente");
                listarAlimentos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar alimento");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void actualizarAlimento() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            BigDecimal cantidad = new BigDecimal(txtCantidad.getText());
            String tipo = txtTipo.getText();

            Alimento alimento = new Alimento();
            alimento.setIdAlimento(id);
            alimento.setNombre(nombre);
            alimento.setCantidad(cantidad);
            alimento.setTipo(tipo);

            if (alimentoDAO.update(alimento)) {
                JOptionPane.showMessageDialog(this, "Alimento actualizado correctamente");
                listarAlimentos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar alimento");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void eliminarAlimento() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (alimentoDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Alimento eliminado correctamente");
                listarAlimentos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el alimento");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void buscarAlimento() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Alimento alimento = alimentoDAO.read(id);
            if (alimento != null) {
                txtNombre.setText(alimento.getNombre());
                txtCantidad.setText(alimento.getCantidad().toString());
                txtTipo.setText(alimento.getTipo());
                JOptionPane.showMessageDialog(this, "Alimento encontrado");
            } else {
                JOptionPane.showMessageDialog(this, "No existe un alimento con ese ID");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listarAlimentos() {
        modeloTabla.setRowCount(0); // limpiar tabla
        List<Alimento> lista = alimentoDAO.readAll();
        for (Alimento a : lista) {
            modeloTabla.addRow(new Object[]{
                    a.getIdAlimento(),
                    a.getNombre(),
                    a.getCantidad(),
                    a.getTipo()
            });
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
            java.util.logging.Logger.getLogger(FrmAlimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAlimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAlimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAlimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAlimento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
