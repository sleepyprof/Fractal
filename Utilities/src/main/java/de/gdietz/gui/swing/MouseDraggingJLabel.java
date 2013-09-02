package de.gdietz.gui.swing;

import javax.swing.*;
import java.awt.*;

public class MouseDraggingJLabel extends JLabel implements MouseDraggingListener {

    private boolean draggingRect = false;
    private boolean draggingRight;
    private int fromX, fromY, toX, toY;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (draggingRect) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.darkGray);
            if (draggingRight)
                g2d.drawLine(fromX, fromY, toX, toY);
            else
                g2d.drawRect(Math.min(fromX, toX), Math.min(fromY, toY), Math.abs(toX - fromX), Math.abs(toY - fromY));
        }
    }

    public void updateRect(int fromX, int fromY, int toX, int toY, boolean right) {
        draggingRect = true;
        draggingRight = right;
        this.fromX = fromX; this.fromY = fromY;
        this.toX = toX; this.toY = toY;
        repaint();
    }

    public void removeRect() {
        draggingRect = false;
        repaint();
    }

}
