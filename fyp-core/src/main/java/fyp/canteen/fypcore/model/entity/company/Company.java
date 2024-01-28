package fyp.canteen.fypcore.model.entity.company;

import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company", uniqueConstraints = {@UniqueConstraint(name = "unique_company_email", columnNames = "emailAddress")})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Company extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "company_gen", sequenceName = "company_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_gen")
    private Long id;
    @Column(nullable = false)
    private String companyName;
    private String description;
    private String address;
    private String location;
    @Column(nullable = false)
    private String emailAddress;
    private String companyLogo;
    private String contactNumber;
}
