using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using NewCRMApp.Models;

namespace NewCRMApp.Services;

public class AuthService {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl;

        public AuthService(HttpClient httpClient, IConfiguration configuration)
        {
            _httpClient = httpClient;
            _baseUrl = configuration["ApiSettings:BaseUrl"];
        }

    public async Task<RenderResponse<UserResponse>> AuthenticateAsync(AuthRequest request)
    {
        var options = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase, 
            WriteIndented = true
        };

        string requestJson = JsonSerializer.Serialize(request, options);
        Console.WriteLine("🔹 Requête envoyée (JSON) :\n" + requestJson);

        var jsonContent = new StringContent(requestJson, Encoding.UTF8, "application/json");

        HttpResponseMessage response = await _httpClient.PostAsync($"{_baseUrl}/login", jsonContent);

        string responseString = await response.Content.ReadAsStringAsync();
        Console.WriteLine("🔹 Réponse brute reçue :\n" + responseString);

        RenderResponse<UserResponse> renderResponse = JsonSerializer.Deserialize<RenderResponse<UserResponse>>(
            responseString,
            new JsonSerializerOptions { PropertyNameCaseInsensitive = true }
        );

        Console.WriteLine("🔹 Objet désérialisé :\n" + renderResponse.ToString());

        return renderResponse;
    }

}
