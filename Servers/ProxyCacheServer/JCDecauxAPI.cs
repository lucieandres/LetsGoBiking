using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.ServiceModel;
using System.Text.Json;
using System.Threading.Tasks;

namespace ProxyCacheServer
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    class JCDecauxAPI
    {
        private static readonly HttpClient proxyClient = new HttpClient();
        private const string JCDecauxAPIKey = "ad32bedbf38ecd157fb582847e561d8c4d5a2341";
        string query, url, response;

        public JCDecauxAPI()
        {
            if (!proxyClient.DefaultRequestHeaders.Contains("User-Agent"))
            {
                proxyClient.DefaultRequestHeaders.Add("User-Agent", "LetsGoBikingServer");
            }
        }

        private static async Task<string> JCDecauxAPICall(string url, string query)
        {
            HttpResponseMessage response = await proxyClient.GetAsync(url + "?" + query);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadAsStringAsync();
        }

        public async Task<string> GetStationsWithBikesAsync()
        {
            url = "https://api.jcdecaux.com/vls/v3/stations";
            query = "apiKey=" + JCDecauxAPIKey;
            response = await JCDecauxAPICall(url, query);

            if (response.Length > int.MaxValue)
            {
                throw new Exception("La réponse de l'API dépasse la limite autorisée.");
            }

            List<Station> stationDataList = JsonSerializer.Deserialize<List<Station>>(response);

            string openStationsWithBikes = JsonSerializer.Serialize(stationDataList
                .Where(station =>
                {
                    return station.status == "OPEN" && station.totalStands.availabilities.bikes > 0;
                }));

            return openStationsWithBikes;
        }


        public async Task<string> GetStationsWithSpotsAsync()
        {
            url = "https://api.jcdecaux.com/vls/v3/stations";
            query = "apiKey=" + JCDecauxAPIKey;
            response = await JCDecauxAPICall(url, query);

            if (response.Length > int.MaxValue)
            {
                throw new Exception("La réponse de l'API dépasse la limite autorisée.");
            }

            List<Station> stationDataList = JsonSerializer.Deserialize<List<Station>>(response);

            string openStationsWithSpots = JsonSerializer.Serialize(stationDataList
                .Where(station =>
                {
                    return station.status == "OPEN" && station.totalStands.availabilities.stands > 0;
                }));

            return openStationsWithSpots;
        }

    }
}
