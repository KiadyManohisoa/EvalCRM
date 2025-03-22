package site.easy.to.build.crm.config.entity;

import java.util.ArrayList;
import java.util.List;

import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.employee.Employee;
import site.easy.to.build.crm.entity.employee.EmployeeImportEntity;

public class ListImport {
    
    private static final List<ImportEntity> ImportEntities = new ArrayList<>(){ 
        { 
            add(new EmployeeImportEntity(0, "employee", Employee.class));
        } 
    };

    public static List<ImportEntity> getImportentities() {
        return ImportEntities;
    }



}
