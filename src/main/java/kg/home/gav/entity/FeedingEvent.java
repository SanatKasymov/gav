package kg.home.gav.entity;

import kg.home.gav.enums.CatFoodPortion;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class FeedingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String user;

    Date dateTime;

    CatFoodPortion portion;
}
