package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.easy.to.build.crm.entity.budget.CustomerExpenses;

@Repository
public interface CustomerExpensesRepository extends JpaRepository<CustomerExpenses, Integer> {
    
    @Override
    CustomerExpenses save(CustomerExpenses cust_exp);

}
