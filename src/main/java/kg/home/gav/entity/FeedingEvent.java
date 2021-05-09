package kg.home.gav.entity;

import kg.home.gav.enums.CatFoodPortion;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name= "FEEDING_EVENT")
public class FeedingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "date_time")
    Date dateTime;

    @Column(name = "portion")
    CatFoodPortion portion;
}
