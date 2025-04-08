package site.easy.to.build.crm.entity.ticked_lead;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.CustomerExpenses;
import site.easy.to.build.crm.models.BudgetImport;
import site.easy.to.build.crm.models.TicketLeadExpenseImport;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.user.UserService;

public class TicketLeadExpenseEntity extends ImportEntity  {

    public TicketLeadExpenseEntity (int id, String libelle, Class <?> clazz) {
        super(id, libelle, clazz);
    }

    private CustomerService getCustomerService() {
        return this.getApplicationContext().getBean(CustomerService.class);
    }

    private UserService getUserService() {
        return this.getApplicationContext().getBean(UserService.class);
    }

    Customer getCustomerConcerned(TicketLeadExpenseImport tle) throws Exception {
        Customer customer = this.getCustomerService().findByEmail(tle.getCustomerEmail());
        if(customer==null) {
            throw new Exception("Customer not found for mail "+tle.getCustomerEmail());
        }
        return customer;
    }

    void processAsTicket(TicketLeadExpenseImport tle, Connection co) throws Exception {
        Customer customer = this.getCustomerConcerned(tle);
        Ticket ticket = new Ticket();
        ticket.setSubject(tle.getSubjectOrName());
        ticket.setStatus(tle.getStatus());
        ticket.genRandPriority();
        ticket.setCustomer(customer);
        ticket.setManager(this.getUserService().findRandUser());
        ticket.setEmployee(this.getUserService().findRandUser());
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.saveSelf(co);
        double expense = tle.getExpense();
        CustomerExpenses cexp = new CustomerExpenses(expense, ticket);
        cexp.saveSelf(co);
        Budget budget = new Budget();
        budget.setAmount(expense*-1);
        budget.setAddedAt();
        budget.setCustomer(customer);
        // budget.saveSelf(co);
    }

    void processAsLead(TicketLeadExpenseImport tle, Connection co) throws Exception {
        Customer customer = this.getCustomerConcerned(tle);
        Lead lead = new Lead();
        lead.setName(tle.getSubjectOrName());
        lead.setStatus(tle.getStatus());
        lead.setManager(this.getUserService().findRandUser());
        lead.setEmployee(this.getUserService().findRandUser());
        lead.setCustomer(customer);
        lead.setCreatedAt(LocalDateTime.now());
        lead.saveSelf(co);
        double expense = tle.getExpense();
        CustomerExpenses cexp = new CustomerExpenses(expense, lead);
        cexp.saveSelf(co);
        Budget budget = new Budget();
        budget.setAmount(expense*-1);
        budget.setAddedAt();
        budget.setCustomer(customer);
        // budget.saveSelf(co);
    }

    @Override
    public void processAndSaveToDatabase(Connection conn, List<Object> objects) throws Exception {
        try {
            conn.setAutoCommit(false);

            for(int i=0;i<objects.size();i++) {
                if(objects.get(i) instanceof TicketLeadExpenseImport) {
                    TicketLeadExpenseImport tle = (TicketLeadExpenseImport) objects.get(i);
                    if(tle.getType().equalsIgnoreCase("lead")) {
                        this.processAsLead(tle, conn);
                    }
                    else if(tle.getType().equalsIgnoreCase("ticket")) {
                        this.processAsTicket(tle, conn);
                    }

                }
            }

            conn.commit();
        }
        catch(Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        }
        finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }


}
