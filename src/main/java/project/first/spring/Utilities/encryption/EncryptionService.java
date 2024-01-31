package project.first.spring.Utilities.encryption;

public interface EncryptionService {
    String encrypt(String plainText);
    String decrypt(String cipherText);
    boolean verifyPassword(String password, String encryptedPassword);
}
