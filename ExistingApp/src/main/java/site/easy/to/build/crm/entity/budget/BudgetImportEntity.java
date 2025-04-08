package site.easy.to.build.crm.entity.budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import jakarta.transaction.Transactional;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.models.BudgetImport;
import site.easy.to.build.crm.models.CustomerImport;
import site.easy.to.build.crm.service.customer.BudgetService;
import site.easy.to.build.crm.service.customer.CustomerService;

public class BudgetImportEntity extends ImportEntity {
    
    public BudgetImportEntity (int id, String libelle, Class <?> clazz) {
        super(id, libelle, clazz);
    }

    private BudgetService getBudgetService() {
        return this.getApplicationContext().getBean(BudgetService.class);
    }

    private CustomerService getCustomerService() {
        return this.getApplicationContext().getBean(CustomerService.class);
    }

    void saveBudget(Connection co, Budget budget) throws Exception {
        PreparedStatement st = null;
        String query = "insert into customer_budget (amount, added_at, customer_id) "
                +
                "values (?, ?, ?)";
        try {
            st = co.prepareStatement(query);
            st.setDouble(1, budget.getAmount());
            st.setTimestamp(2, Timestamp.valueOf(budget.getAddedAt()));
            st.setInt(3, budget.getCustomer().getCustomerId());
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
    
    @Override
    public void processAndSaveToDatabase(Connection conn, List<Object> objects) throws Exception {
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < objects.size(); i++) {
                if(objects.get(i) instanceof BudgetImport) {
                    BudgetImport budgetImport = (BudgetImport) objects.get(i);
                    Customer customer = this.getCustomerService().findByEmail(budgetImport.getCustomerMail());
                    if(customer==null) {
                        throw new Exception("Customer not found for mail "+budgetImport.getCustomerMail());
                    }
                    Budget budget = budgetImport.transcriptToBudget(customer);
                    this.saveBudget(conn, budget);
                    // this.getBudgetService().saveBudget(customer, budget);
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
