package Sidenow.Smart_SeniorCenter.jwt;
import Sidenow.Smart_SeniorCenter.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    private final Key key;
    private static final String GRANT_TYPE = "Bearer";

    public JwtGenerator(@Value("${spring.jwt.secret}") String secretKey) {

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);  // 일반 문자열을 바이트 배열로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);  // HMAC SHA Key 생성
    }

    public JwtToken generateToken(Long userId,List<UserRole> roles) {

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + 86400000);

        String authorities = roles.stream()
                .map(UserRole::getValue)  // Enum -> String 변환
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("auth", authorities)// 권한 설정
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()//아직 리프레쉬 토큰은 구현못함
                .setExpiration(new Date(now + 604800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType(GRANT_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
