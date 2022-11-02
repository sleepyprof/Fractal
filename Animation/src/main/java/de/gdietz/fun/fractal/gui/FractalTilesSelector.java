package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalTilesController;
import de.gdietz.fun.fractal.controller.SizeTilesController;
import de.gdietz.fun.fractal.model.FractalTilesCanvas;
import de.gdietz.fun.fractal.model.FractalTilesModel;
import de.gdietz.fun.fractal.view.FractalView;
import de.gdietz.gui.swing.JNumberCachedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;

public class FractalTilesSelector extends JPanel implements FractalView {

    private final FractalTilesCanvas model;
    private final SizeTilesController controller;

    private final JNumberCachedTextField textTilesX;
    private final JNumberCachedTextField textTilesY;

    private final JButton setButton;

    private class ControlPanelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource()==setButton)
                startFromControl();
        }
    }

    public FractalTilesSelector(FractalTilesModel<?> model, FractalTilesController controller) {
        super(new GridBagLayout());

        JLabel labelTiles = new JLabel("Tiles:");

        NumberFormat format = NumberFormat.getInstance();
        format.setParseIntegerOnly(true);

        textTilesX = new JNumberCachedTextField(format);
        textTilesY = new JNumberCachedTextField(format);

        textTilesX.setPreferredSize(new Dimension(80, textTilesX.getPreferredSize().height));
        textTilesY.setPreferredSize(new Dimension(80, textTilesY.getPreferredSize().height));

        setButton = new JButton("Set");
        setButton.addActionListener(new ControlPanelActionListener());


        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(labelTiles);
        add(textTilesX);
        add(textTilesY);
        add(setButton);

        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        update();
    }

    public void update() {
        textTilesX.setValue(model.getTilesX());
        textTilesY.setValue(model.getTilesY());
    }

    public void update(Observable o, Object arg) {
        update();
    }

    private void startFromControl() {
        int tilesX = textTilesX.getIntValueCommit();
        int tilesY = textTilesY.getIntValueCommit();
        controller.setTiles(tilesX, tilesY);
    }

}
