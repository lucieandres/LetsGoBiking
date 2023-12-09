using System.Collections.Generic;
using System.Runtime.Serialization;

namespace RoutingServer
{
    [DataContract]
    public class Itinerary
    {
        [DataMember(Name = "segments")] public List<Segment> Segments { get; set; }
        [DataMember(Name = "geometry")] public Geometry Geometry { get; set; }

        public override string ToString()
        {
            var result = "Itinerary: \n";
            foreach (var segment in Segments)
                result += segment + "\n";
            result += Geometry;
            return result;
        }

        public void Concatenate(Itinerary other)
        {
            if (other?.Segments != null)
                Segments.AddRange(other.Segments);
            if (other?.Geometry != null)
                Geometry.Coordinates.AddRange(other.Geometry.Coordinates);
        }
    }

}
