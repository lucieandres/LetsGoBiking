package com.soc.client;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class Itineraire {
    private List<GeoPosition> points;

    public Itineraire(List<GeoPosition> points) {
        this.points = new ArrayList<>(points);
    }

    public List<GeoPosition> getpoints() {
        return points;
    }

    static void displayItineraire(JXMapViewer mapViewer, Itineraire itineraireWalking, Itineraire itineraireBiking, Itineraire itineraireWalkingEnd) {
        Set<Waypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(itineraireWalking.getpoints().get(0)));
        waypoints.add(new DefaultWaypoint(itineraireWalkingEnd.getpoints().get(itineraireWalkingEnd.getpoints().size() - 1)));

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        Painter<JXMapViewer> itinerairePainterWalking = new ItinerairePainter(itineraireWalking.getpoints(), Color.BLUE);
        Painter<JXMapViewer> itinerairePainterBiking = new ItinerairePainter(itineraireBiking.getpoints(), Color.RED);
        Painter<JXMapViewer> itinerairePainterWalkingEnd = new ItinerairePainter(itineraireWalkingEnd.getpoints(), Color.BLUE);

        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(itinerairePainterWalking);
        painters.add(itinerairePainterBiking);
        painters.add(itinerairePainterWalkingEnd);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
    }

    public static void displayItineraire(JXMapViewer mapViewer, Itineraire itineraire) {
        Set<Waypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(itineraire.getpoints().get(0)));
        waypoints.add(new DefaultWaypoint(itineraire.getpoints().get(itineraire.getpoints().size() - 1)));

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        Painter<JXMapViewer> itinerairePainterWalking = new ItinerairePainter(itineraire.getpoints(), Color.BLUE);

        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(itinerairePainterWalking);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
    }


    private static class ItinerairePainter implements Painter<JXMapViewer> {
        private final List<GeoPosition> itineraire;
        private final Color color;

        public ItinerairePainter(List<GeoPosition> itineraire, Color color) {
            this.itineraire = itineraire;
            this.color = color;
        }

        @Override
        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            g = (Graphics2D) g.create();
            Rectangle rect = map.getViewportBounds();
            g.translate(-rect.x, -rect.y);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(color);
            g.setStroke(new BasicStroke(3));
            drawRoute(g, map, itineraire);

            g.setColor(Color.BLUE);
            drawWaypoint(g, map, itineraire.get(0));
            g.setColor(Color.RED);
            drawWaypoint(g, map, itineraire.get(itineraire.size() - 1));

            g.dispose();
        }

        private void drawRoute(Graphics2D g, JXMapViewer map, List<GeoPosition> positions) {

            Point2D p1 = map.getTileFactory().geoToPixel(positions.get(0), map.getZoom());
            Point2D p2 = map.getTileFactory().geoToPixel(positions.get(positions.size() - 1), map.getZoom());

            int lastX = 0;
            int lastY = 0;
            boolean first = true;

            for (GeoPosition gp : positions) {

                Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

                if (first) {
                    first = false;
                } else {
                    if (lastX == p2.getX() && lastY == p2.getY() && pt.getX() == p1.getX() && pt.getY() == p1.getY()) {
                        break;
                    }
                    g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                }
                lastX = (int) pt.getX();
                lastY = (int) pt.getY();
            }
        }

        private void drawWaypoint(Graphics2D g, JXMapViewer map, GeoPosition position) {
            Point2D pt = map.getTileFactory().geoToPixel(position, map.getZoom());
            g.fillOval((int) pt.getX() - 4, (int) pt.getY() - 4, 8, 8);
        }
    }
}
