package site.easy.to.build.crm.controller.rest;

import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.service.customer.BudgetService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    private final BudgetService budgetService;

    public CustomerRestController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/budget/{idCustomer}")
    public Double getCustomerBudget(@PathVariable Integer idCustomer) {
        Customer customer = new Customer(idCustomer);
        this.budgetService.setCustomerBudget(customer);
        return customer.getBudget();
    }
    

}
