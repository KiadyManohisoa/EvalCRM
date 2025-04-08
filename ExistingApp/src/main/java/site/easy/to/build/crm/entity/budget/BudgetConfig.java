package site.easy.to.build.crm.entity.budget;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budget_config")
public class BudgetConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alert_rate", nullable = false)
    private Double alertRate;

    @Column(name = "max_budget", nullable = false)
    private Double maxBudget;

    @Column(name = "config_date", nullable = false, updatable = false)
    private LocalDateTime configDate;

    public BudgetConfig() {
    }

    public BudgetConfig(Double alertRate, Double maxBudget) {
        this.setAlertRate(alertRate);
        this.setMaxBudget(maxBudget);
        this.setConfigDate(LocalDateTime.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAlertRate() {
        return alertRate;
    }

    public void setAlertRate(Double alertRate) {
        this.alertRate = alertRate;
    }

    public Double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public LocalDateTime getConfigDate() {
        return configDate;
    }

    public void setConfigDate(LocalDateTime configDate) {
        this.configDate = configDate;
    }
}
