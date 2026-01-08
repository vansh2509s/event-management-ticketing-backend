package com.Event.demo.service.checkin;

import com.Event.demo.checkin.CheckIn;
import com.Event.demo.checkin.CheckInRepository;
import com.Event.demo.common.Role;
import com.Event.demo.event.Event;
import com.Event.demo.event.EventRepository;
import com.Event.demo.ticket.Ticket;
import com.Event.demo.ticket.TicketRepository;
import com.Event.demo.user.User;
import com.Event.demo.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final CheckInRepository checkInRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CheckInServiceImpl(
            CheckInRepository checkInRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository
    ) {
        this.checkInRepository = checkInRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CheckIn checkInTicket(String ticketCode, long eventId, long scannerId) {

        // 1. Fetch ticket
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Invalid ticket"));

        // 2. Check already used
        if (ticket.isUsed()) {
            throw new RuntimeException("Ticket already used");
        }

        // 3. Check expiration
        if (ticket.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Ticket expired");
        }

        // 4. Validate ticket belongs to event
        if (!ticket.getEvent().getId().equals(eventId)) {
            throw new RuntimeException("Ticket does not belong to this event");
        }

        // 5. Fetch scanner
        User scanner = userRepository.findById(scannerId)
                .orElseThrow(() -> new RuntimeException("Scanner not found"));

        // 6. Role validation
        if (scanner.getRole() != Role.SCANNER &&
                scanner.getRole() != Role.HOST &&
                scanner.getRole() != Role.ADMIN) {
            throw new RuntimeException("User not authorized to check in tickets");
        }

        // 7. Prevent duplicate check-in
        if (checkInRepository.findByTicket_Id(ticket.getId()).isPresent()) {
            throw new RuntimeException("Ticket already checked in");
        }


        // 8. Mark ticket as used
        ticket.setUsed(true);
        ticketRepository.save(ticket);

        // 9. Create check-in record
        CheckIn checkIn = new CheckIn();
        checkIn.setTicket(ticket);
        checkIn.setScanner(scanner);
        checkIn.setCheckedInAt(LocalDateTime.now());

        return checkInRepository.save(checkIn);
    }
    @Override
    public CheckIn getCheckInByTicket(Long ticketId) {
        return checkInRepository.findByTicket_Id(ticketId)
                .orElseThrow(() -> new RuntimeException("Check-in not found for ticket"));
    }

    @Override
    public List<CheckIn> getCheckInsByEvent(Long eventId) {
        return checkInRepository.findByTicket_Event_Id(eventId);
    }

    @Override
    public List<CheckIn> getCheckInsByScanner(Long scannerId) {
        return checkInRepository.findByScanner_Id(scannerId);
    }

}
