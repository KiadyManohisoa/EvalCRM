package site.easy.to.build.crm.entity.budget;

import java.time.LocalDateTime;

import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Table(name="customer_expenses")
@Entity
public class CustomerExpenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "expense_date", updatable = false)
    private LocalDateTime expense_date;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable=true)
    Ticket ticket;
    
    @ManyToOne 
    @JoinColumn(name = "lead_id", referencedColumnName = "lead_id", nullable=true)
    Lead lead;

    public CustomerExpenses(Double amount, Lead lead) {
        this.setAmount(amount);
        this.setLead(lead);
        this.setExpense_date();
    }

    public CustomerExpenses(Double amount, Ticket ticket) {
        this.setAmount(amount);
        this.setTicket(ticket);
        this.setExpense_date();
    }

    public CustomerExpenses() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getExpense_date() {
        return expense_date;
    }

    public void setExpense_date() {
        if(this.getExpense_date()==null) {
            this.setExpense_date(LocalDateTime.now());
        }
    }

    public void setExpense_date(LocalDateTime expense_date) {
        this.expense_date = expense_date;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }



}
