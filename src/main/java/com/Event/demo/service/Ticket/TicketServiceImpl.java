package com.Event.demo.service.Ticket;
import com.Event.demo.ticket.Ticket;
import com.Event.demo.user.User;
import com.Event.demo.user.UserRepository;
import com.Event.demo.ticket.TicketRepository;
import com.Event.demo.event.Event;
import com.Event.demo.event.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, EventRepository
            eventRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Ticket createTicket(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        if (event.getTicketsSold() >= event.getTotalTickets()) {
            throw new RuntimeException("Tickets sold out");
        }
        String ticketCode = UUID.randomUUID().toString();
        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketCode);
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setExpiresAt(event.getEndTime());
        ticket.setUsed(false);
        event.setTicketsSold(event.getTotalTickets()+1);
        eventRepository.save(event);
        return ticketRepository.save(ticket);
    }
    @Override
    public Ticket getTicketByCode(String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode).orElseThrow(()->new RuntimeException("Ticket not found"));
    }
    @Override
    public List<Ticket> getTicketsByUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByUserId(user.getId());
    }

    @Override
    public void markTicketAsUsed(String ticketCode) {
        Ticket ticket = getTicketByCode(ticketCode);
        ticket.setUsed(false);
    }
}
