package site.easy.to.build.crm.service.data;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import site.easy.to.build.crm.repository.CleanDataRepository;

@Service
public class CleanDataService {

    private final CleanDataRepository cleanDataRepository;

    public CleanDataService(CleanDataRepository cleanDataRepository) {
        this.cleanDataRepository = cleanDataRepository;
    }

    @Transactional
    public void cleanTables(List<String> tableNames) throws Exception {
        cleanDataRepository.deleteDataFromTables(tableNames);
    }
    
}