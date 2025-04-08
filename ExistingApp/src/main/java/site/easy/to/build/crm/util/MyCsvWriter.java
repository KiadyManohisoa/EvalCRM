package site.easy.to.build.crm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.opencsv.CSVWriter;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

public class MyCsvWriter {

    public ByteArrayInputStream saveInCsvFile(Customer customer, List<Lead> leads, List<Ticket> tickets) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(out), ',', 
                                            CSVWriter.NO_QUOTE_CHARACTER, 
                                            CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
                                            CSVWriter.DEFAULT_LINE_END)) {
            
            // Writing customer info
            writer.writeNext(new String[]{"customer_email", "customer_name"});
            writer.writeNext(new String[]{customer.getEmail(), customer.getName()});

            writer.writeNext(new String[]{});

            // Writing tickets
            writer.writeNext(new String[]{"customer_email", "subject_or_name", "type", "status", "expense"});
            for (Ticket ticket : tickets) {
                writer.writeNext(new String[]{customer.getEmail(), ticket.getSubject(), "ticket", ticket.getStatus(), "0.1"});
            }

            // Writing leads
            for (Lead lead : leads) {
                writer.writeNext(new String[]{customer.getEmail(), lead.getName(), "lead", lead.getStatus(), "0.1"});
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création du CSV", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
