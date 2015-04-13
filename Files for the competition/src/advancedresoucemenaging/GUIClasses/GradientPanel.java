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
package advancedresoucemenaging.GUIClasses;

import java.awt.*;
import javax.swing.JPanel;

/**

 @author Stanisalv
 */
public class GradientPanel extends JPanel {

    public GradientPanel(LayoutManager layout) {
	super(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
   
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	int w = getWidth();
	int h = getHeight();
	Color color1 = Colors.gradienPanelColor;
	Color color2 = getBackground();

	GradientPaint gp = new GradientPaint(0, 0, color1, 0, 200, color2);
	g2d.setPaint(gp);
	g2d.fillRect(0, 0, w, 200);

	g2d.setColor(color2);
	g2d.fillRect(0, 200, w, h - 400);

	g2d.setPaint(new GradientPaint(0, h - 200, color2, 0, h, color1));
	g2d.fillRect(0, h - 200, w, h);
    }
}
