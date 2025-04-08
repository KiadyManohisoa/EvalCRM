package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import site.easy.to.build.crm.entity.budget.BudgetConfig;

@Repository
public interface BudgetConfigRepository extends JpaRepository<BudgetConfig, Integer> {

    @Query(value = "SELECT * FROM budget_config ORDER BY config_date DESC LIMIT 1", nativeQuery = true)
    BudgetConfig findLatestConfig();

    
}
