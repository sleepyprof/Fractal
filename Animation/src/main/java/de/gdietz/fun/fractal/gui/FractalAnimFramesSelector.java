package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.SizeAnimController;
import de.gdietz.fun.fractal.model.SizeAnimModel;
import de.gdietz.fun.fractal.view.FractalView;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class FractalAnimFramesSelector extends JPanel implements FractalView {

	private final SizeAnimModel model;
	private final SizeAnimController controller;

	private final JNumberCachedTextField textFrames;

	private final JButton setButton;

	private class ControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==setButton)
				startFromControl();
		}
	}

	public FractalAnimFramesSelector(SizeAnimModel model, SizeAnimController controller) {
		super(new GridBagLayout());

        JLabel labelFrames = new JLabel("Frames:");

        NumberFormat format = NumberFormat.getInstance();
        format.setParseIntegerOnly(true);

        textFrames = new JNumberCachedTextField(format);

        textFrames.setPreferredSize(new Dimension(80, textFrames.getPreferredSize().height));

		setButton = new JButton("Set");
		setButton.addActionListener(new ControlPanelActionListener());


        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(labelFrames);
        add(textFrames);
        add(setButton);

		this.model = model;
		this.controller = controller;
		model.addObserver(this);
		update();
	}

	public void update() {
		textFrames.setValue(model.getFrames());
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		int frames = textFrames.getIntValueCommit();

        controller.setFrames(frames);
	}

}
