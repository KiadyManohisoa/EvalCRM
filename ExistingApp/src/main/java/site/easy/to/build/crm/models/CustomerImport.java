package site.easy.to.build.crm.models;

import java.time.LocalDateTime;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.exception.FormatException;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.opencsv.bean.CsvBindByName;

import site.easy.to.build.crm.util.EmailTokenUtils;
import site.easy.to.build.crm.util.FormatUtil;

public class CustomerImport {

    @CsvBindByName(column = "customer_email")
    String email;

    @CsvBindByName(column = "customer_name")
    String name;

    public Customer transcriptToCostumer(User user) {
        Customer customer = new Customer();
        customer.setName(this.getName());
        customer.setUser(user);
        customer.setEmail(this.getEmail());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCountry("Madagascar");
        return customer;
    }

    public CustomerLoginInfo transcriptToCustomerLoginInfo(PasswordEncoder pwdEncdr) {
        CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
        customerLoginInfo.setEmail(this.getEmail());
        customerLoginInfo.setPassword(pwdEncdr.encode(this.getName()));
        customerLoginInfo.setToken(EmailTokenUtils.generateToken());
        customerLoginInfo.setPasswordSet(true);
        return customerLoginInfo;
    }

    public CustomerImport(String email, String name) throws FormatException {
        this.setEmail(email);
        this.setName(name);
    }

    public CustomerImport() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws FormatException {
        if(!FormatUtil.isValidEmail(email)) {
            throw new FormatException("Invalid email format \""+email+"\"");
        }
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws FormatException {
        if(name==null || name.isEmpty()) {
            throw new FormatException("Name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomerImport [email=" + email + ", name=" + name + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerImport that = (CustomerImport) o;
        return email != null ? email.equalsIgnoreCase(that.email) : that.email == null;
    }
    
    @Override
    public int hashCode() {
        return email != null ? email.toLowerCase().hashCode() : 0;
    }

}
