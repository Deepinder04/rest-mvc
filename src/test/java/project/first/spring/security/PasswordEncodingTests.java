package project.first.spring.security;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class PasswordEncodingTests {

    private final String password = "password";

    @Test
    void MDMPasswordSalt(){
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        String saltedPassword = password + "mySaltedPassword";
        System.out.println(DigestUtils.md5DigestAsHex(saltedPassword.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void NoOpPasswordEncoder(){
        PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        System.out.println(encoder.encode(password));
    }

    @Test
    void LDAPPasswordEncoder(){
        PasswordEncoder encoder = new LdapShaPasswordEncoder();
        System.out.println(encoder.encode(password));
        System.out.println(encoder.encode(password));

        String encodedPassword = encoder.encode(password);
        System.out.println(encoder.matches(password,encodedPassword));
    }

    @Test
    void SHA256PasswordEncoder(){
        PasswordEncoder encoder = new StandardPasswordEncoder();
        System.out.println(encoder.encode(password));
    }

    @Test
    void BCryptPasswordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder(12);
        System.out.println(encoder.encode(password));
    }
}
