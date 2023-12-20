package com.study.labsystem;

import com.study.labsystem.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LaboratoryBackApplicationTests {

	@Autowired
	private JwtConfig jwtConfig;

	@Test
	void contextLoads() {
		String token = jwtConfig.createToken("admin");
		Claims claims = jwtConfig.geTokenClim(token);
		String subject = jwtConfig.getSubject(token);
		System.out.println(token);
		System.out.println(claims);
		System.out.println(subject);
		System.out.println("=================");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("--------------------");
				Claims claims = jwtConfig.geTokenClim(token);
				String subject = jwtConfig.getSubject(token);
				System.out.println(token);
				System.out.println(claims);
				System.out.println(subject);
			}
		}).start();



	}

}




