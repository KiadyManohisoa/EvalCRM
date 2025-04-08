package site.easy.to.build.crm.models;

import com.opencsv.bean.CsvBindByName;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.exception.FormatException;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.FormatUtil;

public class BudgetImport {

    @CsvBindByName(column = "customer_email")
    String customerMail;

    @CsvBindByName(column="Budget")
    String budgetAmountRaw;

    double budgetAmount;

    public String getBudgetAmountRaw() {
        return budgetAmountRaw;
    }

    public void setBudgetAmountRaw(String value) throws FormatException {
        try {
            double budget = Double.parseDouble(value.replace("\"", "").replace(",", "."));
            this.setBudgetAmount(budget);
        } catch (NumberFormatException e) {
            throw new FormatException("Invalid budget format " + value);
        }    
    }

    public Budget transcriptToBudget(Customer customer) {
        Budget budget = new Budget();
        budget.setAddedAt();
        budget.setAmount(this.getBudgetAmount());
        budget.setCustomer(customer);
        return budget;
    }

    public BudgetImport(String customerMail, double budgetAmount) throws Exception {
        this.setCustomerMail(customerMail);
        this.setBudgetAmount(budgetAmount);
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) throws FormatException {
        if(budgetAmount < 0 || budgetAmount == 0) {
            throw new FormatException("Budget cannot be negative or zero");
        }
        this.budgetAmount = budgetAmount;
    }

    public BudgetImport() {
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) throws FormatException {
        if(customerMail.isEmpty()) {
            throw new FormatException("Customer email cannot be empty");
        }
        if(!FormatUtil.isValidEmail(customerMail)) {
            throw new FormatException("Invalid email format \""+customerMail+"\"");
        }
        this.customerMail = customerMail;
    }

}
