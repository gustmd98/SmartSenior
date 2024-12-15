package Sidenow.Smart_SeniorCenter.jwt;
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
import java.util.Date;

@Component
public class JwtGenerator {
    private final Key key;
    private static final String GRANT_TYPE = "Bearer";

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtGenerator(@Value("${spring.jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);  // 일반 문자열을 바이트 배열로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);  // HMAC SHA Key 생성
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Long userId) {
        // 권한 가져오기

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 86400000);

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
//                .claim("auth", authorities) 권한 설정 안했음
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
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
