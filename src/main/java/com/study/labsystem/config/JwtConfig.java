package com.study.labsystem.config;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@Component
//@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private long expire;
    @Value("${jwt.header}")
    private String header;

    public String createToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000); //配置文件是s
        System.out.println(nowDate+"--"+expireDate);
        return Jwts.builder()
               .setHeaderParam("typ", "JWT")
               .setSubject(subject)
               .setIssuedAt(nowDate)
               .setExpiration(expireDate)
               .signWith(SignatureAlgorithm.HS512, secret)
               .compact();
    }

    public Claims geTokenClim(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    public String getSubject(String token){
        return geTokenClim(token).getSubject();
    }
}















