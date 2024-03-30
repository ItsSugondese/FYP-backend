package fyp.canteen.fypcore.model.entity.foodmgmt;

import fyp.canteen.fypcore.enums.FoodType;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "food_menu", uniqueConstraints ={@UniqueConstraint(name = "unique_food_name", columnNames = {"name"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodMenu extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "food_menu_gen", sequenceName = "food_menu_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_gen")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double cost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    @Column( name = "is_available_today")
    private Boolean isAvailableToday = true;

    @Column(columnDefinition = "boolean default false")
    private Boolean isAuto = false;


}

