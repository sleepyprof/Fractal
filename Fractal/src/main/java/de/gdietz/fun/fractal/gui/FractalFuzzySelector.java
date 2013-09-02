package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalFuzzyController;
import de.gdietz.fun.fractal.model.FractalModel;
import de.gdietz.fun.fractal.view.FractalView;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class FractalFuzzySelector extends JPanel implements FractalView {

    private final FractalFuzzyController controller;

    private final JLabel labelLambda;
    private final JLabel labelEpsilon;

	private final JNumberCachedTextField textLambda;
	private final JNumberCachedTextField textEpsilon;

	private final JButton setButton;

	private class ControlPanelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==setButton)
				startFromControl();
		}
	}

	public FractalFuzzySelector(FractalModel model, FractalFuzzyController controller, boolean showEpsilon) {
		super(new GridBagLayout());

        labelLambda = new JLabel("\u03bb:");
        labelEpsilon = new JLabel("\u03b5:");

        NumberFormat formatLambda = NumberFormat.getInstance();
        formatLambda.setMaximumFractionDigits(1);

        textLambda = new JNumberCachedTextField(formatLambda);
        textEpsilon = new JNumberCachedTextField(null);

        textLambda.setPreferredSize(new Dimension(80, textLambda.getPreferredSize().height));
        textEpsilon.setPreferredSize(new Dimension(80, textEpsilon.getPreferredSize().height));

		setButton = new JButton("Set");
		setButton.addActionListener(new ControlPanelActionListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(labelLambda, c);
        add(textLambda, c);
        if (showEpsilon) {
            add(labelEpsilon, c);
            add(textEpsilon);
        }
        add(setButton, c);

		this.controller = controller;
        model.addObserver(this);
		update();
	}

    public FractalFuzzySelector(FractalModel model, FractalFuzzyController controller) {
        this(model, controller, false);
    }

	public void update() {
		textLambda.setValue(controller.getLambda());
		textEpsilon.setValue(controller.getEpsilon());
        boolean isFuzzy = controller.isFuzzy();
        labelLambda.setEnabled(isFuzzy);
        labelEpsilon.setEnabled(isFuzzy);
        textLambda.setEnabled(isFuzzy);
        textLambda.setEnabled(isFuzzy);
        setButton.setEnabled(isFuzzy);
	}

	public void update(Observable o, Object arg) {
		update();
	}

	private void startFromControl() {
		double lambda = textLambda.getDoubleValueCommit();
		double epsilon = textEpsilon.getDoubleValueCommit();

        controller.setLambda(lambda);
        controller.setEpsilon(epsilon);
	}

}
