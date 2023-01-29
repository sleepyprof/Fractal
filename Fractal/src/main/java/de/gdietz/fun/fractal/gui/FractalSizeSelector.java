package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.Size2DController;
import de.gdietz.fun.fractal.model.Size2DModel;
import de.gdietz.gui.swing.JNumberCachedTextField;
import de.gdietz.fun.fractal.view.FractalView;
import de.gdietz.util.NumberFormatHelper;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Observable;

public class FractalSizeSelector extends JPanel implements FractalView {

	private final Size2DModel model;
	private final Size2DController controller;
    private final Size2DController controllerOther;

	private final JNumberCachedTextField textWidth;
	private final JNumberCachedTextField textHeight;

	private final JButton setButton;

	private class ControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==setButton)
				startFromControl();
		}
	}

	public FractalSizeSelector(Size2DModel model, Size2DController controller, Size2DController controllerOther) {
		super(new GridBagLayout());

        JLabel labelSize = new JLabel("Size:");

        NumberFormat format = NumberFormatHelper.getDefaultNumberFormat();
        format.setParseIntegerOnly(true);

        textWidth = new JNumberCachedTextField(format);
		textHeight = new JNumberCachedTextField(format);

        textWidth.setPreferredSize(new Dimension(80, textWidth.getPreferredSize().height));
        textHeight.setPreferredSize(new Dimension(80, textHeight.getPreferredSize().height));

		setButton = new JButton("Set");
		setButton.addActionListener(new ControlPanelActionListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(labelSize, c);
        add(textWidth, c);
        add(textHeight, c);
        add(setButton, c);

		this.model = model;
		this.controller = controller;
        this.controllerOther = controllerOther;
		model.addObserver(this);
		update();
	}

    public FractalSizeSelector(Size2DModel model, Size2DController controller) {
        this(model, controller, null);
    }

	public void update() {
		textWidth.setValue(model.getWidth());
		textHeight.setValue(model.getHeight());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		int width = textWidth.getIntValueCommit();
		int height = textHeight.getIntValueCommit();

        controller.setSize(width, height);
        if(controllerOther != null)
            controllerOther.setSize(width, height);
	}

}
