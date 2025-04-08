using System;

namespace NewCRMApp.Models
{
    public class Ticket
    {
        public int TicketId { get; set; }
        public string Subject { get; set; }
        public string Status { get; set; }
        public string Priority { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
