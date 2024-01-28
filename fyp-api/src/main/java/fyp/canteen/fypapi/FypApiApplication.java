package fyp.canteen.fypapi;

import fyp.canteen.fypapi.config.mail.MailProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;


@SpringBootApplication
@Import(DelegatingWebMvcConfiguration.class)
@ComponentScan(basePackages = {"fyp.canteen.fypcore.*", "fyp.canteen.fypapi.*"})
@EnableJpaRepositories({"fyp.canteen.fypapi.repository"})
@EntityScan({"fyp.canteen.fypcore.model"})
@EnableConfigurationProperties(MailProperties.class)
public class FypApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FypApiApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(MailProperties mailProperties){
		return  args -> {
			System.out.println(mailProperties);
		};
	}
}
