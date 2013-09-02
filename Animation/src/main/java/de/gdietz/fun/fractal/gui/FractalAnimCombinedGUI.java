package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalAnimIterateModel;
import de.gdietz.fun.fractal.model.FractalAnimSimpleIterateModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class FractalAnimCombinedGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

	private final FractalAnimIterateController controller;

	private Palette palette;

	private final FractalAnimPictureViewBase<Coordinate> animView;
    private final FractalPictureViewBase<Coordinate> pictureView;

	private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public FractalAnimCombinedGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath, boolean julia, boolean resizeable) {
		super(new GridBagLayout());

        FractalAnimIterateModel<Integer> model = new FractalAnimSimpleIterateModel(width, height, frames, from, to, paramPath, iteratorFactory, maxiter, julia);

		controller = new FractalAnimSimpleController(model);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

        FractalAnimPairedController<Coordinate> pairedControllerLeft = new FractalAnim2DPairedControllerImpl(controller);
        Fractal2DPairedController<Coordinate> pairedControllerRight = new Fractal2DAnimCombinedControllerImpl(controller);

		PaletteSelector paletteSelector = new PaletteSelector(this);
		palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalAnimPicture<Coordinate> anim = new FractalAnimPictureImpl<Integer>(model, coloring);
		animView = resizeable ? new FractalAnimScaledPictureView<Coordinate>(anim, pairedControllerLeft) :
                new FractalAnimPictureView<Coordinate>(anim, pairedControllerLeft);

        FractalPicture<Coordinate> picture = new FractalAnimCombinedPicture<Integer>(model, new IntegerCombiner(), coloring);
        pictureView = resizeable ? new FractalScaledPictureView<Coordinate>(picture, pairedControllerRight) :
                new FractalPictureView<Coordinate>(picture, pairedControllerRight);

        JComponent controlView = new FractalAnimEditView(model, controller);
		JPanel panel = getPanel(animView, pictureView, controlView);

		iteratorSelector = new AdvancedFractalIteratorFactorySelector<Coordinate>(this, true);
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
        animView.startAnimation();
	}

    public FractalAnimCombinedGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath, boolean julia) {
        this(iteratorFactory, maxiter, width, height, frames, from, to, paramPath, julia, false);
    }

    public FractalAnimCombinedGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath) {
        this(iteratorFactory, maxiter, width, height, frames, from, to, paramPath, false);
    }

	private JPanel getPanel(JComponent viewLeft, JComponent viewRight, JComponent controlView) {
        JPanel viewPanel = new JPanel(new GridLayout(1, 2));
        viewPanel.add(viewLeft);
        viewPanel.add(viewRight);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        panel.add(viewPanel, c);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        panel.add(controlView, c);
        return panel;
	}

    public void setPalette(Palette palette) {
        this.palette = palette;
        animView.setPalette(palette);
        if (pictureView != null)
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

	public FractalAnimPictureViewBase<Coordinate> getView() {
        return animView;
	}

    public FractalPictureViewBase<Coordinate> getViewRight() {
        return pictureView;
    }

	public FractalAnimIterateController getController() {
        return controller;
	}

}
