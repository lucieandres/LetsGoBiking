using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.ServiceReference1;

namespace test
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            LetsGoBikingClient client = new LetsGoBikingClient();

            GeoCoordinate[] origins =  client.GetCoordinatesFromOpenStreetMap("lyon");
            GeoCoordinate origin = origins.FirstOrDefault();
            Console.WriteLine(origin);

            GeoCoordinate[] destinations =  client.GetCoordinatesFromOpenStreetMap("bruxelles");
            GeoCoordinate destination = destinations.FirstOrDefault();
            Console.WriteLine(destination);

            if (origin != null && destination != null)
            {
                if(client.CheckIfBikeIsWorthUsing(origin, destination))
                {
                    Console.WriteLine(client.GetBikingItinerary(origin, destination));
                }
            }
            else
            {
                Console.WriteLine("Erreur lors de la récupération des coordonnées.");
            }

            Console.ReadLine();
        }
    }

}
