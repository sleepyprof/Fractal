package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public abstract class FractalWindow extends JFrame {

    final String title;
    final ImageIcon icon;

    protected FractalWindow(String title) {
        super(title);
        this.title = title;
        URL iconURL = ClassLoader.getSystemResource("resources/fractal.png");
        icon = iconURL == null ? null : new ImageIcon(ClassLoader.getSystemResource("resources/fractal.png"));
        if (icon != null)
            this.setIconImage(icon.getImage());
    }

    public void exit() {
        processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public abstract SaveableView getSaveableView();

    public SaveableView getSaveableViewOther() {
        return null;
    }

    protected String getHelp() {
        return "<b>Help</b><br><br>" +
                "General help:<br>" +
                "In most cases the parameters represent complex numbers.<br>" +
                "\u2199 and \u2197 denote the corners, while p denotes the parameter.<br>" +
                "The parameter is normally z<sub>0</sub> for Mandelbrot sets and c for Julia sets<br>" +
                "f stands for the formula to use. Just select the formula you want to be used.<br>" +
                "Selecting \"custom\" in this list opens an editor for a formula.<br>" +
                "Currently this is a postfix-stack notation with comma-separated actions.<br>" +
                "Example: \"z,^2,c,+\" stands for z<sup>2</sup>+c.<br>" +
                "\"p-custom\" is similar, but interprets the symbol p as the parameter (for animations).<br><br>" +
                "Some calculations can take some time (especially the precision modes). Be patient.<br>" +
                "The endless mode iterates endlessly and improves the picture over time.<br><br>" +
                "For the 2D picture or animation modes:<br>" +
                "Hold the left mouse button and drag it to another point to zoom into the picture.<br><br>" +
                "For the 2D two picture modes:<br>" +
                "Click the right mouse button to set the parameter for the other picture.<br><br>" +
                "For the 2D animation plus picture modes:<br>" +
                "Click the right mouse button in the animation to set the parameter for the picture.<br>" +
                "Hold and drag the right mouse button in the picture to set the parameters for the animation.<br><br>" +
                "For the 3D mode:<br>" +
                "Drag left mouse button rotates, drag right mouse button moves, drag middle mouse button zooms.<br>" +
                "Click right mouse button to zoom into the body (new calculation).";
    }

    protected String getCopyright() {
        return "<b>About Fractal(3D)</b><br><br>" +
                "(C) 2009/2010 by Dr. Gunnar Dietz<br>" +
                "Surrey International Institute / International Education Centre<br>" +
                "Dongbei University of Finance and Economics<br>" +
                "<br>" +
                "<font size=-1>In memory of the Java Course CS0801/CS0802/CS0803</font>";
    }

    public void showHelp() {
        JOptionPane.showMessageDialog(this, "<html>" + getHelp() + "</html>", title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    public void showCopyright() {
        JOptionPane.showMessageDialog(this, "<html>" + getCopyright() + "</html>", title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

}
