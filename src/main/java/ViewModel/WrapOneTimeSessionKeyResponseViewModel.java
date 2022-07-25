package ViewModel;

public class WrapOneTimeSessionKeyResponseViewModel {

    public String EncryptedSecretKeyHex;
    public String DecryptedData;
    public String OneTimeSessionKey; 

    public String getEncryptedSecretKeyHex() {
        return EncryptedSecretKeyHex;
    }
    public void setEncryptedSecretKeyHex(String encryptedSecretKeyHex) {
        EncryptedSecretKeyHex = encryptedSecretKeyHex;
    }

    public String getOneTimeSessionKey() {
        return OneTimeSessionKey;
    }
    public void setOneTimeSessionKey(String oneTimeSessionKey) {
        OneTimeSessionKey = oneTimeSessionKey;
    }
    
    public String getDecryptedData() {
        return DecryptedData;
    }
    public void setDecryptedData(String decryptedData) {
        DecryptedData = decryptedData;
    }

}
