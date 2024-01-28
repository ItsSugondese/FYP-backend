package fyp.canteen.fypapi.repository.company;

import fyp.canteen.fypcore.model.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {
}
