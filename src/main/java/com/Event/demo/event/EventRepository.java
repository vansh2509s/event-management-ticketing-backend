package com.Event.demo.event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface EventRepository extends JpaRepository<Event , Long>
{


    Optional<Event> findByEventName(String Event_name);
    List<Event> findByCreatedById(Long userId);
}
