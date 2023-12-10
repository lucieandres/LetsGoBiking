package com.soc.client;

import com.soap.ws.client.generated.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.viewer.GeoPosition;

import javax.jms.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Client {

    private static List<String> receivedMessages = new ArrayList<>();
    private static int currentIndexText = 0;
    private static int currentIndexZoom = 0;

    public static void main(String[] args) {

        try {
            LetsGoBiking service = new LetsGoBiking();
            ILetsGoBiking is = service.getBasicHttpBindingILetsGoBiking();
            Scanner sc = new Scanner(System.in);

            System.out.println("Origin :\n 1. Polytech Nice Sophia\n 2. Lyon Brasserie Georges\n 3. Bruxelles Atomium\n 4. Nice place massena\n 5. Others");
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
                    origin = "Nice place massena";
                    break;
                case "5":
                    System.out.println("Write your own origin :");
                    origin = sc.nextLine();
                    break;
                default:
                    break;
            }
            GeoCoordinate originCoordinates = is.getCoordinatesFromOpenStreetMap(origin);

            System.out.println("Destination :\n 1. Polytech Nice Sophia\n 2. Lyon Brasserie Georges\n 3. Bruxelles Atomium\n 4. Nice place massena\n 5. Others");
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
                    destination = "Nice place massena";
                    break;
                case "5":
                    System.out.println("Write your own destination :");
                    destination = sc.nextLine();
                    break;
                default:
                    break;
            }
            GeoCoordinate destinationCoordinates = is.getCoordinatesFromOpenStreetMap(destination);

            if (is.checkIfBikeIsWorthUsing(originCoordinates, destinationCoordinates)==null) {
                Itinerary itineraryOriginToStation = is.getBikingItinerary(originCoordinates, destinationCoordinates);

                JTextArea textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setColumns(30);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JFrame frame = new JFrame("Let's go biking !");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setSize(800, 600);

                TileFactoryInfo info = new OSMTileFactoryInfo();
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                JXMapViewer mapViewer = new JXMapKit().getMainMap();
                mapViewer.setTileFactory(tileFactory);

                List<GeoPosition> trackWalking = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesWalking1 = itineraryOriginToStation.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesWalking1) {
                    trackWalking.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                Itineraire itineraire = new Itineraire(trackWalking);

                Itineraire.displayItineraire(mapViewer, itineraire);
                textArea.append("Walking is the best option for you !\nClick on \"Display next step\" to see the itinerary !\n\n");

                receivedMessages = receiveMessagesFromActiveMQ();

                JPanel textAndButtonPanel = new JPanel();
                textAndButtonPanel.setLayout(new BorderLayout());
                textAndButtonPanel.add(scrollPane, BorderLayout.CENTER);

                JButton nextButton = new JButton("Display next step");
                nextButton.addActionListener(e -> displayNextMessage(textArea, mapViewer, Arrays.asList(trackWalking)));
                textAndButtonPanel.add(nextButton, BorderLayout.SOUTH);

                frame.add(mapViewer, BorderLayout.CENTER);
                frame.add(textAndButtonPanel, BorderLayout.EAST);

                frame.setVisible(true);

                Set<GeoPosition> positions = new HashSet<>(trackWalking);
                mapViewer.zoomToBestFit(positions, 0.7);

            } else {

                ArrayOfGeoCoordinate stationsArray = is.checkIfBikeIsWorthUsing(originCoordinates, destinationCoordinates);
                List<GeoCoordinate> stations = new ArrayList<>();
                for (GeoCoordinate geoCoordinate : stationsArray.getGeoCoordinate()) {
                    stations.add(geoCoordinate);
                }

                Itinerary itineraryOriginToStation = is.getBikingItinerary(originCoordinates, stations.get(0));
                Itinerary itineraryStationToStation = is.getBikingItinerary(stations.get(0), stations.get(1));
                Itinerary itineraryStationToDestination = is.getBikingItinerary(stations.get(1), destinationCoordinates);

                JTextArea textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setColumns(30);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JFrame frame = new JFrame("Let's go biking !");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setSize(800, 600);

                TileFactoryInfo info = new OSMTileFactoryInfo();
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                JXMapViewer mapViewer = new JXMapKit().getMainMap();
                mapViewer.setTileFactory(tileFactory);

                List<GeoPosition> trackWalking1 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesWalking1 = itineraryOriginToStation.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesWalking1) {
                    trackWalking1.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                Itineraire walkingItinerary1 = new Itineraire(trackWalking1);

                List<GeoPosition> trackBiking2 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesBiking2 = itineraryStationToStation.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesBiking2) {
                    trackBiking2.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                Itineraire bikingItinerary2 = new Itineraire(trackBiking2);

                List<GeoPosition> trackWalking3 = new ArrayList<>();
                List<ArrayOfdouble> geoCoordinatesWalking3 = itineraryStationToDestination.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble();
                for (ArrayOfdouble geoCoordinate : geoCoordinatesWalking3) {
                    trackWalking3.add(new GeoPosition(geoCoordinate.getDouble().get(0), geoCoordinate.getDouble().get(1)));
                }
                Itineraire walkingItinerary3 = new Itineraire(trackWalking3);

                Itineraire.displayItineraire(mapViewer, walkingItinerary1, bikingItinerary2, walkingItinerary3);
                textArea.append("Your itinerary is composed of 3 steps : Click on \"Display next step\" to see the first step !\n\n");

                receivedMessages = receiveMessagesFromActiveMQ();

                JPanel textAndButtonPanel = new JPanel();
                textAndButtonPanel.setLayout(new BorderLayout());
                textAndButtonPanel.add(scrollPane, BorderLayout.CENTER);

                JButton nextButton = new JButton("Display next step");
                nextButton.addActionListener(e -> displayNextMessage(textArea, mapViewer, Arrays.asList(trackWalking1, trackBiking2, trackWalking3)));
                textAndButtonPanel.add(nextButton, BorderLayout.SOUTH);

                frame.add(mapViewer, BorderLayout.CENTER);
                frame.add(textAndButtonPanel, BorderLayout.EAST);

                frame.setVisible(true);

                Set<GeoPosition> positions = new HashSet<>(trackBiking2);
                mapViewer.zoomToBestFit(positions, 0.7);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static List<String> receiveMessagesFromActiveMQ() {
        String brokerUrl = "tcp://localhost:61616";
        String queueName = "bikingItineraryQueue";
        List<String> receivedMessages = new ArrayList<>();

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(destination);

            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 5000) { // Receive messages for 5 seconds
                Message message = consumer.receive(100); // Wait for 100ms for a message
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String receivedText = textMessage.getText();
                    receivedMessages.add(receivedText);
                }
            }

            consumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

        return receivedMessages;
    }

    private static void displayNextMessage(JTextArea textArea, JXMapViewer mapViewer, List<List<GeoPosition>> tracks) {
        if (currentIndexText < receivedMessages.size()) {
            String nextMessage = receivedMessages.get(currentIndexText);
            textArea.append(nextMessage + "\n\n");
            currentIndexText++;
            if (currentIndexZoom < tracks.size()) {
                Set<GeoPosition> positions = new HashSet<>(tracks.get(currentIndexZoom));
                mapViewer.zoomToBestFit(positions, 0.7);
                currentIndexZoom++;
            }
        } else {
            textArea.append("You have reached the end of the itinerary !\n\n");
        }
    }


}
