package site.easy.to.build.crm.entity.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import site.easy.to.build.crm.entity.ImportEntity;

public class EmployeeImportEntity extends ImportEntity {
    
    public EmployeeImportEntity(int id, String libelle, Class<?> clazz) {
        super(id, libelle, clazz);
    }

    @Override
    public void processAndSaveToDatabase(Connection conn, List<Object> objects) throws Exception {
        String sql = "INSERT INTO employee (username, first_name, last_name, email, password, provider)"+ 
            " VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);

            conn.setAutoCommit(false); // Début de la transaction

            for (Object obj : objects) {
                Employee emp = (Employee) obj;
                stmt.setString(1, emp.getUsername());
                stmt.setString(2, emp.getFirstName());
                stmt.setString(3, emp.getLastName());
                stmt.setString(4, emp.getEmail());
                stmt.setString(5, emp.getPassword());
                stmt.setString(6, emp.getProvider());
                stmt.addBatch();
            }
            stmt.executeBatch(); 
            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback(); 
            }
            throw e;
        }
        finally {
            if(stmt!=null) {
                stmt.close();
            }
            if(conn!=null) {
                conn.setAutoCommit(true);
            }
        }
    }

    
}