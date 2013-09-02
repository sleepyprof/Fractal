package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.FractalAnimController;
import de.gdietz.fun.fractal.model.FractalAnimModel;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.util.Observable;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class FractalAnimEditView extends JPanel implements FractalView {

	private final FractalAnimModel<?> model;
	private final FractalAnimController controller;

	private final JNumberCachedTextField textFromX;
	private final JNumberCachedTextField textFromY;
	private final JNumberCachedTextField textToX;
	private final JNumberCachedTextField textToY;
	private final JNumberCachedTextField textParamFromX;
	private final JNumberCachedTextField textParamFromY;
    private final JNumberCachedTextField textParamToX;
    private final JNumberCachedTextField textParamToY;

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

	public FractalAnimEditView(FractalAnimModel<?> model, FractalAnimController controller) {
		super(new GridBagLayout());

		JPanel panelControlData = new JPanel(new GridBagLayout());

        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(10);

        textFromX      = new JNumberCachedTextField(format);
		textFromY      = new JNumberCachedTextField(format);
		textToX        = new JNumberCachedTextField(format);
		textToY        = new JNumberCachedTextField(format);
		textParamFromX = new JNumberCachedTextField(format);
		textParamFromY = new JNumberCachedTextField(format);
        textParamToX   = new JNumberCachedTextField(format);
        textParamToY   = new JNumberCachedTextField(format);

        addNumberRow(panelControlData, 0, "\u2199", textFromX, textFromY);
        addNumberRow(panelControlData, 1, "\u2197", textToX, textToY);
        addNumberRow(panelControlData, 2, "p\u2199", textParamFromX, textParamFromY);
        addNumberRow(panelControlData, 3, "p\u2197", textParamToX, textParamToY);

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
		textFromX.     setValue(model.getCornerFrom().getX());
		textFromY.     setValue(model.getCornerFrom().getY());
		textToX.       setValue(model.getCornerTo().getX());
		textToY.       setValue(model.getCornerTo().getY());
		textParamFromX.setValue(model.getParamFrom().getX());
		textParamFromY.setValue(model.getParamFrom().getY());
        textParamToX.  setValue(model.getParamTo().getX());
        textParamToY.  setValue(model.getParamTo().getY());

        stopButton.setEnabled(model.isCalculating());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		double fromX      = textFromX.getDoubleValueCommit();
		double fromY      = textFromY.getDoubleValueCommit();
		double toX        = textToX.getDoubleValueCommit();
		double toY        = textToY.getDoubleValueCommit();
		double paramFromX = textParamFromX.getDoubleValueCommit();
		double paramFromY = textParamFromY.getDoubleValueCommit();
        double paramToX   = textParamToX.getDoubleValueCommit();
        double paramToY   = textParamToY.getDoubleValueCommit();

        controller.setCorners(new Coordinate(fromX, fromY), new Coordinate(toX, toY));
		controller.setParameters(new Coordinate(paramFromX, paramFromY), new Coordinate(paramToX, paramToY));
		controller.startCalculation();
	}

}
