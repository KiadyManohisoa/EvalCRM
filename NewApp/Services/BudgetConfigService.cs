using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using NewCRMApp.Models;

namespace NewCRMApp.Services;

public class BudgetConfigService
{

    private readonly HttpClient _httpClient;
    private readonly string _baseUrl;

    public BudgetConfigService(HttpClient httpClient, IConfiguration configuration)
    {
        _httpClient = httpClient;
        _baseUrl = configuration["ApiSettings:BaseUrl"];
    }

    public async Task<RenderResponse<Dictionary<string, object>>> DoConfigAsync(decimal alertRate)
    {
        var options = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase, 
            WriteIndented = true
        };

        var requestPayload = new
        {
            alertRate = alertRate
        };

        string requestJson = JsonSerializer.Serialize(requestPayload, options);
        Console.WriteLine("🔹 Requête envoyée (JSON) :\n" + requestJson);

        var jsonContent = new StringContent(requestJson, Encoding.UTF8, "application/json");

        // Appel à l'API Java
        HttpResponseMessage response = await _httpClient.PostAsync($"{_baseUrl}/config/alert_rate", jsonContent);

        // Lire la réponse de l'API
        string responseString = await response.Content.ReadAsStringAsync();
        Console.WriteLine("🔹 Réponse brute reçue :\n" + responseString);

        // Désérialiser la réponse en un objet RenderResponse<Dictionary<string, object>>
        RenderResponse<Dictionary<string, object>> renderResponse = JsonSerializer.Deserialize<RenderResponse<Dictionary<string, object>>>(
            responseString,
            new JsonSerializerOptions { PropertyNameCaseInsensitive = true }
        );

        Console.WriteLine("🔹 Objet désérialisé :\n" + renderResponse.ToString());

        return renderResponse;
    }


}