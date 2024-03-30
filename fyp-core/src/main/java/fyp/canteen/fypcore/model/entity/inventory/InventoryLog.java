package fyp.canteen.fypcore.model.entity.inventory;

import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryLog extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "inventory_menu_mapping_gen", sequenceName = "inventory_menu_mapping_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_menu_mapping_gen")
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_menu_mapping_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_inventory_log_menu_mapping"))
    private InventoryMenuMapping inventoryMenuMapping;

}
