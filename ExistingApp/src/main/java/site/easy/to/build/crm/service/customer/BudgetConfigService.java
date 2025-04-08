package site.easy.to.build.crm.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.budget.BudgetConfig;
import site.easy.to.build.crm.repository.BudgetConfigRepository;

@Service
public class BudgetConfigService {

    @Autowired
    private BudgetConfigRepository budgetConfigRepository;

    public BudgetConfig save(BudgetConfig budget) {
        this.budgetConfigRepository.save(budget);
        return budget;
    }

    public BudgetConfig getLatestConfig() {
        return budgetConfigRepository.findLatestConfig();
    }
}
