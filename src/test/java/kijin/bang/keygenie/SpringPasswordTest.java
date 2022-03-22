package kijin.bang.keygenie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SpringPasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoding() {
        String password = "1111";
        String encodedPW = passwordEncoder.encode(password);
        System.out.println("Encoded \'1111\': " + encodedPW);
        System.out.println("Let\'s compare: " +  passwordEncoder.matches(password, encodedPW));
    }
}
