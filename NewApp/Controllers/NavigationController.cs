using Microsoft.AspNetCore.Mvc;

namespace NewCRMApp.Controllers;

[Route("admin")]
public class NavigationController : Controller
{

    [HttpGet("import")]
    public IActionResult Import()
    {
        return View(); 
    }

    [HttpGet("home")]
    public IActionResult Home()
    {
        return View(); 
    }

    [HttpGet("graphs")]
    public IActionResult Graphs()
    {
        return View(); 
    }

    [HttpGet("config/budget")]
    public IActionResult ConfigBudget()
    {
        return View(); 
    }
}

