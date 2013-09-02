package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Tuple;
import de.gdietz.fun.fractal.view.PictureCoordMapper;
import de.gdietz.gui.swing.MouseDraggingListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Fractal2DMouseController<T extends Tuple<T>> extends MouseAdapter {

    private final FractalPairedController<T> controller;
    private final PictureCoordMapper<T> mapper;
    private final MouseDraggingListener listener;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private int x;
    private int y;

    public Fractal2DMouseController(FractalPairedController<T> controller, PictureCoordMapper<T> mapper, MouseDraggingListener listener) {
        this.controller = controller;
        this.mapper = mapper;
        this.listener = listener;
    }

    public Fractal2DMouseController(FractalPairedController<T> controller, PictureCoordMapper<T> mapper) {
        this(controller, mapper, null);
    }

    public void mousePressed(MouseEvent event) {
        x = event.getX();
        y = event.getY();
        T c = mapper.getCoordinate(x, y);
        if (!rightPressed && event.getButton()==MouseEvent.BUTTON1) {
            leftPressed = true;
            controller.setPoint(c);
        } else if (!leftPressed && event.getButton()==MouseEvent.BUTTON3) {
            rightPressed = true;
            controller.setPointOther(c);
        }
    }

    public void mouseDragged(MouseEvent event) {
        int x2 = event.getX();
        int y2 = event.getY();
        T c = mapper.getCoordinate(x2, y2);
        if (leftPressed)
            controller.dragPoint(c);
        if (rightPressed)
            controller.dragPointOther(c);
        if (listener != null)
            listener.updateRect(x, y, x2, y2, rightPressed);
    }

    public void mouseReleased(MouseEvent event) {
        int x2 = event.getX();
        int y2 = event.getY();
        T from = mapper.getCoordinate(x, y);
        T to = mapper.getCoordinate(x2, y2);
        if (leftPressed && event.getButton()==MouseEvent.BUTTON1) {
            leftPressed = false;
            if (x != x2 || y != y2)
                controller.setCorners(from, to);
        } else if (rightPressed && event.getButton()==MouseEvent.BUTTON3) {
            rightPressed = false;
            if (x != x2 || y != y2)
                controller.setCornersOther(from, to);
        }
        if (listener != null)
            listener.removeRect();
    }

}
