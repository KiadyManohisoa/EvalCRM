package site.easy.to.build.crm.entity.budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import site.easy.to.build.crm.entity.Customer;

@Table(name="customer_budget")
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Transient
    private LocalDate addedDate;
    @Transient
    private LocalTime addedTime;

    public void saveSelf(Connection co) throws Exception {
        PreparedStatement st = null;
        String query = "insert into customer_budget (amount, added_at, customer_id) "
                +
                "values (?, ?, ?)";
        try {
            st = co.prepareStatement(query);
            st.setDouble(1, this.getAmount());
            st.setTimestamp(2, Timestamp.valueOf(this.getAddedAt()));
            st.setInt(3, this.getCustomer().getCustomerId());
            st.executeUpdate();
        } 
        catch(Exception e) {
            throw e;
        }
        finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void setAddedAt() {
        if (this.getAddedDate() == null || this.getAddedTime() == null) {
            this.setAddedAt(LocalDateTime.now());
        } else {
            LocalDateTime combinedDateTime = LocalDateTime.of(this.getAddedDate(), this.getAddedTime());
            this.setAddedAt(combinedDateTime);
        }
    }

    public Budget() {}

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public LocalTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalTime addedTime) {
        this.addedTime = addedTime;
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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        if (addedAt == null) {
            this.addedAt = LocalDateTime.now();
        } else {
            this.addedAt = addedAt;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
