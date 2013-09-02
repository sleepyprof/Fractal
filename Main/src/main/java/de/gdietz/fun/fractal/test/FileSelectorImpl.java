package de.gdietz.fun.fractal.test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

public class FileSelectorImpl implements FileSelector {

    private static class FilenameExtensionFilter implements FilenameFilter {

        private String dotExtension;

        private FilenameExtensionFilter(String extension) {
            dotExtension = "." + extension;
        }

        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(dotExtension);
        }

        public String getDotExtension() {
            return dotExtension;
        }

    }

    private final JFrame parent;

    private String oldDir;

    public FileSelectorImpl(JFrame parent) {
        this.parent = parent;

        oldDir = null;
    }

    public FileSelectorImpl() {
        this(null);
    }

    public File selectFile(String extension) {
        String title = "Save As";
        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg"))
            title = "Save picture as";
        if (extension.equalsIgnoreCase("mov") || extension.equalsIgnoreCase("avi"))
            title = "Save animation video as";

        FileDialog dialog = new FileDialog(parent, title, FileDialog.SAVE);

        FilenameExtensionFilter filter = new FilenameExtensionFilter(extension);
        dialog.setFilenameFilter(filter);

        String dotExtension = filter.getDotExtension();

        if (oldDir != null)
            dialog.setDirectory(oldDir);

        dialog.setVisible(true);

        String filename = dialog.getFile();
        String dir = dialog.getDirectory();

        if (filename == null)
            return null;

        oldDir = dir;

        File file = new File(dir, filename);

        String path = file.getAbsolutePath();
        if (!path.endsWith(dotExtension))
            file = new File(path + dotExtension);

        if (file.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(parent, filename + " exists.\nOverwrite file?", "File exists", JOptionPane.YES_NO_OPTION);
            if (overwrite == JOptionPane.NO_OPTION)
                file = null;
        }

        return file;
	}

}
