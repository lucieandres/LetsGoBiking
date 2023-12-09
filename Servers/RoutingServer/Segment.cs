using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace RoutingServer
{
    [DataContract]
    public class Segment
    {
        [DataMember(Name = "steps")]
        public List<Step> Steps { get; set; }

        [DataMember(Name = "distance")]
        public double Distance { get; set; }

        [DataMember(Name = "duration")]
        public double Duration { get; set; }

        public override string ToString()
        {
            var result = $"Segment: Distance = {Distance}m, Duration = {Duration}s\n";
            foreach (var step in Steps)
                result += "  " + step + "\n";
            return result;
        }
    }

}
