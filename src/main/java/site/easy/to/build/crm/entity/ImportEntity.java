package site.easy.to.build.crm.entity;

import java.sql.Connection;
import java.util.List;

public abstract class ImportEntity {
    private int id;
    private String libelle;
    private Class<?> clazz;

    public ImportEntity(int id, String libelle, Class<?> clazz) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setClazz(clazz);
    }

    public abstract void saveToDatabase(Connection conn, List<Object> objects) throws Exception;
    
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
}