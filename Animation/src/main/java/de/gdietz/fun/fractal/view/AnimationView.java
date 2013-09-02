package de.gdietz.fun.fractal.view;

public interface AnimationView {

    public void addAnimationListener(AnimationListener listener);

    public void startAnimation();
    public void stopAnimation();

    public void setCurrentFrame(int frame);

}
