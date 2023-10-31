package fyp.canteen.fypcore.model.entity.ordermgmt;

import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_food_mapping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFoodMapping {

    @Id
    @SequenceGenerator(name = "order_food_mapping_gen", sequenceName = "order_food_mapping_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_food_mapping_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_order_food_mapping_food"), nullable = false)
    private FoodMenu foodMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_user_mapping_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_order_food_mapping_order_user_mapping"))
    private OrderUserMapping orderUserMapping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_order_food_mapping_online_order"))
    private OnlineOrder onlineOrder;

    @Column(nullable = false)
    private Integer quantity;
}
