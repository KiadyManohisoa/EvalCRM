package site.easy.to.build.crm.controller.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.RenderResponse;

@RequestMapping("/api")
@RestController
public class TicketLeadRestController {

    private final TicketService ticketService;

    public TicketLeadRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @GetMapping("/tickets/by-day")
    public ResponseEntity<RenderResponse<Map<LocalDate, List<Ticket>>>> getTicketsByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            Map<LocalDate, List<Ticket>> data = ticketService.getTicketsGroupedByDay(start, end);
            return ResponseEntity.ok(new RenderResponse<>(200, data, "", null));
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Some error occured while fetching data", e.getMessage()));
        }
    }

}
