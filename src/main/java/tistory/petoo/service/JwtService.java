package tistory.petoo.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${pddu.jwt.key}")
    private String secretKey;
    private SignatureAlgorithm signatureAlgorithm;
    private Key signingKey;

    @PostConstruct
    private void init() throws Exception {
        // 2022.12.15[프뚜]: 암호화 기법 설정
        signatureAlgorithm = SignatureAlgorithm.HS512;

        // 2022.12.15[프뚜]: 암호키 설정(보안으로 인해 프로퍼티에 설정)
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
    }

    public String create() throws Exception {
        // 2022.12.15[프뚜]: body에 넣을 값 생성
        Map<String, Object> body = new HashMap<>();
        body.put("name", "pddu");
        body.put("birth", "0801");
        body.put("hobby", "coding");

        // 2022.12.15[프뚜]: 1시간짜리 토큰 발행
        JwtBuilder builder = Jwts.builder()
                .setClaims(body)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + (3600 * 1000)));
        return builder.compact();
    }

    public String check(String token) throws Exception {
        // 2022.12.15[프뚜]: token 해석
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();

        return body.toString();
    }
}
