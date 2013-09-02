package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.view.AnimationListener;
import de.gdietz.fun.fractal.view.AnimationView;
import de.gdietz.fun.fractal.view.FractalView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class AnimationSelector extends JPanel implements FractalView, AnimationListener {

    private final AnimationView view;

    private final JSlider slider;

    private final JButton startButton;
    private final JButton stopButton;

    private class ControlPanelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource()==startButton)
                view.startAnimation();
            if (event.getSource()==stopButton)
                view.stopAnimation();
        }
    }

    private class SliderChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (slider.getValueIsAdjusting()) {
                int value = slider.getValue();
                view.setCurrentFrame(value);
            }
        }
    }

    public AnimationSelector(AnimationView view) {
        super(new GridBagLayout());

        JLabel label = new JLabel("Animation:");

        slider = new JSlider();
        slider.addChangeListener(new SliderChangeListener());

        startButton = new JButton("Start");
        startButton.addActionListener(new ControlPanelActionListener());
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ControlPanelActionListener());

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        add(label);
        add(slider);
        add(startButton);
        add(stopButton);

        this.view = view;
        view.addAnimationListener(this);
        update();
    }

    public void update(int currentFrame, int frames) {
        slider.setMaximum(frames);
        slider.setValue(currentFrame);
    }

    public void update() {
    }

    public void update(Observable o, Object arg) {
        update();
    }

}
