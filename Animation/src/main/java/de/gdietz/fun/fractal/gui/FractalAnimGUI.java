package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Path;
import de.gdietz.fun.fractal.view.*;
import de.gdietz.fun.fractal.model.*;

import javax.swing.*;
import java.awt.*;

public class FractalAnimGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

	private final FractalAnimIterateController controllerLeft;
    private final Fractal2DIterateController controllerRight;

	private Palette palette;

	private final FractalAnimPictureViewBase<Coordinate> animViewLeft;
    private final FractalPictureViewBase<Coordinate> pictureViewRight;

	private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public enum Config {
        MANDEL_ANIM, JULIA_ANIM, MANDEL_ANIM_JULIA, JULIA_ANIM_MANDEL
    }

    public FractalAnimGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath, Config config, boolean resizeable) {
		super(new GridBagLayout());

        FractalAnimIterateModel<Integer> modelLeft;
        FractalIterateModel<Integer> modelRight = null;

        switch(config) {
            case MANDEL_ANIM_JULIA:
                modelRight = new FractalSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter, true);
            case MANDEL_ANIM:
                modelLeft = new FractalAnimSimpleIterateModel(width, height, frames, from, to, paramPath, iteratorFactory, maxiter);
                break;
            case JULIA_ANIM_MANDEL:
                modelRight = new FractalSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter);
            case JULIA_ANIM:
                modelLeft = new FractalAnimSimpleIterateModel(width, height, frames, from, to, paramPath, iteratorFactory, maxiter, true);
                break;
            default:
                throw new AssertionError("Unknown FractalAnimGUI configuration.");
        }

		controllerLeft = new FractalAnimSimpleController(modelLeft);
        controllerRight = modelRight == null ? null : new FractalSimpleController(modelRight);

        @SuppressWarnings("unused")
        WatchingController watcherRight = modelRight == null ? null : new WatchingController(controllerRight);
        
        FractalAnimPairedController<Coordinate> pairedControllerLeft = new FractalAnim2DPairedControllerImpl(controllerLeft, controllerRight);
        Fractal2DPairedController<Coordinate> pairedControllerRight = null;
        if (modelRight != null)
            pairedControllerRight = new Fractal2DAnimPairedControllerImpl(controllerRight, controllerLeft);

		PaletteSelector paletteSelector = new PaletteSelector(this);
		palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalAnimPicture<Coordinate> animLeft = new FractalAnimPictureImpl<>(modelLeft, coloring);
		animViewLeft = resizeable ? new FractalAnimScaledPictureView<>(animLeft, pairedControllerLeft) :
                new FractalAnimPictureView<>(animLeft, pairedControllerLeft);
		JComponent controlViewLeft = new FractalAnimEditView(modelLeft, controllerLeft);
		JPanel panelLeft = getPanel(animViewLeft, controlViewLeft);

        FractalPictureViewBase<Coordinate> pictureViewSecond = null;
		JPanel panelRight = null;
        if (modelRight != null) {
            FractalPicture<Coordinate> pictureRight = new FractalPictureImpl<>(modelRight, coloring);
            pictureViewSecond =  resizeable ? new FractalScaledPictureView<>(pictureRight, pairedControllerRight) :
                    new FractalPictureView<>(pictureRight, pairedControllerRight);
            JComponent controlViewRight = new FractalEditView(modelRight, controllerRight);
            panelRight = getPanel(pictureViewSecond, controlViewRight);
        }
        pictureViewRight = pictureViewSecond;

		iteratorSelector = new AdvancedFractalIteratorFactorySelector<>(this, true);
		iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, "default");

        GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		if (panelRight == null)
			c.gridwidth = GridBagConstraints.REMAINDER;
		add(panelLeft, c);
		if (panelRight != null) {
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(panelRight, c);
		}
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		add(paletteSelector, c);
		add(iteratorSelector, c);

        if (resizeable) {
            FractalSizeSelector sizeSelector = new FractalSizeSelector(modelLeft, controllerLeft, controllerRight);
            add(sizeSelector, c);
        }

        FractalAnimFramesSelector framesSelector = new FractalAnimFramesSelector(modelLeft, controllerLeft);
        add(framesSelector, c);

        AnimationSelector animSelector = new AnimationSelector(animViewLeft);
        add(animSelector, c);
        
        controllerLeft.startCalculation();
        if (controllerRight != null)
            controllerRight.startCalculation();
        animViewLeft.startAnimation();
	}

    public FractalAnimGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath, Config config) {
        this(iteratorFactory, maxiter, width, height, frames, from, to, paramPath, config, false);
    }

    public FractalAnimGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height, int frames,
	                  Coordinate from, Coordinate to, Path<Coordinate> paramPath) {
        this(iteratorFactory, maxiter, width, height, frames, from, to, paramPath, Config.MANDEL_ANIM);
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
        animViewLeft.setPalette(palette);
        if (pictureViewRight != null)
            pictureViewRight.setPalette(palette);
    }

    public Palette getPalette() {
        return palette;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
        controllerLeft.setIteratorFactory(iteratorFactory);
        if (controllerRight != null)
            controllerRight.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        controllerLeft.setMaxiter(maxiter);
        if (controllerRight != null)
            controllerRight.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        controllerLeft.setIteratorData(iteratorFactory, maxiter);
        if (controllerRight != null)
            controllerRight.setIteratorData(iteratorFactory, maxiter);
    }

	public AdvancedFractalIteratorFactorySelector<Coordinate> getIteratorSelector() {
		return iteratorSelector;
	}

	public FractalAnimPictureViewBase<Coordinate> getView() {
        return animViewLeft;
	}

    public FractalPictureViewBase<Coordinate> getViewRight() {
        return pictureViewRight;
    }

	public FractalAnimIterateController getController() {
        return controllerLeft;
	}

    public Fractal2DIterateController getControllerRight() {
        return controllerRight;
    }

}
