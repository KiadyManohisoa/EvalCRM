using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace NewCRMApp.Models;

public class CustomerImportRequest
{
    public Customer Customer { get; set; }
    public List<CustomerLeadExpense> Expenses { get; set; }

    public static CustomerImportRequest FromFile(IFormFile file)
        {
            var customerImportRequest = new CustomerImportRequest();
            customerImportRequest.Expenses = new List<CustomerLeadExpense>();

            using (var reader = new StreamReader(file.OpenReadStream()))
            {
                List<string> lines = new List<string>();
                while (!reader.EndOfStream)
                {
                    lines.Add(reader.ReadLine());
                }

                if (lines.Count < 4)
                {
                    throw new Exception("Le fichier CSV est invalide.");
                }

                // Création de l'objet Customer à partir de la deuxième ligne
                string[] customerData = lines[1].Split(',');
                customerImportRequest.Customer = new Customer
                {
                    Email = customerData[0].Trim(),
                    Name = customerData[1].Trim()
                };

                // Création de la liste des CustomerLeadExpense à partir des lignes suivantes
                for (int i = 4; i < lines.Count; i++)
                {
                    string[] expenseData = lines[i].Split(',');
                    if (expenseData.Length < 5) continue;

                    var expense = new CustomerLeadExpense
                    {
                        CustomerEmail = expenseData[0].Trim(),
                        SubjectOrName = expenseData[1].Trim(),
                        Type = expenseData[2].Trim(),
                        Status = expenseData[3].Trim(),
                        Expense = decimal.Parse(expenseData[4].Trim(), CultureInfo.InvariantCulture)
                    };

                    customerImportRequest.Expenses.Add(expense);
                }
            }

            return customerImportRequest;
        }

}
