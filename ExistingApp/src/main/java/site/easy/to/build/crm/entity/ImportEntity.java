package site.easy.to.build.crm.entity;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class ImportEntity {
    private int id;
    private String libelle;
    private Class<?> clazz;

    @Autowired
    protected transient ApplicationContext applicationContext;

    public ImportEntity(int id, String libelle, Class<?> clazz) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setClazz(clazz);
    }

    public abstract void processAndSaveToDatabase(Connection conn, List<Object> objects) throws Exception;
    
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}