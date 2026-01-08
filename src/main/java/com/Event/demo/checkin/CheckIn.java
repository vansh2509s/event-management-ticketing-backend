package com.Event.demo.checkin;
import com.Event.demo.event.Event;
import jakarta.persistence.*;
import com.Event.demo.ticket.Ticket;
import com.Event.demo.user.User;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "check_ins")
@Data
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false, unique = true)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "scanner_id", nullable = false)
    private User scanner;

    @Column(nullable = false)
    private LocalDateTime checkedInAt;
}


