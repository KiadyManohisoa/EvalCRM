package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.easy.to.build.crm.entity.budget.Budget;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    
    List<Budget> findByCustomer_CustomerId(Integer customerId);

    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM customer_budget WHERE customer_id = :customerId", 
           nativeQuery = true)
    Double findTotalBudgetByCustomerId(@Param("customerId") Integer customerId);
}

