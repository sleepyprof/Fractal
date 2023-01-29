package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal3DController;
import de.gdietz.fun.fractal.model.Fractal3DModel;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.gui.swing.JNumberCachedTextField;
import de.gdietz.util.NumberFormatHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class Fractal3DEditView extends JPanel implements FractalView {

    private final Fractal3DModel model;
    private final Fractal3DController controller;

    private final JNumberCachedTextField textFromX;
    private final JNumberCachedTextField textFromY;
    private final JNumberCachedTextField textFromZ;
    private final JNumberCachedTextField textToX;
    private final JNumberCachedTextField textToY;
    private final JNumberCachedTextField textToZ;
    private final JNumberCachedTextField textParamX;
    private final JNumberCachedTextField textParamY;
    private final JNumberCachedTextField textParamZ;

    private final JButton startButton;
    private final JButton stopButton;
    private final JButton aspectRatioButton;

    private class ControlPanelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == startButton)
                startFromControl();
            if (event.getSource() == stopButton)
                controller.stopCalculation();
            if (event.getSource() == aspectRatioButton)
                controller.correctAspectRatio(true);
        }
    }

    public Fractal3DEditView(Fractal3DModel model, Fractal3DController controller) {
        super(new GridBagLayout());

        JPanel panelControlData = new JPanel(new GridBagLayout());

        NumberFormat format = NumberFormatHelper.getDefaultNumberFormat();
        format.setMaximumFractionDigits(10);

        textFromX = new JNumberCachedTextField(format);
        textFromY = new JNumberCachedTextField(format);
        textFromZ = new JNumberCachedTextField(format);
        textToX = new JNumberCachedTextField(format);
        textToY = new JNumberCachedTextField(format);
        textToZ = new JNumberCachedTextField(format);
        textParamX = new JNumberCachedTextField(format);
        textParamY = new JNumberCachedTextField(format);
        textParamZ = new JNumberCachedTextField(format);

        addNumberRow(panelControlData, 0, "\u2199", textFromX, textFromY, textFromZ);
        addNumberRow(panelControlData, 1, "\u2197", textToX, textToY, textToZ);
        addNumberRow(panelControlData, 2, "p", textParamX, textParamY, textParamZ);

        startButton = new JButton("Start");
        startButton.addActionListener(new ControlPanelActionListener());
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ControlPanelActionListener());
        aspectRatioButton = new JButton("Aspect Ratio");
        aspectRatioButton.addActionListener(new ControlPanelActionListener());

        CalculationProgressBar progressBar = new CalculationProgressBar();
        progressBar.setPreferredSize(new Dimension(20, progressBar.getPreferredSize().height));

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.NONE;
        add(panelControlData, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        add(startButton, c);
        c.weightx = 0.3;
        add(progressBar);
        c.weightx = 0.0;
        add(stopButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(aspectRatioButton, c);

        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        model.addCalculationListener(progressBar);
        update();
    }

    private void addNumberRow(JPanel panel, int y, String name, JTextField textX, JTextField textY, JTextField textZ) {
        int height = Math.max(Math.max(textX.getMinimumSize().height, textY.getMinimumSize().height),
                textZ.getMinimumSize().height);
        textX.setPreferredSize(new Dimension(120, height));
        textY.setPreferredSize(new Dimension(120, height));
        textZ.setPreferredSize(new Dimension(120, height));
        textX.setMinimumSize(new Dimension(50, height));
        textY.setMinimumSize(new Dimension(50, height));
        textZ.setMinimumSize(new Dimension(50, height));

        JLabel labelX = new JLabel("<html><i>x</i><sub>" + name + "</sub></html>");
        JLabel labelY = new JLabel("<html><i>y</i><sub>" + name + "</sub></html>");
        JLabel labelZ = new JLabel("<html><i>z</i><sub>" + name + "</sub></html>");
        labelX.setMinimumSize(new Dimension(50, height));
        labelY.setMinimumSize(new Dimension(50, height));
        labelZ.setMinimumSize(new Dimension(50, height));

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 0.5;
        c.gridy = y;
        c.gridx = 0;
        panel.add(labelX, c);
        c.weightx = 1.0;
        c.gridx++;
        panel.add(textX, c);
        c.weightx = 0.5;
        c.gridx++;
        panel.add(labelY, c);
        c.weightx = 1.0;
        c.gridx++;
        panel.add(textY, c);
        c.weightx = 0.5;
        c.gridx++;
        panel.add(labelZ, c);
        c.weightx = 1.0;
        c.gridx++;
        panel.add(textZ, c);
    }

    public void update() {
        textFromX.setValue(model.getCornerFrom().getX());
        textFromY.setValue(model.getCornerFrom().getY());
        textFromZ.setValue(model.getCornerFrom().getZ());
        textToX.setValue(model.getCornerTo().getX());
        textToY.setValue(model.getCornerTo().getY());
        textToZ.setValue(model.getCornerTo().getZ());
        textParamX.setValue(model.getParameter().getX());
        textParamY.setValue(model.getParameter().getY());
        textParamZ.setValue(model.getParameter().getZ());

        stopButton.setEnabled(model.isCalculating());
    }

    public void update(Observable o, Object arg) {
        update();
    }

    private void startFromControl() {
        double fromX = textFromX.getDoubleValueCommit();
        double fromY = textFromY.getDoubleValueCommit();
        double fromZ = textFromZ.getDoubleValueCommit();
        double toX = textToX.getDoubleValueCommit();
        double toY = textToY.getDoubleValueCommit();
        double toZ = textToZ.getDoubleValueCommit();
        double paramX = textParamX.getDoubleValueCommit();
        double paramY = textParamY.getDoubleValueCommit();
        double paramZ = textParamZ.getDoubleValueCommit();

        controller.setCorners(new Coordinate3D(fromX, fromY, fromZ), new Coordinate3D(toX, toY, toZ));
        controller.setParameter(new Coordinate3D(paramX, paramY, paramZ));
        controller.startCalculation();
    }

}
