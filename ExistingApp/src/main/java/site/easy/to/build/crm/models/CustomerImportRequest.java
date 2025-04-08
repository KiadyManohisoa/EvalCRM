package site.easy.to.build.crm.models;

import java.util.List;

public class CustomerImportRequest {
    
    private CustomerImport customer;
    private List<TicketLeadExpenseImport> expenses;

    // Getters et Setters
    public CustomerImport getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerImport customer) {
        this.customer = customer;
    }

    public List<TicketLeadExpenseImport> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<TicketLeadExpenseImport> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        String str = customer.toString();
        str+="\n";
        for(TicketLeadExpenseImport t : this.getExpenses()) {
            str+=t.toString();
            str+="\n";
        }
        return str;
    }

    
}
