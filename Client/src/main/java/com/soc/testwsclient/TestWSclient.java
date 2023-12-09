package com.soc.testwsclient;

import com.soap.ws.client.generated.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.viewer.GeoPosition;

import javax.jms.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TestWSclient {

    public static void main(String[] args) {

        try {
            LetsGoBiking service = new LetsGoBiking();
            ILetsGoBiking is = service.getBasicHttpBindingILetsGoBiking();
            Scanner sc = new Scanner(System.in);

            System.out.println("Origine :\n 1. Polytech Nice Sophia\n 2. Lyon Brasserie Georges\n 3. Bruxelles Atomium\n 4. Autres");
            String originChoice = sc.nextLine();
            String origin = "";
            switch (originChoice) {
                case "1":
                    origin = "Polytech Nice Sophia";
                    break;
                case "2":
                    origin = "Lyon Brasserie Georges";
                    break;
                case "3":
                    origin = "Bruxelles Atomium";
                    break;
                case "4":
                    System.out.println("Entrez manuellement votre origine :");
                    origin = sc.nextLine();
                    break;
                default:
                    break;
            }
            GeoCoordinate originCoordinates = is.getCoordinatesFromOpenStreetMap(origin);

            System.out.println("Destination :\n 1. Polytech Nice Sophia\n 2. Lyon Brasserie Georges\n 3. Bruxelles Atomium\n 4. Autres");
            String destinationChoice = sc.nextLine();
            String destination = "";
            switch (destinationChoice) {
                case "1":
                    destination = "Polytech Nice Sophia";
                    break;
                case "2":
                    destination = "Lyon Brasserie Georges";
                    break;
                case "3":
                    destination = "Bruxelles Atomium";
                    break;
                case "4":
                    System.out.println("Entrez manuellement votre origine :");
                    destination = sc.nextLine();
                    break;
                default:
                    break;
            }
            GeoCoordinate destinationCoordinates = is.getCoordinatesFromOpenStreetMap(destination);

            ArrayOfGeoCoordinate stationsArray = is.checkIfBikeIsWorthUsing(originCoordinates, destinationCoordinates);
            List<GeoCoordinate> stations = new ArrayList<>();
            for (GeoCoordinate geoCoordinate : stationsArray.getGeoCoordinate()) {
                stations.add(geoCoordinate);
            }
            if (stations==null || stations.size() < 2) {
                System.out.println("Walking is recommended (cycling itinerary too long)");
            } else {
                Itinerary itineraryOriginToStation = is.getBikingItinerary(originCoordinates, stations.get(0));
                Itinerary itineraryStationToStation = is.getBikingItinerary(stations.get(0), stations.get(1));
                Itinerary itineraryStationToDestination = is.getBikingItinerary(stations.get(1), destinationCoordinates);
                JXMapViewer mapViewer = new JXMapKit().getMainMap();
                JTextArea textArea = new JTextArea();

                // Display the viewer in a JFrame
                JFrame frame = new JFrame("Let's go biking !");
                frame.getContentPane().add(mapViewer);
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(textArea, BorderLayout.WEST);
                frame.getContentPane().add(mapViewer);
                frame.setVisible(true);

                // Create a TileFactoryInfo for OpenStreetMap
                TileFactoryInfo info = new OSMTileFactoryInfo();
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);

                List<GeoPosition> trackWalking1 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesWalking1 = itineraryOriginToStation.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesWalking1) {
                    trackWalking1.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                RoutePainter itineraire1 = new RoutePainter(trackWalking1);

                List<GeoPosition> trackBiking2 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesBiking2 = itineraryStationToStation.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesBiking2) {
                    trackBiking2.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                RoutePainter itineraire2 = new RoutePainter(trackBiking2);

                List<GeoPosition> trackWalking3 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesWalking3 = itineraryStationToDestination.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesWalking3) {
                    trackWalking3.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                RoutePainter itineraire3 = new RoutePainter(trackWalking3);

                RoutePainter.displayItineraire(mapViewer, itineraire1, itineraire2, itineraire3);
                mapViewer.zoomToBestFit(new HashSet<>(trackWalking1), 0.7);


                String receivedMessage = receiveMessageFromActiveMQ();
                var text = "";
                if (receivedMessage != null && !receivedMessage.isEmpty()) {
                    String[] steps = receivedMessage.split("\n");
                    for (String step : steps) {
                        text += step + "\n";
                    }
                } else {
                    text = "No message received";
                }
                textArea.setText(text);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String receiveMessageFromActiveMQ() {
        String brokerUrl = "tcp://localhost:61616";
        String queueName = "bikingItineraryQueue";

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive();

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String receivedText = textMessage.getText();
                consumer.close();
                session.close();
                connection.close();
                return receivedText;
            } else {
                System.out.println("Received unexpected message type");
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return null;
    }
}
