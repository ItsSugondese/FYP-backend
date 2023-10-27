package fyp.canteen.fypcore.model.temporaryattachments;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fyp.canteen.fypcore.enums.FileType;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "temporary_attachments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
public class TemporaryAttachments extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "temporary_attachments_gen", sequenceName = "temporary_attachments_gen", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temporary_attachments_gen")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "file_size", columnDefinition = "FLOAT")
    private Float fileSize;

    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;
}

