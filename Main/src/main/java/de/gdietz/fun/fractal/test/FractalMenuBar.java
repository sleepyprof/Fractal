package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FractalMenuBar extends JMenuBar implements ActionListener {

    private final FractalWindow window;

    private final FileSelector fileSelector;

    public FractalMenuBar(FractalWindow window) {
        this.window = window;

        boolean hasSaveableView = window.getSaveableView() != null;
        boolean hasSaveableViewOther = window.getSaveableViewOther() != null;

        fileSelector = new FileSelectorImpl(window);

        JMenu menuFile = new JMenu("File");
        JMenuItem menuSave = new JMenuItem(hasSaveableViewOther ? "Save left side..." : "Save...");
        menuSave.setActionCommand("save");
        menuSave.addActionListener(this);
        JMenuItem menuSave2 = new JMenuItem("Save right side...");
        menuSave2.setActionCommand("save2");
        menuSave2.addActionListener(this);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setActionCommand("exit");
        menuExit.addActionListener(this);
        if (hasSaveableView) {
            menuFile.add(menuSave);
            if (hasSaveableViewOther)
                menuFile.add(menuSave2);
            menuFile.addSeparator();
        }
        menuFile.add(menuExit);
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuHlp = new JMenuItem("Help...");
        menuHlp.setActionCommand("help");
        menuHlp.addActionListener(this);
        JMenuItem menuAbout = new JMenuItem("About...");
        menuAbout.setActionCommand("about");
        menuAbout.addActionListener(this);
        menuHelp.add(menuHlp);
        menuHelp.addSeparator();
        menuHelp.add(menuAbout);
        add(menuFile);
        add(menuHelp);
    }

    private void saveFile(SaveableView view) {
        window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        File file = fileSelector.selectFile(view.getExtension());
        try {
            if (file != null)
                view.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setCursor(Cursor.getDefaultCursor());
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("save".equals(cmd)) {
            SaveableView view = window.getSaveableView();
            saveFile(view);
        }
        if ("save2".equals(cmd)) {
            SaveableView viewOther = window.getSaveableViewOther();
            saveFile(viewOther);
        }
        if ("exit".equals(cmd))
            window.exit();
        if ("help".equals(cmd))
            window.showHelp();
        if ("about".equals(cmd))
            window.showCopyright();
	}

}
