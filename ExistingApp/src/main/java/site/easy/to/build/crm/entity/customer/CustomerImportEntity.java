package site.easy.to.build.crm.entity.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.employee.Employee;
import site.easy.to.build.crm.models.CustomerImport;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.user.UserService;

public class CustomerImportEntity extends ImportEntity {

    private PasswordEncoder getPasswordEncoder() {
        return this.getApplicationContext().getBean(PasswordEncoder.class);
    }
    
    private CustomerLoginInfoService getCustomerLoginInfoService() {
        return this.getApplicationContext().getBean(CustomerLoginInfoService.class);
    }

    private UserService getUserService() {
        return this.getApplicationContext().getBean(UserService.class);
    }

    private CustomerService getCustomerService() {
        return this.getApplicationContext().getBean(CustomerService.class);
    }
    
    public CustomerImportEntity (int id, String libelle, Class <?> clazz) {
        super(id, libelle, clazz);
    }

    @Override 
    public void processAndSaveToDatabase(Connection conn, List<Object> objects) throws Exception {
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i) instanceof CustomerImport) {
                CustomerImport customerImport = (CustomerImport) objects.get(i);
                CustomerLoginInfo clinf = customerImport.transcriptToCustomerLoginInfo(this.getPasswordEncoder());
                clinf = this.getCustomerLoginInfoService().save(clinf);

                Customer customer = customerImport.transcriptToCostumer(this.getUserService().findRandUser());
                customer.setCustomerLoginInfo(clinf);
                this.getCustomerService().save(customer);
            }
        }
    }

}
