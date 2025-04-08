package site.easy.to.build.crm.controller.rest;

import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.RenderResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api")
@RestController
public class TicketLeadController {

    private final TicketService ticketService;

    public TicketLeadController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/list/tickets")
    public ResponseEntity<RenderResponse<List<Map<String, Object>>>> getListTicket() {
        try {
            List<Ticket> tickets = this.ticketService.findAll();
    
            // Filtrer uniquement les attributs souhaités
            List<Map<String, Object>> filteredTickets = tickets.stream().map(ticket -> {
                Map<String, Object> ticketData = new HashMap<>();
                ticketData.put("ticketId", ticket.getTicketId());
                ticketData.put("subject", ticket.getSubject());
                ticketData.put("status", ticket.getStatus());
                ticketData.put("priority", ticket.getPriority());
                ticketData.put("createdAt", ticket.getCreatedAt());
                return ticketData;
            }).collect(Collectors.toList());
    
            // Création d'un objet RenderResponse avec une liste comme data
            RenderResponse<List<Map<String, Object>>> response = new RenderResponse<>(200, filteredTickets, "Liste des tickets", null);
    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new RenderResponse<>(400, null, "Échec de récupération des tickets", e.getMessage()));
        }
    }
    
    

}
