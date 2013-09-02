package de.gdietz.fun.fractal.view;

import java.io.File;
import java.io.IOException;

public interface SaveableView {

    public void writeTo(File file) throws IOException;

    public String getExtension();

}
