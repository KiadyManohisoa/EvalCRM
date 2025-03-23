package site.easy.to.build.crm.service.customer;

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.budget.CustomerExpenses;
import site.easy.to.build.crm.repository.CustomerExpensesRepository;

@Service
public class CustomerExpensesService {

    private final CustomerExpensesRepository customerExpensesRepository;
    
    public CustomerExpensesService(CustomerExpensesRepository customerExpensesRepository) {
        this.customerExpensesRepository = customerExpensesRepository;
    }

    public CustomerExpenses save(CustomerExpenses custom_exp) {
        return this.customerExpensesRepository.save(custom_exp);
    }


}
