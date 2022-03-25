package kr.co.team.planner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncodingTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoding(){
        String password = "1111";
        //encoding - store in DB with encoded type
        String enPw = passwordEncoder.encode(password);

        //output
        System.out.println("encoded password: " + enPw);
        //a sample of encodedPw: $2a$10$ziTrg9jJ.jG5q98QY3uN1ux1g6cx1U5ryzMhQvaE5jWKZLsHkG4VO

        //compare - compare data when check login information
        System.out.println("compare: " + passwordEncoder.matches(password, enPw));

    }
}
