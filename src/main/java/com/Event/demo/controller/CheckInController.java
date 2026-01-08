package com.Event.demo.controller;
import com.Event.demo.checkin.CheckIn;
import com.Event.demo.service.checkin.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    // 1️⃣ Scan / Check-in ticket
    @PostMapping
    public ResponseEntity<CheckIn> checkInTicket(
            @RequestParam String ticketCode,
            @RequestParam Long eventId,
            @RequestParam Long scannerId
    ) {
        CheckIn checkIn = checkInService.checkInTicket(ticketCode, eventId, scannerId);
        return ResponseEntity.ok(checkIn);
    }

    // 2️⃣ Get check-in by ticket
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<CheckIn> getCheckInByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(checkInService.getCheckInByTicket(ticketId));
    }

    // 3️⃣ Get check-ins by event
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CheckIn>> getCheckInsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(checkInService.getCheckInsByEvent(eventId));
    }

    // 4️⃣ Get check-ins by scanner
    @GetMapping("/scanner/{scannerId}")
    public ResponseEntity<List<CheckIn>> getCheckInsByScanner(@PathVariable Long scannerId) {
        return ResponseEntity.ok(checkInService.getCheckInsByScanner(scannerId));
    }
}
