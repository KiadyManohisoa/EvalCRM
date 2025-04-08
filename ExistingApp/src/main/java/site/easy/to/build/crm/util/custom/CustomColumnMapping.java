package site.easy.to.build.crm.util.custom;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;

public class CustomColumnMapping extends HeaderColumnNameMappingStrategy<Object> {
   
    @Override
    public Object populateNewBean(String[] line) {
        try {
            for (int i = 0; i < line.length; i++) {
                if (line[i].matches("\"\\d{1,},\\d{1,}\"")) {  
                    line[i] = line[i].replace(",", ".").replace("\"", ""); 
                }
            }
            return super.populateNewBean(line);
        } catch (CsvFieldAssignmentException | CsvChainedException e) {
            throw new RuntimeException("Erreur lors de l'assignation des champs CSV : " + e.getMessage(), e);
        }
    }
    

    
}
