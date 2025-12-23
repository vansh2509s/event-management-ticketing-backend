package com.Event.demo.service.event;

import com.Event.demo.common.Role;
import com.Event.demo.event.Event;
import com.Event.demo.event.EventRepository;
import com.Event.demo.user.User;
import com.Event.demo.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository,
                            UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Event createEvent(Event event, Long hostId) {

        // 1. Validate host existence
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("Host not found"));

        // 2. Role check
        if (host.getRole() != Role.HOST) {
            throw new RuntimeException("Only HOST users can create events");
        }

        // 3. Time validation
        if (event.getEndTime().isBefore(event.getStartTime())) {
            throw new RuntimeException("Event end time cannot be before start time");
        }

        // 4. Initialize controlled fields
        event.setCreatedBy(host);
        event.setTicketsSold(0L);

        // 5. Persist
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsByHost(Long hostId) {

        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("Host not found"));

        if (host.getRole() != Role.HOST) {
            throw new RuntimeException("User is not a host");
        }

        return eventRepository.findByCreatedById(hostId);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Event updateEvent(Long eventId, Event updatedEvent) {

        Event existing = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // TODO (AUTH): Ensure requester is EVENT OWNER or ADMIN

        // Time validation
        if (updatedEvent.getEndTime().isBefore(updatedEvent.getStartTime())) {
            throw new RuntimeException("Event end time cannot be before start time");
        }

        // Ticket consistency validation
        if (updatedEvent.getTotalTickets() < existing.getTicketsSold()) {
            throw new RuntimeException(
                    "Total tickets cannot be less than tickets already sold"
            );
        }

        // Update allowed fields
        existing.setEventName(updatedEvent.getEventName());
        existing.setEventDescription(updatedEvent.getEventDescription());
        existing.setEventLocation(updatedEvent.getEventLocation());
        existing.setStartTime(updatedEvent.getStartTime());
        existing.setEndTime(updatedEvent.getEndTime());
        existing.setTotalTickets(updatedEvent.getTotalTickets());

        return eventRepository.save(existing);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteEvent(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        //TODO (AUTH): Allow only ADMIN or event creator

        eventRepository.delete(event);
    }
}
