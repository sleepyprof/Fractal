package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalZoomIterateModel;
import de.gdietz.fun.fractal.model.FractalZoomSimpleIterateModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class FractalZoomGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

    private final FractalZoomIterateController controller;

    private Palette palette;

    private final FractalAnimPictureViewBase<Coordinate> animView;

    private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public FractalZoomGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                      Path<Coordinate> fromPath, Path<Coordinate> toPath, boolean julia, boolean resizeable) {
        super(new GridBagLayout());

        FractalZoomIterateModel<Integer> model = new FractalZoomSimpleIterateModel(width, height, frames, fromPath, toPath, iteratorFactory, maxiter, julia);

        controller = new FractalZoomSimpleController(model);
        FractalAnimPairedController<Coordinate> pairedController = new FractalAnimNullPairedControllerImpl(controller);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

        PaletteSelector paletteSelector = new PaletteSelector(this);
        palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalAnimPicture<Coordinate> anim = new FractalZoomPictureImpl<>(model, coloring);
        animView = resizeable ? new FractalAnimScaledPictureView<>(anim, pairedController) :
                new FractalAnimPictureView<>(anim, pairedController);
        JComponent controlView = new FractalZoomEditView(model, controller);
        JPanel panel = getPanel(animView, controlView);

        iteratorSelector = new AdvancedFractalIteratorFactorySelector<>(this, true);
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

        FractalAnimFramesSelector framesSelector = new FractalAnimFramesSelector(model, controller);
        add(framesSelector, c);

        AnimationSelector animSelector = new AnimationSelector(animView);
        add(animSelector, c);

        controller.startCalculation();
    }

    public FractalZoomGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                      Path<Coordinate> fromPath, Path<Coordinate> toPath, boolean julia) {
        this(iteratorFactory, maxiter, width, height, frames, fromPath, toPath, julia, false);
    }

    public FractalZoomGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                      Path<Coordinate> fromPath, Path<Coordinate> toPath) {
        this(iteratorFactory, maxiter, width, height, frames, fromPath, toPath, false);
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
        animView.setPalette(palette);
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

    public FractalAnimPictureViewBase<Coordinate> getView() {
        return animView;
    }

    public FractalZoomIterateController getController() {
        return controller;
    }

}
