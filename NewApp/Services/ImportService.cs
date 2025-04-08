using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using NewCRMApp.Models;
namespace NewCRMApp.Services;

public class ImportService {

    private static readonly HttpClient client = new HttpClient();

    public async Task<string> SendImportRequest(CustomerImportRequest request)
    {
        var options = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase, 
            WriteIndented = true
        };

        string json = JsonSerializer.Serialize(request, options);
        Console.WriteLine("🔹 Requête envoyée (JSON) :\n" + json);

        var content = new StringContent(json, Encoding.UTF8, "application/json");

        try
        {
            var response = await client.PostAsync("http://localhost:8080/api/import/customer", content);
            var responseContent = await response.Content.ReadAsStringAsync(); // Récupérer la réponse détaillée

            Console.WriteLine("🔹 Réponse brute reçue :\n" + responseContent);

            if (response.IsSuccessStatusCode)
            {
                return "Importation réussie !";
            }
            else
            {
                return $"Erreur lors de l'importation : {response.StatusCode} - {response.ReasonPhrase}\nDétails : {responseContent}";
            }
        }
        catch (HttpRequestException httpEx)
        {
            return $"Erreur HTTP lors de l'appel API : {httpEx.Message}\nStackTrace : {httpEx.StackTrace}";
        }
        catch (Exception ex)
        {
            return $"Exception inattendue lors de l'appel API : {ex.Message}\nStackTrace : {ex.StackTrace}";
        }
    }
}