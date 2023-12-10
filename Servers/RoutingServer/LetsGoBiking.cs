using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Device.Location;
using Newtonsoft.Json.Linq;
using System.Globalization;
using JsonException = System.Text.Json.JsonException;
using System.ServiceModel;
using System.Text;
using System.Text.Json;
using Servers.ProxyServiceReference;
using ProxyCacheServer;
using Newtonsoft.Json;
using static System.Net.Mime.MediaTypeNames;
using Apache.NMS;
using Apache.NMS.ActiveMQ;

namespace RoutingServer
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    class LetsGoBiking : ILetsGoBiking
    {
        private static readonly HttpClient routingClient = new HttpClient();
        private static readonly ProxyServiceClient proxyServiceClient = new ProxyServiceClient();
        private const string ORSAPIKey = "5b3ce3597851110001cf62485abe3be6f519478387d58419cddca81b";

        public LetsGoBiking()
        {
            if (!routingClient.DefaultRequestHeaders.Contains("User-Agent"))
            {
                routingClient.DefaultRequestHeaders.Add("User-Agent", "LetsGoBikingServer");
            }
        }

        public async Task<Itinerary> GetBikingItinerary(GeoCoordinate originCoordinates, GeoCoordinate destinationCoordinates)
        {
            Itinerary itinerary = null;
            itinerary = await CalculateItinary(originCoordinates, destinationCoordinates);

            string instructionsJson = ConvertInstructionsToJson(itinerary);

            if (IsActivemqRunning())
            {
                Send(instructionsJson);
            }
            else
            {
                Console.WriteLine("ActiveMQ is not running. Unable to send message.");
            }
            return itinerary;
        }

        private bool IsActivemqRunning()
        {
            try
            {
                Uri connectUri = new Uri("activemq:tcp://localhost:61616");
                var connectionFactory = new ConnectionFactory(connectUri);
                using (var connection = connectionFactory.CreateConnection())
                {
                    return true;
                }
            }
            catch (Exception)
            {
                return false;
            }
        }

        public void Send(string message)
        {
            try
            {
                Uri connectUri = new Uri("activemq:tcp://localhost:61616");
                ConnectionFactory connectionFactory = new ConnectionFactory(connectUri);
                IConnection connection = connectionFactory.CreateConnection();
                connection.Start();
                ISession session = connection.CreateSession();
                IDestination destination = session.GetQueue("bikingItineraryQueue");
                IMessageProducer producer = session.CreateProducer(destination);
                var textMessage = session.CreateTextMessage(message);
                producer.Send(textMessage);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Erreur lors de l'envoi du message à ActiveMQ : " + ex.Message);
            }
        }

        private double CalculateDistance(double lat1, double lon1, double lat2, double lon2)
        {
            const double EarthRadius = 6371; // Rayon de la Terre en kilomètres

            // Conversion des degrés en radians
            double dLat = ToRadians(lat2 - lat1);
            double dLon = ToRadians(lon2 - lon1);

            // Formule de la haversine
            double a = Math.Sin(dLat / 2) * Math.Sin(dLat / 2) +
                       Math.Cos(ToRadians(lat1)) * Math.Cos(ToRadians(lat2)) *
                       Math.Sin(dLon / 2) * Math.Sin(dLon / 2);

            double c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));

            // Distance en kilomètres
            double distance = EarthRadius * c;
            return distance;
        }

        private double ToRadians(double angle)
        {
            return Math.PI * angle / 180.0;
        }

        private List<Station> ConvertStringToStationList(string stringStations)
        {
            try
            {
                List<Station> stations = JsonConvert.DeserializeObject<List<Station>>(stringStations);
                return stations;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Erreur lors de la conversion de la chaîne en objets Station : " + ex.Message);
                return new List<Station>();
            }
        }

        private async Task<Station> CalculateClosestStationAsync(GeoCoordinate coordinates, string param)
        {

            List<Station> closestStations = CalculateClosestStations(coordinates, param);

            double shortestSegmentDistance = double.MaxValue;
            Station closestStation = null;

            foreach (var station in closestStations)
            {
                var itinerary = await CalculateItinary(
                    coordinates,
                    new GeoCoordinate(station.position.latitude, station.position.longitude)
                );

                double totalDistance = itinerary.Segments.Sum(seg => seg.Distance);

                if (totalDistance < shortestSegmentDistance)
                {
                    shortestSegmentDistance = totalDistance;
                    closestStation = station;
                }
            }

            return closestStation;

        }

        private List<Station> CalculateClosestStations(GeoCoordinate coordinates, string param, int count = 5)
        {
            if (coordinates != null)
            {
                List<Station> availableStations = new List<Station>();
                if (param.Equals("bikes"))
                {
                    string stringStations = proxyServiceClient.GetStationsWithBikes();
                    availableStations = ConvertStringToStationList(stringStations);
                }
                if (param.Equals("spots"))
                {
                    string stringStations = proxyServiceClient.GetStationsWithSpots();
                    availableStations = ConvertStringToStationList(stringStations);
                }

                if (availableStations.Count > 0)
                {
                    List<Station> closestStations = new List<Station>();

                    availableStations.Sort((station1, station2) =>
                    {
                        double distance1 = CalculateDistance(coordinates.Latitude, coordinates.Longitude, station1.position.latitude, station1.position.longitude);
                        double distance2 = CalculateDistance(coordinates.Latitude, coordinates.Longitude, station2.position.latitude, station2.position.longitude);
                        return distance1.CompareTo(distance2);
                    });

                    closestStations = availableStations.Take(count).ToList();

                    return closestStations;
                }
            }
            return null;
        }


        private async Task<Itinerary> CalculateItinary(GeoCoordinate origin, GeoCoordinate destination)
        {
            var requestUri = BuildRequestUri(ORSAPIKey, origin, destination);

            try
            {
                var response = await routingClient.GetAsync(requestUri);
                response.EnsureSuccessStatusCode();

                var jsonResponse = await response.Content.ReadAsStringAsync();
                return ParseItinerary(JObject.Parse(jsonResponse));
            }
            catch (HttpRequestException ex)
            {
                Console.WriteLine(ex.Message);
            }

            return null;
        }

        private Itinerary ParseItinerary(JObject jsonData)
        {
            var itinerary = new Itinerary();
            itinerary.Segments = new List<Segment>();

            var segments = jsonData["features"][0]["properties"]["segments"];
            var geometry = jsonData["features"][0]["geometry"];

            foreach (var seg in segments)
            {
                var segment = new Segment
                {
                    Distance = (double)seg["distance"],
                    Duration = (double)seg["duration"],
                    Steps = new List<Step>()
                };

                var steps = seg["steps"];
                foreach (var st in steps)
                {
                    var step = new Step
                    {
                        Distance = (double)st["distance"],
                        Duration = (double)st["duration"],
                        Instruction = (string)st["instruction"]
                    };

                    segment.Steps.Add(step);
                }

                itinerary.Segments.Add(segment);
            }

            var coordinates = geometry["coordinates"];
            itinerary.Geometry = new Geometry
            {
                Coordinates = new List<double[]>()
            };

            foreach (var coordinate in coordinates)
            {
                var coord = new double[2];
                coord[0] = (double)coordinate[1];
                coord[1] = (double)coordinate[0];
                itinerary.Geometry.Coordinates.Add(coord);
            }

            return itinerary;
        }

        private string BuildRequestUri(string apiKey, GeoCoordinate originCoordinates, GeoCoordinate destinationCoordinates)
        {
            string origin = $"{originCoordinates.Longitude.ToString(CultureInfo.InvariantCulture)},{originCoordinates.Latitude.ToString(CultureInfo.InvariantCulture)}";
            string destination = $"{destinationCoordinates.Longitude.ToString(CultureInfo.InvariantCulture)},{destinationCoordinates.Latitude.ToString(CultureInfo.InvariantCulture)}";

            return $"https://api.openrouteservice.org/v2/directions/cycling-regular/?api_key={apiKey}&start={origin}&end={destination}";
        }
        
        private string ConvertInstructionsToJson(Itinerary itinerary)
        {
            if (itinerary == null || itinerary.Segments == null || itinerary.Segments.Count == 0)
            {
                return "null";
            }

            StringBuilder instructionsBuilder = new StringBuilder();

            try
            {
                foreach (var segment in itinerary.Segments)
                {
                    double distanceInKm = segment.Distance / 1000.0;
                    double durationInHours = segment.Duration / 3600.0;

                    instructionsBuilder.AppendLine($"Segment distance: {distanceInKm:F2} kilometers, duration: {durationInHours:F2} hours");
                    instructionsBuilder.AppendLine();

                    foreach (var step in segment.Steps)
                    {
                        instructionsBuilder.AppendLine($"Instruction: {step.Instruction}");
                        instructionsBuilder.AppendLine($"- Distance: {step.Distance} meters, Duration: {step.Duration} seconds");
                        instructionsBuilder.AppendLine(); 
                    }
                }

                return instructionsBuilder.ToString();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Erreur lors de la conversion en JSON : " + ex.Message);
                return "Erreur lors de la conversion des instructions.";
            }
        }

        public async Task<GeoCoordinate> GetCoordinatesFromOpenStreetMap(string address)
        {
            try
            {
                string url = $"https://nominatim.openstreetmap.org/search?q={Uri.EscapeDataString(address)}&format=json&addressdetails=1&limit=5&polygon_svg=1";

                HttpResponseMessage response = await routingClient.GetAsync(url);
                response.EnsureSuccessStatusCode();

                string responseData = await response.Content.ReadAsStringAsync();

                JArray jsonArray = JArray.Parse(responseData);
                if (jsonArray.Any())
                {
                    for (int i = 0; i < jsonArray.Count; i++)
                    {
                        JObject result = jsonArray[i] as JObject;
                        if (result != null)
                        {
                            double latitude;
                            double longitude;

                            if (double.TryParse(result.Value<string>("lat"), NumberStyles.Any, CultureInfo.InvariantCulture, out latitude) &&
                                double.TryParse(result.Value<string>("lon"), NumberStyles.Any, CultureInfo.InvariantCulture, out longitude))
                            {
                                GeoCoordinate geoCoordinate = new GeoCoordinate(latitude, longitude);
                                return geoCoordinate;
                            }
                            else
                            {
                                Console.WriteLine("Failed to parse latitude and longitude.");
                            }
                        }
                    }
                }
                else
                {
                    Console.WriteLine("No results found.");
                }
            }
            catch (HttpRequestException ex)
            {
                Console.WriteLine("HTTP Request Exception: " + ex.Message);
            }
            catch (JsonException ex)
            {
                Console.WriteLine("JSON Deserialization Error: " + ex.Message);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error fetching coordinates from OpenStreetMap: " + ex.Message);
            }

            return null;
        }


        public async Task<List<GeoCoordinate>> CheckIfBikeIsWorthUsing(GeoCoordinate originCoordinates, GeoCoordinate destinationCoordinates)
        {
            Station originStation = await CalculateClosestStationAsync(originCoordinates, "bikes");
            Station destinationStation = await CalculateClosestStationAsync(destinationCoordinates, "spots");

            if (originStation == null || destinationStation == null)
            {
                return null;
            }
            else
            {
                double bikingDistance = CalculateDistance(originCoordinates.Latitude, originCoordinates.Longitude, originStation.position.latitude, originStation.position.longitude);
                bikingDistance += CalculateDistance(originStation.position.latitude, originStation.position.longitude, destinationStation.position.latitude, destinationStation.position.longitude);
                bikingDistance += CalculateDistance(destinationStation.position.latitude, destinationStation.position.longitude, destinationCoordinates.Latitude, destinationCoordinates.Longitude);

                double walkingDistance = CalculateDistance(originCoordinates.Latitude, originCoordinates.Longitude, destinationCoordinates.Latitude, destinationCoordinates.Longitude);

                if (bikingDistance - 10 < walkingDistance)
                {
                    GeoCoordinate originStationCoordinate = new GeoCoordinate(originStation.position.latitude, originStation.position.longitude);
                    GeoCoordinate destinationStationCoordinate = new GeoCoordinate(destinationStation.position.latitude, destinationStation.position.longitude);
                    List<GeoCoordinate> result = new List<GeoCoordinate>() {  originStationCoordinate, destinationStationCoordinate };
                    return result;
                } else { 
                    return null;
                }
            }
        }
    }
}
