using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using NewCRMApp.Models;

namespace NewCRMApp.Services
{
    public class TicketService
    {
        private readonly HttpClient _httpClient;
        private readonly ILogger<TicketService> _logger;

        public TicketService(HttpClient httpClient, ILogger<TicketService> logger)
        {
            _httpClient = httpClient;
            _logger = logger;
        }

        public async Task<List<Ticket>> GetTicketsAsync()
        {
            try
            {
                var response = await _httpClient.GetAsync("http://localhost:8080/api/list/tickets");

                if (!response.IsSuccessStatusCode)
                {
                    _logger.LogError($"Erreur API : {response.StatusCode}");
                    return new List<Ticket>();
                }

                string responseBody = await response.Content.ReadAsStringAsync();
                var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };

                var apiResponse = JsonSerializer.Deserialize<RenderResponse<List<Ticket>>>(responseBody, options);

                return apiResponse?.Data ?? new List<Ticket>();
            }
            catch (Exception ex)
            {
                _logger.LogError($"Exception : {ex.Message}");
                return new List<Ticket>();
            }
        }
    }
}
