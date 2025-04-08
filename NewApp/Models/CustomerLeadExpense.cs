namespace NewCRMApp.Models;

public class CustomerLeadExpense
{
    public string CustomerEmail { get; set; }
    public string SubjectOrName { get; set; }
    public string Type { get; set; }
    public string Status { get; set; }
    public decimal Expense { get; set; }

    public override string ToString()
    {
        return $"CustomerLeadExpense [Email={CustomerEmail}, SubjectOrName={SubjectOrName}, Type={Type}, Status={Status}, Expense={Expense}]";
    }
    
}