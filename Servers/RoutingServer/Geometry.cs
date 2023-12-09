using System.Collections.Generic;
using System.Runtime.Serialization;

namespace RoutingServer
{
    [DataContract]
    public class Geometry
    {
        [DataMember(Name = "coordinates")]
        public List<double[]> Coordinates { get; set; }

        public override string ToString()
        {
            var result = "Geometry: \n";
            foreach (var coordinate in Coordinates)
                result += $"[{coordinate[0]}, {coordinate[1]}], ";
            return result.TrimEnd(',', ' ') + "\n";
        }
    }

}
