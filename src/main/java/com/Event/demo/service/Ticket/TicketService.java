package com.Event.demo.service.Ticket;
import com.Event.demo.ticket.Ticket;
import java.util.List;
public interface TicketService
{
    Ticket createTicket(long userId,long eventId);
    Ticket getTicketByCode(String ticketCode);
    List<Ticket> getTicketsByUser(long userId);
    void markTicketAsUsed(String ticketCode);

}
