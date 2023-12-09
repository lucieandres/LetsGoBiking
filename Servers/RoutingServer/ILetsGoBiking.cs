using System.Collections.Generic;
using System.Device.Location;
using System.ServiceModel;
using System.Threading.Tasks;

namespace RoutingServer
{
    [ServiceContract]
    public interface ILetsGoBiking
    {
        [OperationContract]
        Task<Itinerary> GetBikingItinerary(GeoCoordinate originCoordinates, GeoCoordinate destinationCoordinates);

        [OperationContract]
        Task<GeoCoordinate> GetCoordinatesFromOpenStreetMap(string address);

        [OperationContract]
        Task<List<GeoCoordinate>> CheckIfBikeIsWorthUsing(GeoCoordinate originCoordinates, GeoCoordinate destinationCoordinates);

    }
}
