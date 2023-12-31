package fyp.canteen.fypapi.config.auditor;

import fyp.canteen.fypcore.utils.UserDataConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class JpaAuditingConfiguration {

    private final UserDataConfig userDataConfig;


    @Bean
    public AuditorAware<Long> auditorProvider(HttpServletRequest request) {
        return new AuditorAware<Long>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    return Optional.of(userDataConfig.userId());
                } else {
                    return Optional.empty();
                }
            }
        };
    }




}
