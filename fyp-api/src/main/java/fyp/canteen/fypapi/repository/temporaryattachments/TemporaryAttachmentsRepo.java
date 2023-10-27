package fyp.canteen.fypapi.repository.temporaryattachments;

import fyp.canteen.fypcore.model.temporaryattachments.TemporaryAttachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryAttachmentsRepo extends JpaRepository<TemporaryAttachments, Long> {
}
