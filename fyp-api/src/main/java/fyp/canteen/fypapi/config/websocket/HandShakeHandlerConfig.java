package fyp.canteen.fypapi.config.websocket;


import com.sun.security.auth.UserPrincipal;
import fyp.canteen.fypapi.repository.websocket.UserPrincipalMappingRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.websocket.UserPrincipalMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Component
public class HandShakeHandlerConfig extends DefaultHandshakeHandler {


    @Autowired
    private UserPrincipalMappingRepo userPrincipalMappingRepo;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String query = request.getURI().getQuery();
        String userIdString = Arrays.stream(query.split("&"))
                .filter(param -> param.startsWith("userId="))
                .findFirst()
                .map(param -> param.substring("userId=".length()))
                .orElseThrow(() -> new AppException("User id is null"));

        Long id = Long.parseLong(userIdString);



            if (userPrincipalMappingRepo.existsById(id)) {

                return userPrincipalMappingRepo.findById(id).get().getPrincipal();
            } else {
                UserPrincipalMapping savedUser = userPrincipalMappingRepo.saveAndFlush(UserPrincipalMapping.builder()
                        .userId(id)
                        .principal(new UserPrincipal(String.valueOf(id)))
                        .build());
                return savedUser.getPrincipal();
            }
        }
    }

