package ViewModel;

public class DecryptMeaWalletResponseViewModel {
   public String MeaWalletResponseData; 
   public String OneTimeSessionKey;

    public String getOneTimeSessionKey() {
        return OneTimeSessionKey;
    }
    public void setOneTimeSessionKey(String oneTimeSessionKey) {
        OneTimeSessionKey = oneTimeSessionKey;
    }
    public String getMeaWalletResponseData() {
        return MeaWalletResponseData;
    }
    public void setMeaWalletResponseData(String meaWalletResponseData) {
        MeaWalletResponseData = meaWalletResponseData;
    }
}
