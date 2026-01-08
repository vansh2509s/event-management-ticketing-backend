package com.Event.demo.service.checkin;
import com.Event.demo.checkin.CheckIn;
import com.Event.demo.event.Event;
import com.Event.demo.common.Role;
import com.Event.demo.ticket.Ticket;

import java.util.List;

public interface CheckInService
{
    CheckIn checkInTicket(String ticketCode,long eventId, long checkInBy);

    CheckIn getCheckInByTicket(Long ticketId);

    List<CheckIn> getCheckInsByEvent(Long eventId);

    List<CheckIn> getCheckInsByScanner(Long scannerId);
}
