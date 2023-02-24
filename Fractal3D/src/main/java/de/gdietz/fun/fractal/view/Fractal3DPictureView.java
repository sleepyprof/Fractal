package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal3DController;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.model.Fractal3DModel;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.imageio.PNGWriter;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.behaviors.mouse.MouseRotate;
import org.jogamp.java3d.utils.behaviors.mouse.MouseTranslate;
import org.jogamp.java3d.utils.behaviors.mouse.MouseZoom;
import org.jogamp.java3d.utils.geometry.GeometryInfo;
import org.jogamp.java3d.utils.geometry.NormalGenerator;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Fractal3DPictureView extends JPanel implements FractalView, SaveableView {

	private final Fractal3DModel model;
	private final Fractal3DController controller;

    private final Canvas3D canvas;
    private final SimpleUniverse universe;

    private BranchGroup scene;

    private Shape3D body;
    private List<Point3f> points;

    private final static Logger log = LoggerFactory.getLogger(Fractal3DPictureView.class);

    public Fractal3DPictureView(Fractal3DModel model, Fractal3DController controller) {
        this.model = model;
		this.controller = controller;
		model.addObserver(this);

        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        add(canvas);

        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        canvas.addMouseListener(new MouseInteractionListener());

        scene = null;

		update();
	}

    public void update() {
        log.debug("update on Fractal3DPictureView called");

        if (scene != null)
            universe.getLocale().removeBranchGraph(scene);

        scene = null;
        body = null;
        points = null;
        System.gc();

        scene = createSceneGraph();
        scene.setCapability(BranchGroup.ALLOW_DETACH);
        scene.compile();

        universe.addBranchGraph(scene);

        canvas.setSize(getSize());
    }

	public void update(Observable o, Object arg) {
		update();
	}

    private Appearance getBodyAppearance() {
        Appearance app = new Appearance();
        Material material = new Material();
        PolygonAttributes polygonAttr = new PolygonAttributes();

        polygonAttr.setPolygonMode(PolygonAttributes.POLYGON_FILL);
        polygonAttr.setCullFace(PolygonAttributes.CULL_NONE);
        polygonAttr.setBackFaceNormalFlip(true);

        material.setDiffuseColor(0.7f, 0.7f, 0.7f);
        material.setAmbientColor(0.7f, 0.7f, 0.7f);
        material.setLightingEnable(true);

        app.setMaterial(material);
        app.setPolygonAttributes(polygonAttr);

        return app;
    }

    private Shape3D getBody(Point3f[] pointArr) {
        GeometryInfo ginfo = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
        ginfo.setCoordinates(pointArr);
        NormalGenerator n = new NormalGenerator();
        n.generateNormals(ginfo);

        QuadArray quadArray = (QuadArray) ginfo.getGeometryArray();

        return new Shape3D(quadArray);
    }

    private TransformGroup getMouseTransform() {
        MouseRotate mouseRotate = new MouseRotate();
        MouseZoom mouseZoom = new MouseZoom();
        MouseTranslate mouseTranslate = new MouseTranslate();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        mouseRotate.setSchedulingBounds(bounds);
        mouseZoom.setSchedulingBounds(bounds);
        mouseTranslate.setSchedulingBounds(bounds);

        TransformGroup mouseTransform = new TransformGroup();
        mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        mouseRotate.setTransformGroup(mouseTransform);
        mouseTransform.addChild(mouseRotate);
        mouseZoom.setTransformGroup(mouseTransform);
        mouseTransform.addChild(mouseZoom);
        mouseTranslate.setTransformGroup(mouseTransform);
        mouseTransform.addChild(mouseTranslate);

        return mouseTransform;
    }

    private List<Light> getLights() {
        List<Light> lights = new ArrayList<>();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

//        Color3f light1Color = new Color3f(1f, 1f, 1f);
        Color3f light1Color = new Color3f(1f, .8f, .8f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);

        Color3f light2Color = new Color3f(.8f, 1f, .8f);
        Vector3f light2Direction = new Vector3f(4.0f, 7.0f, -12.0f);
        DirectionalLight light2 = new DirectionalLight(light2Color, light2Direction);
        light2.setInfluencingBounds(bounds);

        AmbientLight ambientLight = new AmbientLight(new Color3f(.5f, .5f, .5f));
        ambientLight.setInfluencingBounds(bounds);

        lights.add(light1);
        lights.add(light2);
        lights.add(ambientLight);

        return lights;
    }


    public BranchGroup createSceneGraph(Point3f[] pointArr) {
        log.info("Creating scene...");

        BranchGroup scene = new BranchGroup();

        body = getBody(pointArr);
        body.setAppearance(getBodyAppearance());
        body.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);

        TransformGroup mouseTransform = getMouseTransform();
        mouseTransform.addChild(body);
        scene.addChild(mouseTransform);

        List<Light> lights = getLights();
        for(Light light : lights)
            scene.addChild(light);

        log.info("scene finished.");

        return scene;
    }

    private boolean isInside(int x, int y, int z) {
        int size = model.getSize();
        return x >= 0 && y >= 0 && z >= 0 && x < size && y < size && z < size && model.getData(x, y, z);
    }

    private Point3f getViewPointHalf(int x2, int y2, int z2) {
        return new Point3f((float)x2 / (model.getSize()-1) - 1.0f, (float)y2 / (model.getSize()-1) - 1.0f, (float)z2 / (model.getSize()-1) - 1.0f);
    }

    public BranchGroup createSceneGraph() {
        log.info("Calculating surfaces...");

        points = new ArrayList<>();

        int size = model.getSize();
        for(int x=0; x <= size; x++) {
            for(int y=0; y <= size; y++) {
                for(int z=0; z <= size; z++) {
                    if (isInside(x, y, z) != isInside(x - 1, y, z)) {
                        points.add(getViewPointHalf(x + x - 1, y + y - 1, z + z - 1));
                        points.add(getViewPointHalf(x + x - 1, y + y + 1, z + z - 1));
                        points.add(getViewPointHalf(x + x - 1, y + y + 1, z + z + 1));
                        points.add(getViewPointHalf(x + x - 1, y + y - 1, z + z + 1));
                    }
                    if (isInside(x, y, z) != isInside(x, y - 1, z)) {
                        points.add(getViewPointHalf(x + x - 1, y + y - 1, z + z - 1));
                        points.add(getViewPointHalf(x + x + 1, y + y - 1, z + z - 1));
                        points.add(getViewPointHalf(x + x + 1, y + y - 1, z + z + 1));
                        points.add(getViewPointHalf(x + x - 1, y + y - 1, z + z + 1));
                    }
                    if (isInside(x, y, z) != isInside(x, y, z - 1)) {
                        points.add(getViewPointHalf(x + x - 1, y + y - 1, z + z - 1));
                        points.add(getViewPointHalf(x + x + 1, y + y - 1, z + z - 1));
                        points.add(getViewPointHalf(x + x + 1, y + y + 1, z + z - 1));
                        points.add(getViewPointHalf(x + x - 1, y + y + 1, z + z - 1));
                    }
                }
            }
        }

        if (points.size() == 0) {
            points.add(getViewPointHalf(size - 1, size - 1, 0));
            points.add(getViewPointHalf(size + 1, size - 1, 0));
            points.add(getViewPointHalf(size + 1, size + 1, 0));
            points.add(getViewPointHalf(size - 1, size + 1, 0));
        }
        
        Point3f[] pointArr = points.toArray(new Point3f[points.size()]);

        log.info("surfaces finished.");

        return createSceneGraph(pointArr);
    }

    public Point3d getPosition(int screenX, int screenY) {
        Point3d eyePos = new Point3d();
        Point3d mousePos = new Point3d();
        canvas.getCenterEyeInImagePlate(eyePos);
        canvas.getPixelLocationInImagePlate(screenX, screenY, mousePos);
        Transform3D transform = new Transform3D();
        canvas.getImagePlateToVworld(transform);
        transform.transform(eyePos);
        transform.transform(mousePos);

        Vector3d direction = new Vector3d(eyePos);
        direction.sub(mousePos);

        Transform3D currentTransform = new Transform3D();
        body.getLocalToVworld(currentTransform);
        Transform3D inverseTransform = new Transform3D(currentTransform);
        inverseTransform.invert();

        List<Point3d> intersections = new ArrayList<>();
        List<Double> dists = new ArrayList<>();
        for(int count=0; count<points.size(); count+=4) {
            Point3d p1 = new Point3d(points.get(count));
            Point3d p2 = new Point3d(points.get(count+1));
            Point3d p3 = new Point3d(points.get(count+2));

            currentTransform.transform(p1);
            currentTransform.transform(p2);
            currentTransform.transform(p3);

            Point3d intersection = new Point3d();
            double[] dist = new double[1];
            boolean intersect = getIntersection(eyePos, mousePos, p1, p2, p3, intersection, dist);
            if (intersect) {
                inverseTransform.transform(intersection);
                intersections.add(intersection);
                dists.add(dist[0]);
            }
        }

        if (intersections.size() == 0)
            return null;

        Double minDist = null;
        Point3d result = null;
        for(int count=0; count<intersections.size(); count++)
            if (minDist==null || minDist>dists.get(count)) {
                minDist = dists.get(count);
                result = intersections.get(count);
            }

        return result;
    }

    private boolean getIntersection(Point3d line1, Point3d line2,
                            Point3d plane1, Point3d plane2, Point3d plane3, Point3d result, double[] dist) {
        Vector3d p1 = new Vector3d(plane1);
        Vector3d p2 = new Vector3d(plane2);
        Vector3d p3 = new Vector3d(plane3);

        Vector3d q2 = new Vector3d(p2);
        q2.sub(p1);
        Vector3d q3 = new Vector3d(p3);
        q3.sub(p1);

        Vector3d l1 = new Vector3d(line1);
        Vector3d l2 = new Vector3d(line2);

        Vector3d negdir = new Vector3d(l1);
        negdir.sub(l2);
        Vector3d tr = new Vector3d(l1);
        tr.sub(p1);

        double d = det(q2, q3, negdir);
        if (d == 0)
            return false;

        double a = det(tr, q3, negdir) / d;
        if (a < 0.0 || a > 1.0)
            return false;
        double b = det(q2, tr, negdir) / d;
        if (b < 0.0 || b > 1.0)
            return false;

        double t = det(q2, q3, tr) / d;
        if (t < 0.0)
            return false;

        Vector3d scaledDirection = new Vector3d(negdir);
        scaledDirection.scale(-t);

        Vector3d intersection = new Vector3d(line1);
        intersection.add(scaledDirection);

        result.set(intersection);
        dist[0] = t;
        return true;
    }

    private static double det(Vector3d u, Vector3d v, Vector3d w) {
        Matrix3d m = new Matrix3d(u.x, u.y, u.z, v.x, v.y, v.z, w.x, w.y, w.z);
        return m.determinant();
    }


    private class MouseInteractionListener extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            if (event.getButton() != 3)
                 return;

            Point3d pos = getPosition(event.getX(), event.getY());
            if (pos == null) {
                log.info("Clicked on empty space...");
                return;
            }

            double ax = 0.5 * (pos.x + 1.0);
            double ay = 0.5 * (pos.y + 1.0);
            double az = 0.5 * (pos.z + 1.0);

            Coordinate3D from = model.getCornerFrom();
            Coordinate3D to = model.getCornerTo();

            double dx = to.getX() - from.getX();
            double dy = to.getY() - from.getY();
            double dz = to.getZ() - from.getZ();

            Coordinate3D newFrom = new Coordinate3D(
                    from.getX() + (ax - 0.25) * dx,
                    from.getY() + (ay - 0.25) * dy,
                    from.getZ() + (az - 0.25) * dz
            );
            Coordinate3D newTo = new Coordinate3D(
                    from.getX() + (ax + 0.25) * dx,
                    from.getY() + (ay + 0.25) * dy,
                    from.getZ() + (az + 0.25) * dz
            );

            controller.setCorners(newFrom, newTo);
        }

    }

    public static BufferedImage takeScreenshot(Canvas3D canvas) {
        Screen3D on = canvas.getScreen3D();

        Canvas3D shot = new Canvas3D(canvas.getGraphicsConfiguration(), true);
        canvas.getView().stopView();
        canvas.getView().addCanvas3D(shot);
        canvas.getView().startView();

        Screen3D off = shot.getScreen3D();
        off.setSize(on.getSize());
        off.setPhysicalScreenHeight(on.getPhysicalScreenHeight());
        off.setPhysicalScreenWidth(on.getPhysicalScreenWidth());
        shot.setOffScreenLocation(canvas.getLocationOnScreen());

        BufferedImage bi = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bi);
        shot.setOffScreenBuffer(buffer);
        shot.renderOffScreenBuffer();
        shot.waitForOffScreenRendering();
        BufferedImage res = shot.getOffScreenBuffer().getImage();
        canvas.getView().removeCanvas3D(shot);

        return res;
    }

    public void writeTo(File file) throws IOException {
        BufferedImage image = takeScreenshot(canvas);
        PNGWriter writer = new PNGWriter(image, file);
        FractalMetadata metadata = model.getMetadata();
        writer.addMetadata(metadata);
        writer.write();
    }

    public String getExtension() {
        return "png";
    }

}