package com.donacionAlimentos.view.components;

import javax.swing.JLabel;

/**
 * clase que ayuda a mejorar es aspecto de la interfas sin agregar ruido al
 * codigo
 *
 * @author erwbyel
 */
public class PLabelButton extends JLabel {

    public PLabelButton() {
        super();
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setForeground(new java.awt.Color(124, 111, 159));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setForeground(new java.awt.Color(255, 255, 255));
            }
        });
    }
}
