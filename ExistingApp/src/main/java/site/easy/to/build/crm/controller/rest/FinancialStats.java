package site.easy.to.build.crm.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.models.AuthRequest;
import site.easy.to.build.crm.service.customer.BudgetService;
import site.easy.to.build.crm.service.customer.CustomerExpensesService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.RenderResponse;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api")
@RestController
public class FinancialStats {
    
    private final TicketService ticketService;
    private final BudgetService budgetService;
    private final LeadService leadService;
    private final CustomerExpensesService customerExpensesService;

    public FinancialStats(TicketService ticketService, BudgetService budgetService, LeadService leadService, CustomerExpensesService customerExpensesService) {
        this.ticketService = ticketService;
        this.budgetService = budgetService;
        this.leadService = leadService;
        this.customerExpensesService = customerExpensesService;
    }

//     @GetMapping("/financial/stat")
//     public ResponseEntity<RenderResponse<Map<String, Object>>> getTotal() {
//             try {
//                 double total = this.budgetService.getTotalBudget();
//                     Map<String, Object> userData = new HashMap<>();
//                     userData.put("total_depenses", total);
//                     return ResponseEntity.ok(new RenderResponse<>(200, userData, "Success", null));
//             } catch (Exception e) {
//                     // e.printStackTrace();
//                     return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Error", e.getMessage()));
//             }
//     }
    

    @GetMapping("/financial/stat")
    public ResponseEntity<RenderResponse<Map<String, Object>>> getFinancialStats() {
            try {
                    double total = this.budgetService.getTotalBudget();
                    double total_expenses = this.customerExpensesService.findTotalExpenses();
                    List<Ticket> tickets = this.ticketService.findAll();
                    List<Lead> leads = this.leadService.findAll();
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("count_tickets", tickets.size());
                    userData.put("count_leads", leads.size());
                    userData.put("total_depenses", total);
                    userData.put("total_expenses", total_expenses);

                    return ResponseEntity.ok(new RenderResponse<>(200, userData, "Success", null));
            } catch (Exception e) {
                    // e.printStackTrace();
                    return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Error", e.getMessage()));
            }
    }
        
    

}
