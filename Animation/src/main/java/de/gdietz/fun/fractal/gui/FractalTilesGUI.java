package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.*;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class FractalTilesGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

    private final FractalTilesIterateController controller;

    private Palette palette;

    private final FractalPictureViewBase<Coordinate> pictureView;

    private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public FractalTilesGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int tileWidth, int tileHeight, int tilesX, int tilesY,
                           Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo, boolean julia, boolean resizeable) {
        super(new GridBagLayout());

        FractalTilesIterateModel<Integer> model = new FractalTilesSimpleIterateModel(tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo, iteratorFactory, maxiter, julia);

        controller = new FractalTilesSimpleController(model);
        FractalTilesPairedController pairedController = new FractalTilesPairedControllerImpl(controller);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

        PaletteSelector paletteSelector = new PaletteSelector(this);
        palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalPicture<Coordinate> picture = new FractalTilesPicture<>(model, coloring);
        pictureView = resizeable ? new FractalScaledPictureView<>(picture, pairedController) :
                new FractalPictureView<>(picture, pairedController);
        JComponent controlView = new FractalTilesEditView(model, controller);
        JPanel panel = getPanel(pictureView, controlView);

        iteratorSelector = AdvancedFractalIteratorFactorySelector.create(Coordinate.class, this, true);
        iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, "default");

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(panel, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        add(paletteSelector, c);
        add(iteratorSelector, c);

        if (resizeable) {
            FractalSizeSelector sizeSelector = new FractalSizeSelector(model, controller);
            add(sizeSelector, c);
        }

        FractalTilesSelector tilesSelector = new FractalTilesSelector(model, controller);
        add(tilesSelector, c);

        controller.startCalculation();
    }

    public FractalTilesGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int tileWidth, int tileHeight, int tilesX, int tilesY,
                           Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo, boolean julia) {
        this(iteratorFactory, maxiter, tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo, julia, false);
    }

    public FractalTilesGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int tileWidth, int tileHeight, int tilesX, int tilesY,
                           Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo) {
        this(iteratorFactory, maxiter, tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo, false);
    }

    private JPanel getPanel(JComponent view, JComponent controlView) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        panel.add(view, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(controlView, c);
        return panel;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
        pictureView.setPalette(palette);
    }

    public Palette getPalette() {
        return palette;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
        controller.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        controller.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        controller.setIteratorData(iteratorFactory, maxiter);
    }

    public AdvancedFractalIteratorFactorySelector<Coordinate> getIteratorSelector() {
        return iteratorSelector;
    }

    public FractalPictureViewBase<Coordinate> getView() {
        return pictureView;
    }

    public FractalTilesIterateController getController() {
        return controller;
    }

}
