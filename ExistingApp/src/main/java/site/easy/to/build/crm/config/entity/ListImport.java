package site.easy.to.build.crm.config.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.budget.BudgetImportEntity;
import site.easy.to.build.crm.entity.customer.CustomerImportEntity;
import site.easy.to.build.crm.entity.employee.Employee;
import site.easy.to.build.crm.entity.employee.EmployeeImportEntity;
import site.easy.to.build.crm.entity.ticked_lead.TicketLeadExpenseEntity;
import site.easy.to.build.crm.models.BudgetImport;
import site.easy.to.build.crm.models.CustomerImport;
import site.easy.to.build.crm.models.TicketLeadExpenseImport;
@Component
public class ListImport {
    
    private final List<ImportEntity> importEntities;
    
    @Autowired
    public ListImport(ApplicationContext context) {
        this.importEntities = initEntities(context);
    }
    
    private List<ImportEntity> initEntities(ApplicationContext context) {
        List<ImportEntity> entities = new ArrayList<>();
        
        // Employee
        EmployeeImportEntity employeeEntity = new EmployeeImportEntity(0, "employee", Employee.class);
        employeeEntity.setApplicationContext(context);
        entities.add(employeeEntity);
        
        // Customer
        CustomerImportEntity customerEntity = new CustomerImportEntity(1, "customer", CustomerImport.class);
        customerEntity.setApplicationContext(context);
        entities.add(customerEntity);

        //Budget 
        BudgetImportEntity budgetImportEntity = new BudgetImportEntity(2, "budget", BudgetImport.class);
        budgetImportEntity.setApplicationContext(context);
        entities.add(budgetImportEntity);

        //TicketLeadExpense
        TicketLeadExpenseEntity ticketLeadExpenseEntity = new TicketLeadExpenseEntity(3, "ticket-lead-expense", TicketLeadExpenseImport.class);
        ticketLeadExpenseEntity.setApplicationContext(context);
        entities.add(ticketLeadExpenseEntity);
        
        return Collections.unmodifiableList(entities);
    }
    
    public List<ImportEntity> getImportentities() {
        return importEntities;
    }
}