using ProxyCacheServer;
using RoutingServer;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

public class ProxyService : IProxyService
{
    private GenericProxyCache<string> stationBikesCache = new GenericProxyCache<string>();
    private GenericProxyCache<string> stationSpotsCache = new GenericProxyCache<string>();
    private JCDecauxAPI jcDecauxAPI = new JCDecauxAPI();

    public ProxyService() { }

    public async Task<string> GetStationsWithBikesAsync()
    {
        return stationBikesCache.Get("stations_bikes", () => jcDecauxAPI.GetStationsWithBikesAsync().Result);
    }

    public async Task<string> GetStationsWithSpots()
    {
        return stationSpotsCache.Get("stations_spots", () => jcDecauxAPI.GetStationsWithSpotsAsync().Result);
    }

}
