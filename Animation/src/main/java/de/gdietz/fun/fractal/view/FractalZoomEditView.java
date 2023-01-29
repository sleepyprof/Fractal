package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.FractalZoomController;
import de.gdietz.fun.fractal.model.FractalZoomModel;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.gui.swing.JNumberCachedTextField;
import de.gdietz.util.NumberFormatHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class FractalZoomEditView extends JPanel implements FractalView {

	private final FractalZoomModel<?> model;
	private final FractalZoomController controller;

	private final JNumberCachedTextField textFromStartX;
	private final JNumberCachedTextField textFromStartY;
	private final JNumberCachedTextField textToStartX;
	private final JNumberCachedTextField textToStartY;
    private final JNumberCachedTextField textFromEndX;
    private final JNumberCachedTextField textFromEndY;
    private final JNumberCachedTextField textToEndX;
    private final JNumberCachedTextField textToEndY;
	private final JNumberCachedTextField textParamX;
	private final JNumberCachedTextField textParamY;

	private final JButton startButton;
	private final JButton stopButton;
	private final JButton aspectRatioButton;

    private class ControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==startButton)
				startFromControl();
			if (event.getSource()==stopButton)
				controller.stopCalculation();
			if (event.getSource()==aspectRatioButton)
				controller.correctAspectRatio(true);
		}
	}

	public FractalZoomEditView(FractalZoomModel<?> model, FractalZoomController controller) {
		super(new GridBagLayout());

		JPanel panelControlData = new JPanel(new GridBagLayout());

        NumberFormat format = NumberFormatHelper.getDefaultNumberFormat();
        format.setMaximumFractionDigits(10);

        textFromStartX = new JNumberCachedTextField(format);
		textFromStartY = new JNumberCachedTextField(format);
		textToStartX   = new JNumberCachedTextField(format);
		textToStartY   = new JNumberCachedTextField(format);
        textFromEndX   = new JNumberCachedTextField(format);
		textFromEndY   = new JNumberCachedTextField(format);
		textToEndX     = new JNumberCachedTextField(format);
		textToEndY     = new JNumberCachedTextField(format);
		textParamX     = new JNumberCachedTextField(format);
		textParamY     = new JNumberCachedTextField(format);

        addNumberRow(panelControlData, 0, "\u2199,0", textFromStartX, textFromStartY);
        addNumberRow(panelControlData, 1, "\u2197,0", textToStartX, textToStartY);
        addNumberRow(panelControlData, 2, "\u2199,1", textFromEndX, textFromEndY);
        addNumberRow(panelControlData, 3, "\u2197,1", textToEndX, textToEndY);
        addNumberRow(panelControlData, 4, "p", textParamX, textParamY);

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

    private void addNumberRow(JPanel panel, int y, String name, JTextField textX, JTextField textY) {
        int height = Math.max(textX.getMinimumSize().height, textY.getMinimumSize().height);
        textX.setPreferredSize(new Dimension(120, height));
        textY.setPreferredSize(new Dimension(120, height));
        textX.setMinimumSize(new Dimension(120, height));
        textY.setMinimumSize(new Dimension(120, height));

        JLabel labelX = new JLabel("<html><i>x</i><sub>" + name + "</sub></html>");
        JLabel labelY = new JLabel("<html><i>y</i><sub>" + name + "</sub></html>");
        labelX.setMinimumSize(new Dimension(50, height));
        labelY.setMinimumSize(new Dimension(50, height));

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
    }


	public void update() {
		textFromStartX.setValue(model.getCornerFromStart().getX());
		textFromStartY.setValue(model.getCornerFromStart().getY());
		textToStartX.  setValue(model.getCornerToStart().getX());
		textToStartY.  setValue(model.getCornerToStart().getY());
        textFromEndX.  setValue(model.getCornerFromEnd().getX());
        textFromEndY.  setValue(model.getCornerFromEnd().getY());
        textToEndX.    setValue(model.getCornerToEnd().getX());
        textToEndY.    setValue(model.getCornerToEnd().getY());
		textParamX.    setValue(model.getParameter().getX());
		textParamY.    setValue(model.getParameter().getY());

        stopButton.setEnabled(model.isCalculating());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		double fromStartX = textFromStartX.getDoubleValueCommit();
		double fromStartY = textFromStartY.getDoubleValueCommit();
		double toStartX   = textToStartX.getDoubleValueCommit();
		double toStartY   = textToStartY.getDoubleValueCommit();
        double fromEndX   = textFromEndX.getDoubleValueCommit();
        double fromEndY   = textFromEndY.getDoubleValueCommit();
        double toEndX     = textToEndX.getDoubleValueCommit();
        double toEndY     = textToEndY.getDoubleValueCommit();
		double paramX     = textParamX.getDoubleValueCommit();
		double paramY     = textParamY.getDoubleValueCommit();

        controller.setCorners(new Coordinate(fromStartX, fromStartY), new Coordinate(toStartX, toStartY),
                new Coordinate(fromEndX, fromEndY), new Coordinate(toEndX, toEndY));
		controller.setParameter(new Coordinate(paramX, paramY));
		controller.startCalculation();
	}

}
