package kg.home.gav.repository;

import kg.home.gav.entity.FeedingEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface FeedingEventRepo extends CrudRepository <FeedingEvent, Long> {
    List<FeedingEvent> findAllByDateTimeAfter(Date date);
}
