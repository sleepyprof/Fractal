package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.Fractal3DController;
import de.gdietz.fun.fractal.model.Fractal3DModel;
import de.gdietz.fun.fractal.view.FractalView;
import de.gdietz.gui.swing.JNumberCachedTextField;
import de.gdietz.util.NumberFormatHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class Fractal3DSizeSelector extends JPanel implements FractalView {

	private final Fractal3DModel model;
	private final Fractal3DController controller;

	private final JNumberCachedTextField textSize;

	private final JButton setButton;

	private class ControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==setButton)
				startFromControl();
		}
	}

	public Fractal3DSizeSelector(Fractal3DModel model, Fractal3DController controller) {
		super(new GridBagLayout());

        JLabel labelSize = new JLabel("Size:");

        NumberFormat format = NumberFormatHelper.getDefaultNumberFormat();
        format.setParseIntegerOnly(true);

        textSize = new JNumberCachedTextField(format);

        textSize.setPreferredSize(new Dimension(80, textSize.getPreferredSize().height));

		setButton = new JButton("Set");
		setButton.addActionListener(new ControlPanelActionListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(labelSize, c);
        add(textSize, c);
        add(setButton, c);

		this.model = model;
		this.controller = controller;
		model.addObserver(this);
		update();
	}

	public void update() {
		textSize.setValue(model.getSize());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		int size = textSize.getIntValueCommit();

        controller.setSize(size);
	}

}
