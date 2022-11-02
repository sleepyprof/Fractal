package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.FractalController;
import de.gdietz.fun.fractal.model.FractalPreciseModel;
import de.gdietz.fun.fractal.util.BigCoordinate;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Observable;

public class FractalPreciseEditView extends JPanel implements FractalView {

	private final FractalPreciseModel<?> model;
	private final FractalController<BigCoordinate> controller;

	private final JNumberCachedTextField textFromX;
	private final JNumberCachedTextField textFromY;
	private final JNumberCachedTextField textToX;
	private final JNumberCachedTextField textToY;
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

	public FractalPreciseEditView(FractalPreciseModel<?> model, FractalController<BigCoordinate> controller) {
		super(new GridBagLayout());

		JPanel panelControlData = new JPanel(new GridBagLayout());

        textFromX  = new JNumberCachedTextField(true);
		textFromY  = new JNumberCachedTextField(true);
		textToX    = new JNumberCachedTextField(true);
		textToY    = new JNumberCachedTextField(true);
		textParamX = new JNumberCachedTextField(true);
		textParamY = new JNumberCachedTextField(true);

        addNumberRow(panelControlData, 0, "\u2199", textFromX, textFromY);
        addNumberRow(panelControlData, 1, "\u2197", textToX, textToY);
        addNumberRow(panelControlData, 2, "p", textParamX, textParamY);

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
        textX.setPreferredSize(new Dimension(200, height));
        textY.setPreferredSize(new Dimension(200, height));
        textX.setMinimumSize(new Dimension(200, height));
        textY.setMinimumSize(new Dimension(200, height));

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
		textFromX. setValue(model.getCornerFrom().getX());
		textFromY. setValue(model.getCornerFrom().getY());
		textToX.   setValue(model.getCornerTo().getX());
		textToY.   setValue(model.getCornerTo().getY());
		textParamX.setValue(model.getParameter().getX());
		textParamY.setValue(model.getParameter().getY());

        stopButton.setEnabled(model.isCalculating());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		BigDecimal fromX  = textFromX.getBigDecimalValueCommit();
		BigDecimal fromY  = textFromY.getBigDecimalValueCommit();
		BigDecimal toX    = textToX.getBigDecimalValueCommit();
		BigDecimal toY    = textToY.getBigDecimalValueCommit();
		BigDecimal paramX = textParamX.getBigDecimalValueCommit();
		BigDecimal paramY = textParamY.getBigDecimalValueCommit();

        controller.setCorners(new BigCoordinate(fromX, fromY), new BigCoordinate(toX, toY));
		controller.setParameter(new BigCoordinate(paramX, paramY));
		controller.startCalculation();
	}

}
