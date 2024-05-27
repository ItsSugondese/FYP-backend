package fyp.canteen.fypapi;

import fyp.canteen.fypapi.config.mail.MailProperties;
import fyp.canteen.fypapi.repository.auth.RoleRepo;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.usermgmt.UserServiceHelper;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;


@SpringBootApplication
@Import(DelegatingWebMvcConfiguration.class)
@ComponentScan(basePackages = {"fyp.canteen.fypcore.*", "fyp.canteen.fypapi.*"})
@EnableJpaRepositories({"fyp.canteen.fypapi.repository"})
@EntityScan({"fyp.canteen.fypcore.model"})
@EnableConfigurationProperties(MailProperties.class)
public class FypApiApplication {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserServiceHelper userServiceHelper;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${base.main-mail}")
	private String mail;

	@Value("${base.pass}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(FypApiApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(MailProperties mailProperties){
		return  args -> {
			System.out.println(mailProperties);
		};
	}

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kathmandu"));
		insertRoles();
		Optional<User> admin =  userRepo.findByEmail(mail);

		if(!admin.isPresent()){
			User user = new User();
			user.setUserType(UserType.ADMIN);
			user.setFullName("Admin");
			user.setEmail(mail);
			user.setPassword(passwordEncoder.encode(password));

			user.setRole(userServiceHelper.getRoles("ADMIN".toUpperCase()));

			userRepo.save(user);

		}
	}

	private void insertRoles(){
		List<String> roleNames = List.of("ADMIN", "STAFF", "USER");
		List<Role> roles = roleRepo.findAll();

		List<String> roleInRole = roles.stream().map(Role::getRole).collect(Collectors.toList());


		for (String roleName : roleNames){
			if(!roleInRole.contains(roleName)){
				Role role = new Role();
				role.setRole(roleName);

				roleRepo.save(role);
			}
		}


	}


}
