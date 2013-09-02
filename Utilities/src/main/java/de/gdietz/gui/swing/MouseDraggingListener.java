package de.gdietz.gui.swing;

public interface MouseDraggingListener {

    public void updateRect(int fromX, int fromY, int toX, int toY, boolean right);
    public void removeRect();

}
