/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

import com.donacionAlimentos.dao.DonanteDAO;
import com.donacionAlimentos.models.Donante;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saidr
 */
public class FrmDonante extends javax.swing.JFrame {

    private JLabel lblTitulo;
    private JLabel lblIdPersona;
    private JLabel lblTipoDonante;
    private JTextField txtIdPersona;
    private JTextField txtTipo;
    private JButton btnInsertar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tablaDonantes;
    private JScrollPane scrollTabla;
    private DonanteDAO donanteDAO; // DAO para la conexión con BD
    private DefaultTableModel modeloTabla; // Modelo para JTable

    /**
     * Creates new form FrmDonante
     */
    public FrmDonante() {
        initComponents();
// Configuración básica de la ventana
setTitle("Gestión de Donantes");
setSize(700, 550);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setLayout(new BorderLayout());
getContentPane().setBackground(new Color(245, 245, 250));

donanteDAO = new DonanteDAO();

// ---------- PANEL SUPERIOR: Formulario ----------
JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 15, 15));
panelFormulario.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
        "Datos del Donante",
        0, 0, new Font("Segoe UI", Font.BOLD, 14),
        new Color(60, 90, 180)
));
panelFormulario.setBackground(new Color(250, 250, 255));

lblIdPersona = new JLabel("ID Persona:");
lblIdPersona.setFont(new Font("Segoe UI", Font.PLAIN, 13));
txtIdPersona = new JTextField();

lblTipoDonante = new JLabel("Tipo Donante:");
lblTipoDonante.setFont(new Font("Segoe UI", Font.PLAIN, 13));
txtTipo = new JTextField();

panelFormulario.add(lblIdPersona);
panelFormulario.add(txtIdPersona);
panelFormulario.add(lblTipoDonante);
panelFormulario.add(txtTipo);

// ---------- PANEL CENTRAL: Botones ----------
JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
panelBotones.setBackground(new Color(245, 245, 250));

btnInsertar = new JButton("Insertar");
btnActualizar = new JButton("Actualizar");
btnEliminar = new JButton("Eliminar");
btnBuscar = new JButton("Buscar");
btnListar = new JButton("Listar");

// Estilo botones
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
modeloTabla = new DefaultTableModel(new String[]{"ID Persona", "Tipo Donante"}, 0);
tablaDonantes = new JTable(modeloTabla);
tablaDonantes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
tablaDonantes.setRowHeight(22);
tablaDonantes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
tablaDonantes.getTableHeader().setBackground(new Color(100, 149, 237));
tablaDonantes.getTableHeader().setForeground(Color.WHITE);

scrollTabla = new JScrollPane(tablaDonantes);

// ---------- PANEL CENTRAL QUE UNE BOTONES Y TABLA ----------
JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
panelCentro.add(panelBotones, BorderLayout.NORTH);
panelCentro.add(scrollTabla, BorderLayout.CENTER);

// ---------- Agregar paneles al frame ----------
add(panelFormulario, BorderLayout.NORTH);
add(panelCentro, BorderLayout.CENTER);


        // ---------- Eventos de botones ----------
        btnInsertar.addActionListener(e -> insertarDonante());
        btnActualizar.addActionListener(e -> actualizarDonante());
        btnEliminar.addActionListener(e -> eliminarDonante());
        btnBuscar.addActionListener(e -> buscarDonante());
        btnListar.addActionListener(e -> listarDonantes());
    }

    private void insertarDonante() {
        try {
            int id = Integer.parseInt(txtIdPersona.getText());
            String tipo = txtTipo.getText();

            Donante d = new Donante();
            d.setIdPersona(id);
            d.setTipo(tipo);

            if (donanteDAO.create(d)) {
                JOptionPane.showMessageDialog(this, "✅ Donante insertado correctamente.");
                listarDonantes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo insertar el donante.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        }
    }

    /**
     * Actualiza un donante existente
     */
    private void actualizarDonante() {
        try {
            int id = Integer.parseInt(txtIdPersona.getText());
            String tipo = txtTipo.getText();

            Donante d = new Donante();
            d.setIdPersona(id);
            d.setTipo(tipo);

            if (donanteDAO.update(d)) {
                JOptionPane.showMessageDialog(this, "✅ Donante actualizado.");
                listarDonantes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo actualizar el donante.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        }
    }

    /**
     * Elimina un donante por ID
     */
    private void eliminarDonante() {
        try {
            int id = Integer.parseInt(txtIdPersona.getText());
            if (donanteDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "🗑️ Donante eliminado.");
                listarDonantes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se encontró el donante.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        }
    }

    /**
     * Busca un donante por ID
     */
    private void buscarDonante() {
        try {
            int id = Integer.parseInt(txtIdPersona.getText());
            Donante d = donanteDAO.read(id);
            if (d != null) {
                txtTipo.setText(d.getTipo());
                JOptionPane.showMessageDialog(this, "✅ Donante encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ No existe el donante.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        }
    }

    /**
     * Lista todos los donantes en la tabla
     */
    private void listarDonantes() {
        modeloTabla.setRowCount(0);
        List<Donante> lista = donanteDAO.readAll();
        for (Donante d : lista) {
            modeloTabla.addRow(new Object[]{d.getIdPersona(), d.getTipo()});
        }
    }

    /**
     * Limpia los campos de texto
     */
    private void limpiarCampos() {
        txtIdPersona.setText("");
        txtTipo.setText("");
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
            .addGap(0, 759, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(FrmDonante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDonante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDonante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDonante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDonante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
