package site.easy.to.build.crm.service.data;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import site.easy.to.build.crm.config.entity.ListImport;
import site.easy.to.build.crm.entity.ImportEntity;
import site.easy.to.build.crm.entity.employee.Employee;
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

    public void importCsvAndInsertData(MultipartFile file, int idEntity) throws Exception {
        ImportEntity impett = ListImport.getImportentities().get(idEntity);
        List<Object> objects = this.getDataFromCsv(file, impett);
        try (Connection co = jdbcTemplate.getDataSource().getConnection()) {
            impett.saveToDatabase(co, objects);
        }
        catch(Exception e) {
            throw e;
        }
    }

    public List<Object> getDataFromCsv(MultipartFile file, ImportEntity impett) throws Exception {
        List<Object> objects;
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(this.separator)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();

            CsvToBean<Object> csvToBean = new CsvToBeanBuilder<>(csvReader)
                    .withType(impett.getClazz())
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            objects = csvToBean.parse();

        } catch (Exception e) {
            throw e;
        }
        return objects;
    }
}
