package fyp.canteen.fypcore.model.entity.tablemodel;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "custom_table", uniqueConstraints = {@UniqueConstraint(name = "unique_table_number", columnNames = {"tableNumber"})})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomTable {
    @Id
    @SequenceGenerator(name = "table_gen", sequenceName = "table_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_gen")
    private Long id;

    private Integer tableNumber;
    private String guid;
    private String qrPath;
    private LocalDateTime createdDate = LocalDateTime.now();
}
