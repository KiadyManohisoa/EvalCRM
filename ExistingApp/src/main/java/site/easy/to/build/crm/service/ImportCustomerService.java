package site.easy.to.build.crm.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.config.entity.ListImport;
import site.easy.to.build.crm.entity.customer.CustomerImportEntity;
import site.easy.to.build.crm.models.CustomerImport;
import site.easy.to.build.crm.models.CustomerImportRequest;
import site.easy.to.build.crm.models.TicketLeadExpenseImport;
import site.easy.to.build.crm.config.entity.ListImport;
import site.easy.to.build.crm.entity.ticked_lead.TicketLeadExpenseEntity;

@Service
public class ImportCustomerService {

    private final JdbcTemplate jdbcTemplate;

    public ImportCustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void processImport(CustomerImportRequest request, ListImport listImport) throws Exception {
        try(Connection co = jdbcTemplate.getDataSource().getConnection()) {

            if (request.getCustomer() == null || request.getExpenses() == null) {
                throw new IllegalArgumentException("Données d'importation invalides.");
            }

            saveCustomer(co, request.getCustomer(), listImport);
            saveExpenses(co, request.getExpenses(), listImport);

        }
        catch(Exception e) {
            throw e;
        }
    }

    private void saveCustomer(Connection co, CustomerImport customer, ListImport listImport) throws Exception {
        CustomerImportEntity customerImportEntity = (CustomerImportEntity)listImport.getImportentities().get(1);
        List<Object> customerList = new ArrayList<>();
        customerList.add(customer);

        customerImportEntity.processAndSaveToDatabase(co, customerList);
    }

    private void saveExpenses(Connection co,List<TicketLeadExpenseImport> expenses, ListImport listImport) throws Exception {
        TicketLeadExpenseEntity ticketLeadExpenseEntity = (TicketLeadExpenseEntity)listImport.getImportentities().get(3);

        List<Object> objectExpenses = new ArrayList<>(expenses);
        ticketLeadExpenseEntity.processAndSaveToDatabase(co, objectExpenses);        
    }

}
