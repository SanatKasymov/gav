package kg.home.gav.entity;

import kg.home.gav.enums.CatFoodPortion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
