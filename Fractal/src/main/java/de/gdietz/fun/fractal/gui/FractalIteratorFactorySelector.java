package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Tuple;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class FractalIteratorFactorySelector<T extends Tuple<T>> extends JPanel implements ActionListener {

	private final JComboBox comboBoxFunc;
    private final JNumberCachedTextField textMaxiter;

    private final JButton setButton;

	private final FractalIteratorManager<T> listener;

    private class ControlPanelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int maxiter = textMaxiter.getIntValueCommit();
            if (event.getSource()==setButton) {
                listener.setMaxiter(maxiter);
            }
        }
    }

	protected static class FractalIteratorData<T extends Tuple<T>> {

        private final FractalIteratorFactory<T> iteratorFactory;
		private final int maxiter;

        private final String description;

        public FractalIteratorData(FractalIteratorFactory<T> iteratorFactory, int maxiter, String description) {
			this.iteratorFactory = iteratorFactory;
			this.maxiter = maxiter;
		    this.description = description;
        }

        public FractalIteratorFactory<T> getIteratorFactory() {
            return iteratorFactory;
        }

        public int getMaxiter() {
            return maxiter;
        }

        public String toString() {
            return description;
        }

    }

	public FractalIteratorFactorySelector(FractalIteratorManager<T> listener, boolean askMaxiter) {
		super(new GridBagLayout());

        JLabel labelFunc = new JLabel("f:");

		comboBoxFunc = new JComboBox();
        comboBoxFunc.addActionListener(this);

        NumberFormat format = NumberFormat.getInstance();
        format.setParseIntegerOnly(true);

        JLabel labelMaxiter = new JLabel("iter:");

        textMaxiter = new JNumberCachedTextField(format);

        textMaxiter.setPreferredSize(new Dimension(60, textMaxiter.getPreferredSize().height));

        setButton = new JButton("Set");
        setButton.addActionListener(new ControlPanelActionListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;

        add(labelFunc, c);
        add(comboBoxFunc, c);
        if (askMaxiter) {
            add(labelMaxiter, c);
            add(textMaxiter, c);
            add(setButton, c);
        }

		this.listener = listener;
	}

    public FractalIteratorFactorySelector(FractalIteratorManager<T> listener) {
        this(listener, false);
    }

    protected void addIteratorData(FractalIteratorData<T> iteratorData) {
        comboBoxFunc.addItem(iteratorData);
    }

	public void addIteratorFactory(FractalIteratorFactory<T> iteratorFactory, int maxiter, String description) {
		FractalIteratorData<T> iteratorData = new FractalIteratorData<T>(iteratorFactory, maxiter, description);
		addIteratorData(iteratorData);
	}

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBoxFunc) {
            @SuppressWarnings("unchecked")
            FractalIteratorData<T> iteratorData = (FractalIteratorData<T>) comboBoxFunc.getSelectedItem();
            listener.setIteratorData(iteratorData.getIteratorFactory(), iteratorData.getMaxiter());
            textMaxiter.setValue(iteratorData.maxiter);
        }

    }

}
