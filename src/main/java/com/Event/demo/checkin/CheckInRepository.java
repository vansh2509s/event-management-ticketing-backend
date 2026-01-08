package com.Event.demo.checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    boolean existsByTicket_Id(Long ticketId);

    Optional<CheckIn> findByTicket_Id(Long ticketId);

    List<CheckIn> findByTicket_Event_Id(Long eventId);

    List<CheckIn> findByScanner_Id(Long scannerId);
}
