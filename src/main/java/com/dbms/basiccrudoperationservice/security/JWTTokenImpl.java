package com.dbms.basiccrudoperationservice.security;

import com.dbms.basiccrudoperationservice.domain.UserData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenImpl implements IJWTTokenGenerator{
    @Override
    public Map<String, String> generateJWTToken(UserData userData) {
        Map<String, String> map = new HashMap<>();
        String jwtToken = Jwts.builder().setSubject(userData.getUserEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "user-key")
                .compact();
        map.put("token", jwtToken);
        return map;
    }
}
