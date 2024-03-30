package fyp.canteen.fypcore.model.entity.inventory;

import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_menu_mapping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryMenuMapping extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "inventory_menu_mapping_gen", sequenceName = "inventory_menu_mapping_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_menu_mapping_gen")
    private Long id;

    private Integer stock;
    private Integer remainingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_inventory_menu_mapping_food_menu"))
    private FoodMenu foodMenu;
}
