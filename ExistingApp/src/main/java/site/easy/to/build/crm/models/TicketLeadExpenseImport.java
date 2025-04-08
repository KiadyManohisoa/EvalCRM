package site.easy.to.build.crm.models;

import java.util.Arrays;

import com.opencsv.bean.CsvBindByName;

import site.easy.to.build.crm.config.entity.AppParameters;
import site.easy.to.build.crm.exception.FormatException;
import site.easy.to.build.crm.util.FormatUtil;

public class TicketLeadExpenseImport {

    @CsvBindByName(column = "customer_email")
    String customerEmail;

    @CsvBindByName(column="subject_or_name")
    String subjectOrName;

    @CsvBindByName(column = "type")
    String type;

    @CsvBindByName(column="status")
    String status;

    double expense;

    @CsvBindByName(column="expense")
    String expenseRaw;

    public String getExpenseRaw() {
        return expenseRaw;
    }

    public void setExpenseRaw(String value) throws FormatException {
        try {
            double thexpense = Double.parseDouble(value.replace("\"", "").replace(",", "."));
            this.setExpense(thexpense);
        } catch (NumberFormatException e) {
            throw new FormatException("Invalid format " + value);
        }    
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) throws FormatException {
        if(customerEmail.isEmpty()) {
            throw new FormatException("Customer email cannot be empty");
        }
        if(!FormatUtil.isValidEmail(customerEmail)) {
            throw new FormatException("Invalid email format \""+customerEmail+"\"");
        }
        this.customerEmail = customerEmail;
    }

    public String getSubjectOrName() {
        return subjectOrName;
    }

    public void setSubjectOrName(String subjectOrName) throws FormatException {
        if(subjectOrName == null || subjectOrName.isEmpty()) {
            throw new FormatException("Subject or name cannot be empty");
        }
        this.subjectOrName = subjectOrName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws FormatException {
        if(type == null || type.isEmpty()) {
            throw new FormatException("Type cannot be empty");
        }
        if (!Arrays.asList(AppParameters.getValidtypes()).contains(type)) {
            throw new FormatException("Invalid type: " + type);
        }
        this.type = type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) throws FormatException {
        if(status == null || status.isEmpty()) {
            throw new FormatException("Status cannot be empty");
        }
        if (!Arrays.asList(AppParameters.getValidstatuses()).contains(status)) {
            throw new FormatException("Invalid status: " + status);
        }
        this.status = status;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) throws FormatException {
        if(expense < 0 || expense == 0) {
            throw new FormatException("Expense cannot be negative or zero");
        }
        this.expense = expense;
    }

    @Override
    public String toString() {
        return "TicketLeadExpenseImport [customerEmail=" + customerEmail + ", subjectOrName=" + subjectOrName
                + ", type=" + type + ", status=" + status + ", expense=" + expense + "]";
    }
    
}
