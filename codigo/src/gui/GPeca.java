/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Djam
 */
public class GPeca extends JLabel {
    
    public GPeca() {
        super();
        this.setPreferredSize(new Dimension(120, 120));
        this.setBackground(new Color(80, 80, 80));
//        this.setBorder(LineBorder.createGrayLineBorder());
    }
}
