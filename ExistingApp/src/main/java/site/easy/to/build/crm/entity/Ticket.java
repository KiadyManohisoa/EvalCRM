package site.easy.to.build.crm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import site.easy.to.build.crm.util.RandomGenerator;

@Entity
@Table(name = "trigger_ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "subject")
    @NotBlank(message = "Subject is required")
    private String subject;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived)$", message = "Invalid status")
    private String status;

    @Column(name = "priority")
    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "^(low|medium|high|closed|urgent|critical)$", message = "Invalid priority")

    private String priority;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Ticket duplicate () {
        Ticket ticket = new Ticket();
        ticket.setSubject(this.getSubject());
        ticket.setStatus(this.getStatus());
        ticket.setPriority(this.getPriority());
        ticket.setCustomer(this.getCustomer());
        ticket.setManager(this.getManager());
        ticket.setEmployee(this.getEmployee());
        ticket.setCreatedAt(this.getCreatedAt());
        return ticket;
    }


    public Ticket() {
    }

    public void saveSelf(Connection co) throws Exception {
        PreparedStatement st = null;
        ResultSet generatedKeys = null;
        String query = "insert into trigger_ticket (subject, status, priority, customer_id, manager_id, employee_id, created_At) "+
                "values (?, ?, ?, ?, ?, ?, ?)";
        try {
            st = co.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, this.getSubject());
            st.setString(2, this.getStatus());
            st.setString(3, this.getPriority());
            st.setInt(4, this.getCustomer().getCustomerId());
            st.setInt(5, this.getManager().getId());
            st.setInt(6, this.getEmployee().getId());
            st.setTimestamp(7, Timestamp.valueOf(this.getCreatedAt()));

            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée");
            }

            generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.setTicketId(generatedKeys.getInt(1)); 
            } else {
                throw new SQLException("Échec de récupération de l'ID généré");
            }
        } 
        catch(Exception e) {
            throw e;
        }
        finally {
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public void genRandPriority() {
        String [] priorityChoices = new String []{"low","medium","high","closed","urgent","critical"};
        int randIndex = RandomGenerator.genRandomInt(priorityChoices.length);
        this.setPriority(priorityChoices[randIndex]);
    }

    public Ticket(String subject, String description, String status, String priority, User manager, User employee, Customer customer, LocalDateTime createdAt) {
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.manager = manager;
        this.employee = employee;
        this.customer = customer;
        this.createdAt = createdAt;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
