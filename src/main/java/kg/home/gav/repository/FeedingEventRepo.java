package kg.home.gav.repository;

import kg.home.gav.entity.FeedingEvent;
import org.springframework.data.repository.CrudRepository;

public interface FeedingEventRepo extends CrudRepository <FeedingEvent, Long> {
}
