package fyp.canteen.fypapi.config.mail;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Primary
//@PropertySource("classpath:mail.properties")
//@ConfigurationProperties(prefix = "mail")
//@Component
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class MailProperties {
//    private int port;
//    private String host;
//    private String from;
//    private String username;
//    private String password;
//    private String uri;
//    private Long companyId;
//}

@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "mail")
@ConfigurationPropertiesScan
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailProperties {
    private int port;
    private String host;
    private String from;
    private String username;
    private String password;
    private String uri;
    private Long companyId;
}
