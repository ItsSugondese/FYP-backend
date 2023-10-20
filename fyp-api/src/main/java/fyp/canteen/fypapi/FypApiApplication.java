package fyp.canteen.fypapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"fyp.canteen.fypapi.*", "fyp.canteen.fypcore.*"})
@EntityScan({"fyp.canteen.fypcore.model"})
public class FypApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FypApiApplication.class, args);
	}

}
