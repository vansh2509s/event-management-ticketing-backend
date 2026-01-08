package com.Event.demo.controller;
import com.Event.demo.ticket.Ticket;
import com.Event.demo.service.Ticket.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api")
public class TicketController
{
    private final TicketService ticketService;
    public TicketController(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }
    @PostMapping("/event/{eventId}/tickets")
    public ResponseEntity<Ticket> createTicket(@RequestParam long userId ,@PathVariable long eventId)
    {
        Ticket ticket = ticketService.createTicket(userId,eventId);
        return new ResponseEntity<>(ticket , HttpStatus.CREATED);
    }

}
