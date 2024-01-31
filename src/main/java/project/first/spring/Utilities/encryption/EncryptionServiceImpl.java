package project.first.spring.Utilities.encryption;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    @Override
    public String decrypt(String cipherText) {
        return null;
    }

    @Override
    public boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

}
