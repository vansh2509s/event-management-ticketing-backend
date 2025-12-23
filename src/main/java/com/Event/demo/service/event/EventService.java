package com.Event.demo.service.event;
import com.Event.demo.event.Event;
import java.util.List;
public interface EventService
{
    Event createEvent(Event event , Long hostId);
    List<Event> getEventsByHost(Long hostId);
    Event getEventById(Long eventId);
    Event updateEvent(Long eventId,Event updatedEvent);
    List<Event> getAllEvents();
    void deleteEvent(Long eventId);

}
