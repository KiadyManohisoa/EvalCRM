package site.easy.to.build.crm.controller.rest;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.config.entity.ListImport;
import site.easy.to.build.crm.models.CustomerImportRequest;
import site.easy.to.build.crm.service.ImportCustomerService;

@RestController
@RequestMapping("/api/import")
public class ImportCustomerController {

    private final ImportCustomerService importService;
    final ListImport listImport;


    @Autowired
    public ImportCustomerController(ImportCustomerService importService, ListImport listImport) {
        this.importService = importService;
        this.listImport = listImport;
    }

    @PostMapping("/customer")
    public ResponseEntity<String> importCustomer(@RequestBody CustomerImportRequest request) {
        try {
            // if(request.getCustomer()==null) {
            //     System.out.println("customer est encore null");
            // }
            // if(request.getExpenses()==null) {
            //     System.out.println("liste expenses null");
            // }
            // System.out.println(request.toString());
            importService.processImport(request, this.listImport);
            return ResponseEntity.ok("Importation réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'importation : " + e.getMessage());
        }
    }

}
