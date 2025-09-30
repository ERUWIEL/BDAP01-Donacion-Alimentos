/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.donacionAlimentos.view;

/**
 *
 * @author saidr
 */
import com.donacionAlimentos.dao.ContenidoDAO;
import com.donacionAlimentos.models.Contenido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmContenido extends JFrame {

    private JTextField txtIdContenido, txtIdAlimento, txtIdEntrega, txtIdAportacion;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnMostrarTodos;
    private JTable tablaContenidos;
    private DefaultTableModel modeloTabla;
    private ContenidoDAO contenidoDAO;



    /**
     * Creates new form FrmContenido
     */
    public FrmContenido() {
         setTitle("Gestión de Contenidos");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        contenidoDAO = new ContenidoDAO();

        // ---------- PANEL SUPERIOR: Formulario ----------
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 15, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                "Datos del Contenido",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                new Color(60, 90, 180)
        ));
        panelFormulario.setBackground(new Color(250, 250, 255));

        panelFormulario.add(new JLabel("ID Contenido:"));
        txtIdContenido = new JTextField();
        panelFormulario.add(txtIdContenido);

        panelFormulario.add(new JLabel("ID Alimento:"));
        txtIdAlimento = new JTextField();
        panelFormulario.add(txtIdAlimento);

        panelFormulario.add(new JLabel("ID Entrega:"));
        txtIdEntrega = new JTextField();
        panelFormulario.add(txtIdEntrega);

        panelFormulario.add(new JLabel("ID Aportación:"));
        txtIdAportacion = new JTextField();
        panelFormulario.add(txtIdAportacion);

        add(panelFormulario, BorderLayout.NORTH);

        // ---------- PANEL CENTRAL: Botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(245, 245, 250));

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnMostrarTodos = new JButton("Mostrar Todos");

        JButton[] botones = {btnAgregar, btnActualizar, btnEliminar, btnBuscar, btnMostrarTodos};
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
        modeloTabla = new DefaultTableModel(new String[]{"ID Contenido", "ID Alimento", "ID Entrega", "ID Aportación"}, 0);
        tablaContenidos = new JTable(modeloTabla);
        tablaContenidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaContenidos.setRowHeight(22);
        tablaContenidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaContenidos.getTableHeader().setBackground(new Color(100, 149, 237));
        tablaContenidos.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaContenidos);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.add(panelBotones, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ---------- Eventos ----------
        btnAgregar.addActionListener(e -> agregarContenido());
        btnActualizar.addActionListener(e -> actualizarContenido());
        btnEliminar.addActionListener(e -> eliminarContenido());
        btnBuscar.addActionListener(e -> buscarContenido());
        btnMostrarTodos.addActionListener(e -> cargarContenidos());

        // Cargar datos iniciales
        cargarContenidos();

     
    }
     private void agregarContenido() {
        try {
            Contenido contenido = new Contenido();
            contenido.setIdAlimento(Integer.parseInt(txtIdAlimento.getText()));
            contenido.setIdEntrega(Integer.parseInt(txtIdEntrega.getText()));
            contenido.setIdAportacion(Integer.parseInt(txtIdAportacion.getText()));

            if (contenidoDAO.create(contenido)) {
                JOptionPane.showMessageDialog(this, "Contenido agregado correctamente");
                cargarContenidos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar contenido");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Campos inválidos");
        }
    }

    private void actualizarContenido() {
        try {
            Contenido contenido = new Contenido();
            contenido.setIdContenido(Integer.parseInt(txtIdContenido.getText()));
            contenido.setIdAlimento(Integer.parseInt(txtIdAlimento.getText()));
            contenido.setIdEntrega(Integer.parseInt(txtIdEntrega.getText()));
            contenido.setIdAportacion(Integer.parseInt(txtIdAportacion.getText()));

            if (contenidoDAO.update(contenido)) {
                JOptionPane.showMessageDialog(this, "Contenido actualizado correctamente");
                cargarContenidos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar contenido");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Campos inválidos");
        }
    }

    private void eliminarContenido() {
        try {
            int id = Integer.parseInt(txtIdContenido.getText());
            if (contenidoDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Contenido eliminado correctamente");
                cargarContenidos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar contenido");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    private void buscarContenido() {
        try {
            int id = Integer.parseInt(txtIdContenido.getText());
            Contenido contenido = contenidoDAO.read(id);
            if (contenido != null) {
                txtIdAlimento.setText(String.valueOf(contenido.getIdAlimento()));
                txtIdEntrega.setText(String.valueOf(contenido.getIdEntrega()));
                txtIdAportacion.setText(String.valueOf(contenido.getIdAportacion()));
            } else {
                JOptionPane.showMessageDialog(this, "Contenido no encontrado");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    private void cargarContenidos() {
        modeloTabla.setRowCount(0);
        List<Contenido> lista = contenidoDAO.readAll();
        for (Contenido c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getIdContenido(),
                    c.getIdAlimento(),
                    c.getIdEntrega(),
                    c.getIdAportacion()
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
            java.util.logging.Logger.getLogger(FrmContenido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmContenido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmContenido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmContenido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmContenido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
