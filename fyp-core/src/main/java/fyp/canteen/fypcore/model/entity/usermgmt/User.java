package fyp.canteen.fypcore.model.entity.usermgmt;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "unique_email_address", columnNames = {"email"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties
public class User extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    private Long id;
    private String password;

    @Column(nullable = false)
    private String email;
    private String fullName;
    private String contactNumber;
    @Column(nullable = false, name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isEmailAddressVerified = false;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Column(columnDefinition = "TEXT")
    private String profilePath;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name="USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="ROLE_ID")
            }
    )
    private Set<Role> role;
}
