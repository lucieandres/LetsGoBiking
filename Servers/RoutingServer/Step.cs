using System.Runtime.Serialization;
namespace RoutingServer
{
    [DataContract]
    public class Step
    {
        [DataMember(Name = "distance")] public double Distance { get; set; }

        [DataMember(Name = "duration")] public double Duration { get; set; }

        [DataMember(Name = "instruction")] public string Instruction { get; set; }

        public override string ToString()
        {
            return $"Step: Distance = {Distance}m, Duration = {Duration}s, Instruction = {Instruction}";
        }
    }
}
