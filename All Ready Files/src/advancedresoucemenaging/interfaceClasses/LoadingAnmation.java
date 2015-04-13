/*
 * Copyright (C) 2014 Stanisalv
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package advancedresoucemenaging.interfaceClasses;

import advancedresoucemenaging.GUIClasses.Colors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Stanisalv
 */
public class LoadingAnmation extends JFrame implements Runnable {

    JPanel panel;
    double rotation = 0.0d;

    public LoadingAnmation(Point loc) throws HeadlessException {
        super("Loading");
        setLayout(new BorderLayout());//setting layout menager
        setSize(200, 120);
        setResizable(false);
        setLocation(loc);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("advancedresoucemenaging/Timetable.png")));//setting the icon image
        setBackground(Colors.auqaTabbedPaneGradientColor2);
        panel = new JPanel() {
            {
                setBackground(Colors.auqaTabbedPaneGradientColor2);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.black);
                g2.rotate(rotation, 100, 50);
                g2.fillRect(80, 30, 40, 40);
                g2.setColor(getBackground());
                g2.fillRect(85, 35, 30, 30);
                g2.setColor(Color.black);
                g2.fillRect(90, 40, 20, 20);
                g2.setColor(getBackground());
                g2.fillRect(95, 45, 10, 10);
            }

        };
        add(panel);

        new Thread(this).start();
        setVisible(true);
        validate();

    }

    @Override
    public void run() {
        while (true) {
            rotation += 0.15;
            if (rotation >= 2 * Math.PI) {
                rotation = 0.0;
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoadingAnmation.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.repaint();
            this.repaint();
        }
    }

}
