package site.easy.to.build.crm.service.lead;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.repository.LeadRepository;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    public Map<LocalDate, List<Lead>> getLeadsGroupedByDay(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        
        List<Lead> leads = leadRepository.findBetweenDates(startDateTime, endDateTime);
        
        return leads.stream()
            .collect(Collectors.groupingBy(
                lead -> lead.getCreatedAt().toLocalDate(),
                TreeMap::new, 
                Collectors.toList()
            ));
    }

    public LeadServiceImpl(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public Lead findByLeadId(int id) {
        return leadRepository.findByLeadId(id);
    }

    @Override
    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    @Override
    public List<Lead> findAssignedLeads(int userId) {
        return leadRepository.findByEmployeeId(userId);
    }

    @Override
    public List<Lead> findCreatedLeads(int userId) {
        return leadRepository.findByManagerId(userId);
    }

    @Override
    public Lead findByMeetingId(String meetingId){
        return leadRepository.findByMeetingId(meetingId);
    }
    @Override
    public Lead save(Lead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public void delete(Lead lead) {
        leadRepository.delete(lead);
    }

    @Override
    public List<Lead> getRecentLeadsByEmployee(int employeeId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return leadRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId, pageable);
    }

    @Override
    public List<Lead> getRecentCustomerLeads(int customerId, int limit) {
        Pageable pageable = PageRequest.of(0,limit);
        return leadRepository.findByCustomerCustomerIdOrderByCreatedAtDesc(customerId, pageable);
    }

    @Override
    public void deleteAllByCustomer(Customer customer) {
        leadRepository.deleteAllByCustomer(customer);
    }

    @Override
    public List<Lead> getRecentLeads(int managerId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return leadRepository.findByManagerIdOrderByCreatedAtDesc(managerId, pageable);
    }

    @Override
    public List<Lead> getCustomerLeads(int customerId) {
        return leadRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public long countByEmployeeId(int employeeId) {
        return leadRepository.countByEmployeeId(employeeId);
    }

    @Override
    public long countByManagerId(int managerId) {
        return leadRepository.countByManagerId(managerId);
    }

    @Override
    public long countByCustomerId(int customerId) {
        return leadRepository.countByCustomerCustomerId(customerId);
    }
}
