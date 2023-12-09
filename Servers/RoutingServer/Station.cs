using System;

namespace ProxyCacheServer
{
    public class Station
    {
        public int number { get; set; }
        public string contractName { get; set; }
        public string name { get; set; }
        public string address { get; set; }
        public Position position { get; set; }
        public bool banking { get; set; }
        public bool bonus { get; set; }
        public string status { get; set; }
        //public DateTime lastUpdate { get; set; }
        public bool connected { get; set; }
        public bool overflow { get; set; }
        public object shape { get; set; }
        public StandInfo totalStands { get; set; }
        public StandInfo mainStands { get; set; }
        public object overflowStands { get; set; }
    }

}
