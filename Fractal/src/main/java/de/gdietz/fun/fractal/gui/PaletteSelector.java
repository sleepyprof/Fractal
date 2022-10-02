package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.palette.RandomPalette;
import de.gdietz.fun.fractal.palette.TransformedPalette;
import de.gdietz.fun.fractal.view.PaletteView;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.Random;

public class PaletteSelector extends JPanel implements ActionListener {

	private final JButton buttonNew;
    private final JNumberCachedTextField textSeed;
	private final JNumberCachedTextField textFieldFactor;
    private final JComboBox<TransformedPalette.Form> comboBoxForm;

	private final PaletteView listener;

	private Palette palette;

    private long seed;

    private TransformedPalette.Form form;
    private float factor;

	public PaletteSelector(PaletteView listener, boolean showSeed) {
		super(new GridBagLayout());

		buttonNew = new JButton("New Palette");
		buttonNew.addActionListener(this);

        JLabel labelSeed = new JLabel("seed:");

        NumberFormat formatSeed = NumberFormat.getInstance();
        formatSeed.setParseIntegerOnly(true);
        formatSeed.setMinimumIntegerDigits(5);
        formatSeed.setGroupingUsed(false);

        textSeed = new JNumberCachedTextField(formatSeed);
        textSeed.addActionListener(this);

        JLabel labelFactor = new JLabel("speed:");

        NumberFormat formatFactor = NumberFormat.getInstance();
        formatFactor.setMinimumFractionDigits(1);

        textFieldFactor = new JNumberCachedTextField(formatFactor);
		textFieldFactor.addActionListener(this);

        comboBoxForm = new JComboBox<>(TransformedPalette.Form.values());
        comboBoxForm.setRenderer(new FormListCellRenderer());
        comboBoxForm.addActionListener(this);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		add(buttonNew, c);
        if (showSeed) {
            add(labelSeed, c);
            add(textSeed, c);
        }
        add(labelFactor, c);
		add(textFieldFactor, c);
        add(comboBoxForm, c);

        Random random = new Random();
        seed = random.nextInt(90000) + 1;
		palette = new RandomPalette(seed);
        
		form = TransformedPalette.Form.LOGARITHMIC;
		factor = 1.0f;

		update();
        this.listener = listener;
	}

    public PaletteSelector(PaletteView listener) {
        this(listener, true);
    }

	public void update() {
        if (listener != null)
            listener.setPalette(getPalette());

        textSeed.setValue(seed);
		textFieldFactor.setValue(factor);
        comboBoxForm.setSelectedItem(form);
	}

	public void actionPerformed(ActionEvent e) {
        long oldSeed = seed;
        if (e.getSource() == buttonNew || e.getSource() == textSeed)
            seed = textSeed.getLongValueCommit();

        if (e.getSource() == buttonNew && seed == oldSeed && seed != 0)
            seed++;

		if (seed != oldSeed || seed == 0 && (e.getSource() == buttonNew || e.getSource() == textSeed))
            palette = (seed == 0) ? new RandomPalette() : new RandomPalette(seed);

		factor = textFieldFactor.getFloatValueCommit();
		if (factor == 0.0f)
			factor = 1.0f;
        form = (TransformedPalette.Form)comboBoxForm.getSelectedItem();

		update();
	}

	public Palette getPalette() {
        if (form == TransformedPalette.Form.LINEAR && factor == 1.0f)
            return palette;
        return new TransformedPalette(palette, form, factor);
	}

	public void setPalette(Palette palette) {
        seed = 0;
		this.palette = palette;
		factor = 1.0f;
        form = TransformedPalette.Form.LINEAR;
		listener.setPalette(palette);
		update();
	}

    public void setForm(TransformedPalette.Form form) {
        this.form = form;
        update();
    }

    public void setFactor(float factor) {
        this.factor = (factor == 0.0f) ? 1.0f : factor;
        update();
    }


    private static class FormListCellRenderer extends JLabel implements ListCellRenderer<TransformedPalette.Form> {

        private FormListCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(JList<? extends TransformedPalette.Form> list, TransformedPalette.Form value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            Color background = isSelected ? list.getSelectionBackground() : list.getBackground();
            Color foreground = isSelected ? list.getSelectionForeground() : list.getForeground();

            setBackground(background);
            setForeground(foreground);

            Dimension prefsize = getPreferredSize();
            int size = Math.max(10, Math.min(prefsize.width, prefsize.height));

            Icon icon = new ImageIcon(getImage(value, size, foreground));

            setIcon(icon);
            setText(" ");

            return this;
        }

        public BufferedImage getImage(TransformedPalette.Form form, int size, Color color) {
            BufferedImage image = new BufferedImage(2*size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D)image.getGraphics();
            g.setColor(color);

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for(int index = 0; index < 2*size; index++) {
                int val = form.get(10*index, form.getBaseFactor());
                min = Math.min(min, val);
                max = Math.max(max, val);
            }
            max = Math.max(max, min+1);

            for(int index = 1; index < 2*size; index++) {
                int val0 = form.get(10*(index-1), form.getBaseFactor());
                int val1 = form.get(10*index, form.getBaseFactor());
                int v0 = Math.round((float)(size-1) * (val0 - min) / (max - min));
                int v1 = Math.round((float)(size-1) * (val1 - min) / (max - min)); 
                g.drawLine(index-1, size-1 - v0, index, size-1 - v1);
            }

            return image;
        }

    }

}
