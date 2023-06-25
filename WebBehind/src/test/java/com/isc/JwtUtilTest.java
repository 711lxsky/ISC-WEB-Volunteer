package com.isc;

import com.isc.common.utils.JwtUtil;
import com.isc.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("jige");
        user.setPassword("111111");
        String token  = jwtUtil.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkMDI5NTg3ZS04NDUzLTQ3M2YtODVjNC0wYjZiN2VjNjllOTkiLCJzdWIiOiJ7XCJwYXNzd29yZFwiOlwiMTExMTExXCIsXCJ1c2VybmFtZVwiOlwiamlnZVwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY4NzY2NTc0NiwiZXhwIjoxNjg3NjY3NTQ2fQ.DjAkxJ-VjuPI31ZsEcLkERUTd8ucbPQaDHM3Y8men4E";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkMDI5NTg3ZS04NDUzLTQ3M2YtODVjNC0wYjZiN2VjNjllOTkiLCJzdWIiOiJ7XCJwYXNzd29yZFwiOlwiMTExMTExXCIsXCJ1c2VybmFtZVwiOlwiamlnZVwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY4NzY2NTc0NiwiZXhwIjoxNjg3NjY3NTQ2fQ.DjAkxJ-VjuPI31ZsEcLkERUTd8ucbPQaDHM3Y8men4E";
        User user = jwtUtil.parseToken(token,User.class);
        System.out.println(user);
    }
}
