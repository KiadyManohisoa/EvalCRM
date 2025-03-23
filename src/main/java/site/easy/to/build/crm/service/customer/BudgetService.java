package site.easy.to.build.crm.service.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.repository.BudgetRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void saveBudget(Customer customer, Budget budget) {
        budget.setCustomer(customer);
        budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByCustomerId(Integer customerId) {
        return budgetRepository.findByCustomer_CustomerId(customerId);
    }

}