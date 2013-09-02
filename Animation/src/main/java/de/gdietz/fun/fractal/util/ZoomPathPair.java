package de.gdietz.fun.fractal.util;

public class ZoomPathPair<T extends Tuple<T>> {

    private static final double MAX_ZOOM_LOG = 100.0;

    private Path<T> fromPath;
    private Path<T> toPath;

    public ZoomPathPair(Path<T> fromPath, Path<T> toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }

    public void setPaths(Path<T> fromPath, Path<T> toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }

    public Path<T> getFromPath() {
        return fromPath;
    }

    public Path<T> getToPath() {
        return toPath;
    }

    private static double calculateZoomLog(double diffStart, double diffEnd) {
        if (diffStart == 0.0)
            return -MAX_ZOOM_LOG;
        if (diffEnd == 0.0)
            return MAX_ZOOM_LOG;
        double zoomLog = Math.log(diffStart / diffEnd);
        return Math.max(Math.min(zoomLog, MAX_ZOOM_LOG), -MAX_ZOOM_LOG);
    }

    public void setLimits(T fromStart, T toStart, T fromEnd, T toEnd) {
        double oldDiffStart = toPath.getFrom().subtract(fromPath.getFrom()).norm();
        double oldDiffEnd = toPath.getTo().subtract(fromPath.getTo()).norm();

        double newDiffStart = toStart.subtract(fromStart).norm();
        double newDiffEnd = toEnd.subtract(fromEnd).norm();

        fromPath.setLimits(fromStart, fromEnd);
        toPath.setLimits(toStart, toEnd);

        if (fromPath instanceof LogPath && toPath instanceof LogPath) {
            LogPath<T> fromLogPath = (LogPath<T>) fromPath;
            LogPath<T> toLogPath = (LogPath<T>) toPath;

            double oldZoomLog = calculateZoomLog(oldDiffStart, oldDiffEnd);
            double newZoomLog = calculateZoomLog(newDiffStart, newDiffEnd);

            double oldAlphaFrom = fromLogPath.getAlpha();
            double oldAlphaTo = toLogPath.getAlpha();

            double alphaFactorFrom = oldZoomLog == 0.0 ? 1.0 : oldAlphaFrom / oldZoomLog;
            double alphaFactorTo = oldZoomLog == 0.0 ? 1.0 : oldAlphaTo / oldZoomLog;

            fromLogPath.setAlpha(newZoomLog * alphaFactorFrom);
            toLogPath.setAlpha(newZoomLog * alphaFactorTo);
        }
    }

}
