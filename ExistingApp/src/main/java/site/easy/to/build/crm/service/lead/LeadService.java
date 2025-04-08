package site.easy.to.build.crm.service.lead;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

public interface LeadService {

    public Map<LocalDate, List<Lead>> getLeadsGroupedByDay(LocalDate startDate, LocalDate endDate);

    public Lead findByLeadId(int id);

    public List<Lead> findAll();

    public List<Lead> findAssignedLeads(int userId);

    public List<Lead> findCreatedLeads(int userId);

    public Lead findByMeetingId(String meetingId);

    public Lead save(Lead lead);

    public void delete(Lead lead);

    public List<Lead> getRecentLeads(int mangerId, int limit);
    public List<Lead> getCustomerLeads(int customerId);

    long countByEmployeeId(int employeeId);

    long countByManagerId(int managerId);
    long countByCustomerId(int customerId);

    List<Lead> getRecentLeadsByEmployee(int employeeId, int limit);
    List<Lead> getRecentCustomerLeads(int customerId, int limit);
    public void deleteAllByCustomer(Customer customer);
}
