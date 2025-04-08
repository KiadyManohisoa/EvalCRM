package site.easy.to.build.crm.service.data;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import site.easy.to.build.crm.config.entity.ListImport;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.employee.Employee;
import site.easy.to.build.crm.exception.FormatException;
import site.easy.to.build.crm.exception.ImportExceptionBuilder;
import site.easy.to.build.crm.models.CustomerImport;
import site.easy.to.build.crm.repository.EmployeeRepository;

@Service
public class ImportService {
    
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRepository employeeRepository;
    private final char separator = ',';

    public ImportService(EmployeeRepository employeeRepository, JdbcTemplate jdbcTemplate) {
        this.employeeRepository = employeeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void importCsvAndInsertData(ListImport listImport, MultipartFile file, int idEntity) throws Exception {
        ImportEntity impett = listImport.getImportentities().get(idEntity);
        List<Object> objects = this.getDataFromCsv(file, impett);
        // for(int i=0;i<objects.size();i++) {
        //     System.out.println("["+i+"] "+objects.get(i).toString());
        // }
        try (Connection co = jdbcTemplate.getDataSource().getConnection()) {
            impett.processAndSaveToDatabase(co, objects);
        }
        catch(Exception e) {
            throw e;
        }
    }

    public List<Object> getDataFromCsv(MultipartFile file, ImportEntity impett) throws FormatException, IOException {
        List<Object> objects = new ArrayList<>();
        Set<Object> uniqueCheck = new HashSet<>();
        ImportExceptionBuilder exceptionBuilder = new ImportExceptionBuilder();

        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(this.separator)
                    .withQuoteChar('"')
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();

            CsvToBean<Object> csvToBean = new CsvToBeanBuilder<>(csvReader)
                    .withType(impett.getClazz())
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(false) 
                    .build();

            Iterator<Object> iterator = csvToBean.iterator();

            int lineNumber = 2;
            while (iterator.hasNext()) {
                lineNumber++; 
                try {
                    Object obj = iterator.next();
                    if (!uniqueCheck.add(obj)) {
                        exceptionBuilder.addException("Duplicated element "+obj.toString(),lineNumber-1,file.getOriginalFilename());
                        continue; // Passe à la ligne suivante sans ajouter l'objet
                    }
                    objects.add(obj);
                } catch (RuntimeException e) {
                    // throw e;
                    this.handleException(e, lineNumber, file.getOriginalFilename(), exceptionBuilder);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Erreur de lecture du fichier : " + e.getMessage());
        }
        
        if (exceptionBuilder.hasErrors()) {
            throw new FormatException(exceptionBuilder.build());
        }
        // System.out.println("object's length got from csv "+objects.size());

        return objects;
    }

    private void handleException(RuntimeException e, int lineNumber, String filename, ImportExceptionBuilder exceptionBuilder) {
        Throwable rootCause = getRootCause(e);
        String errorMessage = (rootCause instanceof FormatException) 
                ? rootCause.getMessage() 
                : "Erreur de format: " + e.getMessage();
        
        exceptionBuilder.addException(
            errorMessage,
            lineNumber,
            filename
        );
        // System.err.println("Erreur ligne " + lineNumber + " : " + errorMessage);
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }
}
