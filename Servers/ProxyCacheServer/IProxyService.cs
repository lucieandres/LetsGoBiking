using System.Collections.Generic;
using System.ServiceModel;
using System.Threading.Tasks;
using RoutingServer;

namespace ProxyCacheServer
{
    [ServiceContract]
    public interface IProxyService
    {
        [OperationContract]
        Task<string> GetStationsWithBikesAsync();

        [OperationContract]
        Task<string> GetStationsWithSpots();

    }
}
