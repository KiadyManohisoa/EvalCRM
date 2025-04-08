namespace NewCRMApp.Models;

public class Customer {

    public string Email { get; set; }
    public string Name { get; set; }

    public override string ToString()
    {
        return $"Customer [Email={Email}, Name={Name}]";
    }

}
