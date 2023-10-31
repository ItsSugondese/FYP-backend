package fyp.canteen.fypcore.utils;

import fyp.canteen.fypcore.config.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserDataConfig {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    public String getEmailFromToken(String token) {
        return jwtUtil.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return jwtUtil.extractClaim(token, Claims::getExpiration);
    }
    public Long userId(){
        return Long.parseLong(jwtUtil.getTokenBody((request.getHeader("Authorization")).substring(7)).get("userId").toString());
    }
}
