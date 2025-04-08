using Microsoft.AspNetCore.Mvc;
using NewCRMApp.Services;

namespace NewCRMApp.Controllers;

public class BudgetConfigController : Controller {
    private readonly BudgetConfigService _budgetConfigService;

        // Injecting the BudgetConfigService via constructor
     public BudgetConfigController(BudgetConfigService budgetConfigService)
     {
         _budgetConfigService = budgetConfigService;
     }
     
     [HttpPost]
     public async Task<IActionResult> Config(decimal new_alert_rate)
        {
            Console.WriteLine("voici ici à BudgetConfigController dans Config");
            Console.WriteLine("New alert rate is " + new_alert_rate);

            var result = await _budgetConfigService.DoConfigAsync(new_alert_rate);

            if (result.Code == 200)
            {
                TempData["SuccessMessage"] = "Configuration successfully updated.";
            }
            else
            {
                TempData["ErrorMessage"] = "Error occurred during configuration.";
            }

            return Redirect("/admin/config/budget");
        }
}
