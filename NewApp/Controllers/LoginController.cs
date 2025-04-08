using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using NewCRMApp.Models;
using NewCRMApp.Services;

namespace NewCRMApp.Controllers;

public class LoginController : Controller
{
    private readonly AuthService _authService;

    public LoginController(AuthService authService)
    {
        _authService = authService;
    }

    [HttpPost]
        public async Task<IActionResult> Login(AuthRequest authRequest)
        {
            var response = await _authService.AuthenticateAsync(authRequest);

            if (response != null && response.Code == 200 && response.Data != null)
            {
                HttpContext.Session.SetString("UserId", response.Data.Id.ToString());
                return Redirect("/admin/home");
            }

            TempData["ErrorMessage"] = response?.Error ?? "Authentication failed.";
            return Redirect("/");
        }
        
    [HttpGet]
    public IActionResult Logout()
    {
        HttpContext.Session.Clear(); 
        return Redirect("/"); 
    }
    
}