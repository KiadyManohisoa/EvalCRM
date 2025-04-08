using System.Text.Json;

namespace NewCRMApp.Models;

public class RenderResponse<T>
{
    public int Code { get; set; }
    public T Data { get; set; }
    public string Message { get; set; }
    public string Error { get; set; }

    public RenderResponse(int code, T data, string message, string error)
    {
        Code = code;
        Data = data;
        Message = message;
        Error = error;
    }

    public override string ToString()
    {
        return JsonSerializer.Serialize(this, new JsonSerializerOptions { WriteIndented = true });
    }
    
}
