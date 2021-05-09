package kg.home.gav.entity;

import kg.home.gav.enums.CatFoodType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "CAT_FOOD")
public class CatFood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "type")
    CatFoodType type;

    @Column(name = "amount")
    int amount;
}