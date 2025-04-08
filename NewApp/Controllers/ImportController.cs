using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using NewCRMApp.Models;
using NewCRMApp.Services;

namespace NewCRMApp.Controllers;

    public class ImportController : Controller {

        private readonly ImportService _importService;

        // Injection du service
        public ImportController(ImportService importService)
        {
            _importService = importService;
        }

    public IActionResult Import(IFormFile file_name)
    {
        if (file_name == null || file_name.Length == 0)
            {
                ModelState.AddModelError("", "Veuillez sélectionner un fichier.");
                return View();
            }

            try
            {
                // Utilisation de la méthode FromFile pour transformer le fichier CSV en objets
                var customerImportRequest = CustomerImportRequest.FromFile(file_name);
                Console.WriteLine(customerImportRequest.Customer.ToString());
                for(int i = 0;i<customerImportRequest.Expenses.Count;i++) {
                    Console.WriteLine(customerImportRequest.Expenses[i].ToString());
                }
                // Envoi des données via le service
                var result = _importService.SendImportRequest(customerImportRequest).Result;

                TempData["SuccessMessage"] = result;
                return Redirect("/admin/import");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur : {ex.Message}");
                ModelState.AddModelError("", "Erreur lors du traitement du fichier : " + ex.Message);
                return View();
            }
    }

    
        // if (file_name == null || file_name.Length == 0)
        // {
        //     ModelState.AddModelError("", "Veuillez sélectionner un fichier.");
        //     return View();
        // }

        // try
        // {
        //     using (var reader = new StreamReader(file_name.OpenReadStream()))
        //     {
        //         List<string> lines = new List<string>();
        //         while (!reader.EndOfStream)
        //         {
        //             lines.Add(reader.ReadLine());
        //         }

        //         if (lines.Count < 4)
        //         {
        //             ModelState.AddModelError("", "Le fichier CSV est invalide.");
        //             return View();
        //         }

        //         string[] customerData = lines[1].Split(',');

        //         Customer customer = new Customer
        //         {
        //             Email = customerData[0].Trim(),
        //             Name = customerData[1].Trim()
        //         };

        //         Console.WriteLine(customer); 

        //         List<CustomerLeadExpense> expenses = new List<CustomerLeadExpense>();

        //         for (int i = 4; i < lines.Count; i++)
        //         {
        //             string[] expenseData = lines[i].Split(',');
        //             if (expenseData.Length < 5) continue;

        //             CustomerLeadExpense expense = new CustomerLeadExpense
        //             {
        //                 Email = expenseData[0].Trim(),
        //                 SubjectOrName = expenseData[1].Trim(),
        //                 Type = expenseData[2].Trim(),
        //                 Status = expenseData[3].Trim(),
        //                 Expense = decimal.Parse(expenseData[4].Trim(), CultureInfo.InvariantCulture)
        //             };

        //             expenses.Add(expense);
        //             Console.WriteLine(expense); 
        //         }

        //         Console.WriteLine($"Import terminé : {expenses.Count} CustomerLeadExpense trouvés.");

        //         TempData["SuccessMessage"] = "Importation réussie !";
        //         return Redirect("/admin/import");
        //     }
        // }
        // catch (Exception ex)
        // {
        //     Console.WriteLine($"Erreur : {ex.Message}");
        //     Console.WriteLine($"StackTrace : {ex.StackTrace}");
        //     ModelState.AddModelError("", "Erreur lors du traitement du fichier : " + ex.Message);
        //     return View();
        // }

}
