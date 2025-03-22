package site.easy.to.build.crm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CleanDataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteDataFromTables(List<String> tableNames) throws Exception {
        try {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            for (String tableName : tableNames) {
                String deleteQuery = "DELETE FROM " + tableName;
                entityManager.createNativeQuery(deleteQuery).executeUpdate();

                String alterQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
                entityManager.createNativeQuery(alterQuery).executeUpdate();
            }

            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression des données", e);
        }
    }

}
