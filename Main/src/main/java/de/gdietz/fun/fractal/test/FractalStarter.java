package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.gui.FractalAnimGUI;
import de.gdietz.fun.fractal.gui.FractalGUI;
import de.gdietz.fun.fractal.gui.FractalViewsGUI;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.media.j3d.VirtualUniverse;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class FractalStarter implements ListSelectionListener {

    public enum Type {
        MANDEL("Mandelbrot Set", true, true),
        JULIA("Julia Set", true, true),
        MANDEL3D("Mandelbrot Set 3D", false, false),
        JULIA3D("Julia Set 3D", false, false),
        MANDEL4D("Mandelbrot Set 4D Cube", true, true),
        JULIA4D("Julia Set 4D Cube", true, true),
        MANDEL_JULIA("Mandelbrot Set and Julia Set", true, true),
        MANDEL_TRACE("Mandelbrot Set with Trace", true, true),
        MANDEL_BIFURCATION("Mandelbrot Set with Bifurcation", true, true),
        MANDEL_ANIM("Mandelbrot Set Animation", true, false),
        JULIA_ANIM("Julia Set Animation", true, false),
        MANDEL_ANIM_JULIA("Mandelbrot Set Animation with Julia Set", true, false),
        JULIA_ANIM_MANDEL("Julia Set Animation with Mandelbrot Set", true, false),
        MANDEL_ANIM_COMBINED("Mandelbrot Set Animation Combined", true, false),
        JULIA_ANIM_COMBINED("Julia Set Animation Combined", true, false),
        MANDEL_TILES("Mandelbrot Set Tiles", true, false),
        JULIA_TILES("Julia Set Tiles", true, false),
        NEWTON("Newton", true, false),
        NEWTON_TWISTED("Newton twisted", true, false),
        MANDEL_PERIOD("Mandelbrot Set Period Analysis", true, false),
        JULIA_PERIOD("Julia Set Period Analysis", true, false),
        MANDEL_PRECISE("Mandelbrot Set High Precision", true, true, false),
        JULIA_PRECISE("Julia Set High Precision", true, true, false),
        MANDEL_ZOOM("Mandelbrot Zoom Animation", true, false),
        JULIA_ZOOM("Julia Set Zoom Animation", true, false),
        MANDEL_SURFACE("Mandelbrot Set Surface", false, false),
        JULIA_SURFACE("Julia Set Surface", false, false),
        SCALA("Scala Fractal", true, true);

        public final String description;
        public final boolean hasResizeable;
        public final boolean hasEndless;
        public final boolean defaultEndless;

        Type(String description, boolean hasResizeable, boolean hasEndless, boolean defaultEndless) {
            this.description = description;
            this.hasResizeable = hasResizeable;
            this.hasEndless = hasEndless;
            this.defaultEndless = defaultEndless;
        }

        Type(String description, boolean hasResizeable, boolean hasEndless) {
            this(description, hasResizeable, hasEndless, true);
        }

        public String toString() {
            return description;
        }

    }

    private static final int[] HEAPSIZESMEGS = {256, 512, 768, 1024, 1536, 2048, 3072, 4096};
    private static final int MINHEAPINDEX = 2;

    private final JList configList;

    private final JCheckBox endlessCB;
    private final JCheckBox resizeableCB;

    private final JComboBox heapCombo;

    private final ImageIcon icon;
    
    private static Logger log = Logger.getLogger(FractalStarter.class);

    private static class CloseDialogOnDoubleclickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JDialog dialog = null;
                Component component = e.getComponent();
                while (component != null) {
                    component = component.getParent();
                    if (component instanceof JDialog)
                        dialog = (JDialog) component;
                }
                if (dialog != null)
                    dialog.setVisible(false);
            }
        }

    }

    public FractalStarter() {
        configList = new JList(Type.values());
        configList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        configList.setSelectedIndex(0);

        Type type = (Type) configList.getSelectedValue();

        endlessCB = new JCheckBox((type.defaultEndless ? "" : "No " ) + "Endless Iteration", true);
        resizeableCB = new JCheckBox("Resizeable", true);

        configList.addListSelectionListener(this);
        configList.addMouseListener(new CloseDialogOnDoubleclickListener());

        String[] heapSizeStrs = new String[HEAPSIZESMEGS.length + 1];
        long heapSizeMegs = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        heapSizeStrs[0] = "current heap size(" + heapSizeMegs + "MB)";
        for (int c = 0; c < HEAPSIZESMEGS.length; c++)
            heapSizeStrs[c + 1] = "heap size " + String.valueOf(HEAPSIZESMEGS[c]) + "MB";

        heapCombo = new JComboBox(heapSizeStrs);
        if (heapSizeMegs < HEAPSIZESMEGS[MINHEAPINDEX] - 20)
            heapCombo.setSelectedIndex(MINHEAPINDEX + 1);

        URL iconURL = ClassLoader.getSystemResource("fractal.png");
        icon = iconURL == null ? null : new ImageIcon(ClassLoader.getSystemResource("fractal.png"));
    }

    protected void go() {
        final JScrollPane scrollPane = new JScrollPane(configList);

        final JComponent[] inputs = {scrollPane, endlessCB, resizeableCB, heapCombo};
        int result = JOptionPane.showConfirmDialog(null, inputs, "Fractal Starter", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, icon);

        if (result == JOptionPane.CANCEL_OPTION)
            return;

        Type type = (Type) configList.getSelectedValue();
        boolean endless = !type.defaultEndless ^ endlessCB.isSelected();
        boolean resizeable = resizeableCB.isSelected();

        int heapIndex = heapCombo.getSelectedIndex();

        if (heapIndex == 0)
            go(type, endless, resizeable);
        else
            goWithNewVM(type, endless, resizeable, HEAPSIZESMEGS[heapIndex - 1]);
    }

    protected static void go(Type type, boolean endless, boolean resizeable) {
        if (type == Type.MANDEL3D || type == Type.JULIA3D) {
            if (checkJava3D() == null) {
                JOptionPane.showMessageDialog(null, "Java 3D not properly installed!", "Java 3D Error", JOptionPane.ERROR_MESSAGE);
                log.warn("Could not start Java3D environment. Exiting.");
                return;
            }
        }

        switch (type) {
            case MANDEL:
                new FractalTest(FractalGUI.Config.MANDEL, endless, resizeable);
                break;
            case JULIA:
                new FractalTest(FractalGUI.Config.JULIA, endless, resizeable);
                break;
            case MANDEL3D:
                new Fractal3DTest();
                break;
            case JULIA3D:
                new Fractal3DTest(true);
                break;
            case MANDEL4D:
                new FractalTest(FractalGUI.Config.MANDEL4D, endless, resizeable);
                break;
            case JULIA4D:
                new FractalTest(FractalGUI.Config.JULIA4D, endless, resizeable);
                break;
            case MANDEL_JULIA:
                new FractalTest(FractalGUI.Config.MANDEL_JULIA, endless, resizeable);
                break;
            case MANDEL_TRACE:
                new FractalTest(FractalGUI.Config.MANDEL_TRACE, endless, resizeable);
                break;
            case MANDEL_BIFURCATION:
                new FractalTest(FractalGUI.Config.MANDEL_BIFURCATION, endless, resizeable);
                break;
            case MANDEL_ANIM:
                new FractalAnimTest(FractalAnimGUI.Config.MANDEL_ANIM, resizeable);
                break;
            case JULIA_ANIM:
                new FractalAnimTest(FractalAnimGUI.Config.JULIA_ANIM, resizeable);
                break;
            case MANDEL_ANIM_JULIA:
                new FractalAnimTest(FractalAnimGUI.Config.MANDEL_ANIM_JULIA, resizeable);
                break;
            case JULIA_ANIM_MANDEL:
                new FractalAnimTest(FractalAnimGUI.Config.JULIA_ANIM_MANDEL, resizeable);
                break;
            case MANDEL_ANIM_COMBINED:
                new FractalAnimCombinedTest(false, resizeable);
                break;
            case JULIA_ANIM_COMBINED:
                new FractalAnimCombinedTest(true, resizeable);
                break;
            case MANDEL_TILES:
                new FractalTilesTest(false, resizeable);
                break;
            case JULIA_TILES:
                new FractalTilesTest(true, resizeable);
                break;
            case NEWTON:
                new NewtonTest(true, resizeable);
                break;
            case NEWTON_TWISTED:
                new NewtonTest(false, resizeable);
                break;
            case MANDEL_PERIOD:
                new PeriodTest(false, resizeable);
                break;
            case JULIA_PERIOD:
                new PeriodTest(true, resizeable);
                break;
            case MANDEL_PRECISE:
                new FractalPreciseTest(false, endless, resizeable);
                break;
            case JULIA_PRECISE:
                new FractalPreciseTest(true, endless, resizeable);
                break;
            case MANDEL_ZOOM:
                new FractalZoomTest(false, resizeable);
                break;
            case JULIA_ZOOM:
                new FractalZoomTest(true, resizeable);
                break;
            case MANDEL_SURFACE:
                new FractalViewsTest(FractalViewsGUI.ViewType.SURFACE, false, true);
                break;
            case JULIA_SURFACE:
                new FractalViewsTest(FractalViewsGUI.ViewType.SURFACE, true, true);
                break;
            case SCALA:
                new FractalScalaTest(endless, resizeable);
                break;
            default:
                throw new AssertionError("Unknown Selection");
        }
    }

    protected static void go(Type type) {
        go(type, true, false);
    }

    public void valueChanged(ListSelectionEvent e) {
        Type type = (Type) configList.getSelectedValue();

        endlessCB.setText((type.defaultEndless ? "" : "No " ) + "Endless Iteration");

        endlessCB.setEnabled(type.hasEndless);
        resizeableCB.setEnabled(type.hasResizeable);
    }

    public static void goWithNewVM(Type type, boolean endless, boolean resizeable, int heapSizeMegs) {
        String pathToJar = null;
        try {
            pathToJar = FractalStarter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            log.error("Could not find FractalStarter class", e);
        }

        String classPath;
        if (pathToJar != null && pathToJar.endsWith(".jar"))
            classPath = pathToJar;
        else
            classPath = System.getProperty("java.class.path");

        if (classPath != null) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-Xms" + heapSizeMegs + "m", "-Xmx" + heapSizeMegs + "m", "-classpath", classPath,
                        FractalStarter.class.getName(), type.name(), String.valueOf(endless), String.valueOf(resizeable));
                String info = "Starting:";
                for (String cmd : pb.command())
                    info += " " + cmd;
                log.info(info);
                pb.start();
            } catch (IOException e) {
                log.error("Could not start new FractalStarter process", e);
            }
        } else {
            log.error("Could not locate class. Fallback to normal start.");
            go(type, endless, resizeable);
        }
    }

    private static void syntax() {
        System.err.println("Syntax: " + FractalStarter.class.getSimpleName() + " " +
                "type [endless resizeable]");
        System.err.print("   type: One of ");
        for(Type type : Type.values())
            System.err.print(type.name() + " ");
        System.err.println();
        System.err.println("   endless: true/false");
        System.err.println("   resizeable: true/false");
    }

    public static String checkJava3D() {
        try {
            Map properties = VirtualUniverse.getProperties();
            return  properties.get("j3d.version").toString();
        } catch (Throwable e) {
            log.warn("Could not get Java3D version", e);
            return null;
        }
    }


    static {
        BasicConfigurator.configure();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.warn("Could not set look&feel", e);
        }
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static void main(String[] args) {
        Type type = null;
        boolean endless = true;
        boolean resizeable = false;

        if (args.length > 3 || args.length == 2) {
            syntax();
            return;
        }

        if (args.length >= 1) {
            try {
                type = Type.valueOf(args[0]);
            } catch (IllegalArgumentException e) {
                syntax();
                return;
            }
        }

        if (args.length == 3) {
            if (!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false") ||
                    !args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) {
                syntax();
                return;
            }
            endless = args[1].equalsIgnoreCase("true");
            resizeable = args[2].equalsIgnoreCase("true");
        }

        if (type != null)
            go(type, endless, resizeable);
        else
            new FractalStarter().go();
    }

}
