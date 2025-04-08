using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using NewCRMApp.Services;
using NewCRMApp.Models;

namespace NewCRMApp.Controllers
{
    [Route("ticket")]
    public class TicketController : Controller
    {
        private readonly TicketService _ticketService;

        public TicketController(TicketService ticketService)
        {
            _ticketService = ticketService;
        }

        [HttpGet("list")]
        public async Task<IActionResult> Index()
        {
            List<Ticket> tickets = await _ticketService.GetTicketsAsync();
            return View("~/Views/Details/ListTickets.cshtml", tickets);
        }
    }
}
