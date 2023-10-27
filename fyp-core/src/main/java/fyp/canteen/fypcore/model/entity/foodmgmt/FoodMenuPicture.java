package fyp.canteen.fypcore.model.entity.foodmgmt;

import fyp.canteen.fypcore.enums.FileType;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_menu_picture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodMenuPicture extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "food_menu_picture_gen", sequenceName = "food_menu_picture_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_menu_picture_gen")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Float fileSize;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_food_menu_picture_food_menu"))
    private FoodMenu foodMenu;
}
