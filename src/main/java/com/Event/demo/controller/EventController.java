package com.Event.demo.controller;

import com.Event.demo.event.Event;
import com.Event.demo.service.event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 1️⃣ Create Event (HOST only - role check later via security)
    @PostMapping("/host/{hostId}")
    public ResponseEntity<Event> createEvent(
            @PathVariable Long hostId,
            @RequestBody Event event
    ) {
        Event createdEvent = eventService.createEvent(event, hostId);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // 2️⃣ Get single event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    // 3️⃣ Get all events (public)
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // 4️⃣ Get events by host
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Event>> getEventsByHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(eventService.getEventsByHost(hostId));
    }

    // 5️⃣ Update event
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event updatedEvent
    ) {
        Event event = eventService.updateEvent(eventId, updatedEvent);
        return ResponseEntity.ok(event);
    }

    // 6️⃣ Delete event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
