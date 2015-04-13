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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author Stanisalv
 */
public class AquaBarTabbedPaneUI extends BasicTabbedPaneUI {

    private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
    private final ColorSet selectedColorSet;
    private final ColorSet defaultColorSet;
    private final ColorSet hoverColorSet;
    private final Color lineColor = Colors.aquaTabbedPaneLineColor;
    private final Color dividerColor = Colors.aquaTabbedPaneDeviderColor;
    private Insets contentInsets = new Insets(10, 10, 10, 10);
    private int lastRollOverTab = -1;

    public static ComponentUI createUI(JComponent c) {
        return new AquaBarTabbedPaneUI();
    }

    public AquaBarTabbedPaneUI() {

        selectedColorSet = new ColorSet();

        selectedColorSet.bottomGradColor1 = UIManager.getDefaults().getColor("List.selectionBackground");
        selectedColorSet.bottomGradColor2 = UIManager.getDefaults().getColor("List.selectionBackground");

        defaultColorSet = new ColorSet();
        defaultColorSet.topGradColor1 = Colors.auqaTabbedPaneGradientColor1;
        defaultColorSet.topGradColor2 = Colors.auqaTabbedPaneGradientColor1;

        defaultColorSet.bottomGradColor1 = Colors.auqaTabbedPaneGradientColor1;
        defaultColorSet.bottomGradColor2 = Colors.auqaTabbedPaneGradientColor1;

        hoverColorSet = new ColorSet();

        hoverColorSet.bottomGradColor1 = Colors.auqaTabbedPaneGradientColor1;
        hoverColorSet.bottomGradColor2 = Colors.auqaTabbedPaneGradientColor2;

        maxTabHeight = 20;

        contentInsets = new Insets(0, 0, 0, 0);
    }

    @Override
    public int getTabRunCount(JTabbedPane pane) {
        return 1;
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        RollOverListener l = new RollOverListener();
        tabPane.addMouseListener(l);
        tabPane.addMouseMotionListener(l);

        tabAreaInsets = NO_INSETS;
        tabInsets = new Insets(0, 0, 0, 1);

    }

    protected boolean scrollableTabLayoutEnabled() {
        return false;
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return contentInsets;
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex,
            int fontHeight) {
        return 21;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex,
            FontMetrics metrics) {
        int w = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
        int wid = metrics.charWidth('M');
        w += wid * 2;
        return w;
    }

    @Override
    protected int calculateMaxTabHeight(int tabPlacement) {
        return 21;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2d = (Graphics2D) g;

        int w = tabPane.getWidth();
        int h = tabPane.getHeight();

        Color color1 = Colors.gradienPanelColor;
        Color color2 = tabPane.getBackground();

        g2d.setPaint(new GradientPaint(0, h - 200, color2, 0, h, color1));
        g2d.fillRect(0, h - 200, w, h);

        super.paintTabArea(g, tabPlacement, selectedIndex);

    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        ColorSet colorSet;

        Rectangle rect = rects[tabIndex];

        if (isSelected) {
            colorSet = selectedColorSet;
        } else if (getRolloverTab() == tabIndex) {
            colorSet = hoverColorSet;
        } else {
            colorSet = defaultColorSet;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = rect.width;
        int xpos = rect.x;

        g2d.setPaint(new GradientPaint(xpos, rect.y, colorSet.bottomGradColor1, xpos + rect.width / 2, rect.y, colorSet.bottomGradColor2));
        g2d.fillRect(xpos, rect.y, width / 2, rect.height);

        g2d.setPaint(new GradientPaint(xpos + rect.width / 2, rect.y, colorSet.bottomGradColor2, xpos + rect.width, rect.y, colorSet.bottomGradColor1));
        g2d.fillRect(xpos + width / 2, rect.y, width / 2 - 2, rect.height);

        g2d.setColor(lineColor);
        g2d.drawLine(rect.x, rect.y, rect.x + rect.width - 1, rect.y);

        Color color1 = Colors.gradienPanelColor;

        g2d.fillRect(rect.width - 1, 0, rect.width - 1, tabPane.getHeight() - 200);
        g2d.fillRect(1, 0, 1, tabPane.getHeight() - 200);

        g2d.setPaint(new GradientPaint(0, tabPane.getHeight() - 200, lineColor, 0, tabPane.getHeight(), color1));
        g2d.fillRect(rect.width - 1, tabPane.getHeight() - 200, rect.width - 1, 200);
        g2d.fillRect(1, tabPane.getHeight() - 200, 1, 200);

    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
        Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
        g.setColor(dividerColor);
        g.drawLine(rect.x + rect.width, 0, rect.x + rect.width, 20);
    }

    @Override
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
            int selectedIndex, int x, int y, int w, int h) {
    }

    @Override
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,
            int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,
            int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,
            int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement,
            Rectangle[] rects, int tabIndex, Rectangle iconRect,
            Rectangle textRect, boolean isSelected) {

    }

    @Override
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex,
            boolean isSelected) {
        return 0;
    }

    private class ColorSet {

        Color topGradColor1;
        Color topGradColor2;
        Color bottomGradColor1;
        Color bottomGradColor2;
    }

    private class RollOverListener implements MouseMotionListener,
            MouseListener {

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            checkRollOver();
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            checkRollOver();
        }

        public void mouseExited(MouseEvent e) {
            tabPane.repaint();
        }

        private void checkRollOver() {
            int currentRollOver = getRolloverTab();
            if (currentRollOver != lastRollOverTab) {
                lastRollOverTab = currentRollOver;
                Rectangle tabsRect = new Rectangle(0, 0, tabPane.getWidth(), tabPane.getHeight());
                tabPane.repaint(tabsRect);
            }
        }
    }
}
