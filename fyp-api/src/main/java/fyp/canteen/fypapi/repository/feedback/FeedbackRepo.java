package fyp.canteen.fypapi.repository.feedback;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.feedback.Feedback;

public interface FeedbackRepo extends GenericSoftDeleteRepository<Feedback, Long> {
}
