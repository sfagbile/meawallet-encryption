package Utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.RSAPublicKeySpec;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import ViewModel.WrapOneTimeSessionKeyResponseViewModel;

public class WrapOneTimeSessionKeyUtility {
    
    private static final byte[] IV = new byte[16];

    public  WrapOneTimeSessionKeyResponseViewModel EncrytOneTimeToken(String modulus, String exponent) throws Exception{
           // 1. Client generates AES-256 bit one-time session key (SK);
           byte[] oneTimeSessionKey = generateOneTimeSessionKey();

           // 2. Encrypt (wrap) the session key with MeaWallet’s Public Key (G1 key);
           PublicKey publicKey = buildRsaPublicKey(
                   Hex.decodeHex(modulus),
                   Hex.decodeHex(exponent));
           String encryptedSecretKeyHex = encryptSessionKey(oneTimeSessionKey, publicKey);
   
           // 3. Client sends wrapped one time session key to MeaWallet together with publicKeyFingerprint which was used for encryption;
           System.out.println("Wrapped one time session key: " + encryptedSecretKeyHex);

           WrapOneTimeSessionKeyResponseViewModel response = new WrapOneTimeSessionKeyResponseViewModel();
           
           response.setOneTimeSessionKey(Hex.encodeHexString(oneTimeSessionKey));
           response.setEncryptedSecretKeyHex(encryptedSecretKeyHex);

           return response;
    }

    public WrapOneTimeSessionKeyResponseViewModel DecryptMeaWalletResponse(String meaWalletResponseData, String oneTimeSessionStringKey) throws Exception{
      
        byte[] oneTimeSessionKey =  Hex.decodeHex(oneTimeSessionStringKey);
        byte[] decryptedData = decryptCardData(meaWalletResponseData, oneTimeSessionKey , IV);
        String decrptedValue = new String(decryptedData, StandardCharsets.UTF_8);
   

        System.out.println("Data decrypted with one time session key: " + decrptedValue);
        System.out.println("Data decrypted with one time session key: " +decrptedValue.toString());
        System.out.println("Data decrypted with one time session key: " + new String(decryptedData, StandardCharsets.UTF_8));

        WrapOneTimeSessionKeyResponseViewModel response = new WrapOneTimeSessionKeyResponseViewModel();
        response.setDecryptedData(decrptedValue);
     
        return response;
 }


    private static byte[] generateOneTimeSessionKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey().getEncoded();
    }

    public static PublicKey buildRsaPublicKey(byte[] modulus, byte[] exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(1, modulus), new BigInteger(1, exponent));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    private static String encryptSessionKey(byte[] sessionKey, Key publicKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec(
                "SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParameterSpec);
        byte[] encryptedKey = cipher.doFinal(sessionKey);
        return Hex.encodeHexString(encryptedKey);
    }

    /**
    * Card Data encryption on MeaWallet side.
    */
    private static String encryptCardData(byte[] data, byte[] secretKey, byte[] initVector) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, "AES"), iv);
        byte[] encryptedKey = cipher.doFinal(data);
        return Hex.encodeHexString(encryptedKey);
    }

    static byte[] decryptCardData(String encryptedData,
                                byte[] secretKey,
                                byte[] initVector) throws GeneralSecurityException, DecoderException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(initVector);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, "AES"), iv);
        byte[] decryptedData = cipher.doFinal(Hex.decodeHex(encryptedData));
        return decryptedData;
    }
}
