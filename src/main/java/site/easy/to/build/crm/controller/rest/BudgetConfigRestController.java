package site.easy.to.build.crm.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.BudgetConfig;
import site.easy.to.build.crm.models.AuthRequest;
import site.easy.to.build.crm.service.customer.BudgetConfigService;
import site.easy.to.build.crm.util.RenderResponse;

@RestController
@RequestMapping("/api")
public class BudgetConfigRestController {

    BudgetConfigService budgetConfigService;

    public BudgetConfigRestController(BudgetConfigService budgetConfigService) {
        this.budgetConfigService = budgetConfigService;
    }

    @PostMapping("/config/alert_rate")
    public ResponseEntity<RenderResponse<Map<String, Object>>> authenticateUser(@RequestBody BudgetConfig theBudgetConfig) {
            try {
                    double newAlertRate = theBudgetConfig.getAlertRate();
                    BudgetConfig budgetConfig = this.budgetConfigService.getLatestConfig();
                    
                    theBudgetConfig = new BudgetConfig(theBudgetConfig.getAlertRate(), budgetConfig.getMaxBudget());
                    this.budgetConfigService.save(theBudgetConfig);
                    Map<String, Object> data = new HashMap<>();
                    data.put("alert_rate", newAlertRate);
                    return ResponseEntity.ok(new RenderResponse<>(200, data, "Success of configuration for budget config", null));
            } catch (Exception e) {
                e.printStackTrace();
                    return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Error on configuration", e.getMessage()));
            }
    }

}
